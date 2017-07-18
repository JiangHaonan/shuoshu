package ss.common.orm.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;
import ss.common.orm.Page;
import ss.common.orm.interceptor.dialect.Dialect;
import ss.common.util.ReflectionUtils;
import ss.common.util.StringUtils;

/**
 * SQL工具类
 * @author poplar.yfyang / thinkgem
 * @version 2013-8-28
 */
public class SQLHelper {
	/**
	 * 复杂SQL分页，计数SQL缓存
	 */
    private static Map<String, String> CACHE_COUNTSQL = new ConcurrentHashMap<String, String>();
 
    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     *
     * @param ps              表示预编译的 SQL 语句的对象。
     * @param mappedStatement MappedStatement
     * @param boundSql        SQL
     * @param parameterObject 参数对象
     * @throws java.sql.SQLException 数据库异常
     */
    @SuppressWarnings("unchecked")
    public static void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null :
                    configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }


    /**
     * 查询总纪录数
     * @param sql             SQL语句
     * @param connection      数据库连接
     * @param mappedStatement mapped
     * @param parameterObject 参数
     * @param boundSql        boundSql
     * @return 总记录数
     * @throws SQLException sql查询错误
     */
    public static int getCount(final String sql, final Connection connection,
    							final MappedStatement mappedStatement, final Object parameterObject,
    							final BoundSql boundSql, Log log) throws SQLException {
		final String countSql =getCountSql(sql);
        Connection conn = connection;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            log.debug("COUNT SQL: " + StringUtils.replaceEach(countSql, new String[]{"\n","\t"}, new String[]{" "," "}));
            if (conn == null){
        		conn = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            }
        	ps = conn.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), parameterObject);
            //解决MyBatis 分页foreach 参数失效 start
			if (ReflectionUtils.getFieldValue(boundSql, "metaParameters") != null) {
				MetaObject mo = (MetaObject) ReflectionUtils.getFieldValue(boundSql, "metaParameters");
				ReflectionUtils.setFieldValue(countBS, "metaParameters", mo);
			}
			//解决MyBatis 分页foreach 参数失效 end 
            SQLHelper.setParameters(ps, mappedStatement, countBS, parameterObject);
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
            	ps.close();
            }
            if (conn != null) {
            	conn.close();
            }
        }
    }


    /**
     * 根据数据库方言，生成特定的分页sql
     * @param sql     Mapper中的Sql语句
     * @param page    分页对象
     * @param dialect 方言类型
     * @return 分页SQL
     */
    public static String generatePageSql(String sql, Page<Object> page, Dialect dialect) {
        if (dialect.supportsLimit()) {
            return dialect.getLimitString(sql, page.getFirstResult(), page.getPageSize());
        } else {
            return sql;
        }
    }
    
    /** 
     * 去除qlString的select子句。 
     * @param hql 
     * @return 
     */  
/*    @SuppressWarnings("unused")
	private static String removeSelect(String qlString){  
        int beginPos = qlString.toLowerCase().indexOf("from");  
        return qlString.substring(beginPos);  
    }  */
      
    /**
     * 获取智能的countSql
     *
     * @param sql
     * @return
     */
    public static String getSmartCountSql(String sql) {
        //校验是否支持该sql
        isSupportedSql(sql);
         StringBuilder sqlBuffer;
		try {
			MySqlStatementParser parser = new MySqlStatementParser(sql);
			List<SQLStatement> statementList = parser.parseStatementList();

			sqlBuffer = new StringBuilder("select count(0) tmp_count ");
			final StringBuilder from = new StringBuilder();
			final StringBuilder where = new StringBuilder();

			SQLASTOutputVisitor fromVisitor = SQLUtils.createFormatOutputVisitor(from, statementList, JdbcUtils.MYSQL);
			SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(where, statementList, JdbcUtils.MYSQL);
             
		 if(CollectionUtils.isNotEmpty(statementList) && statementList.size() == 1){
			SQLStatement stmt = statementList.get(0);
			if (stmt instanceof SQLSelectStatement) {
				SQLSelectStatement sstmt = (SQLSelectStatement) stmt;
				SQLSelect sqlselect = sstmt.getSelect();
				SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect.getQuery();
				query.getFrom().accept(fromVisitor);
				sqlBuffer.append(" \n from ").append(from.toString());
				SQLExpr whereExpr=query.getWhere();
				if(whereExpr!=null){
					whereExpr.accept(whereVisitor);
					sqlBuffer.append(" \n where ").append(where);
				}
				SQLSelectGroupByClause  gb=query.getGroupBy();
				if(gb != null){
				   return getSimpleCountSql(sql);
				}
			}
		 }else{
				throw  new IllegalArgumentException(sql);
		 }
		  return sqlBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getSimpleCountSql(sql);
    }
    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    private static String getSimpleCountSql(final String sql) {
        isSupportedSql(sql);
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("select count(0) from (");
        stringBuilder.append(sql);
        stringBuilder.append(") tmp_count");
        return stringBuilder.toString();
    }
    
    public  static void isSupportedSql(String sql) {
        if (sql.trim().toUpperCase().endsWith("FOR UPDATE")) {
            throw new RuntimeException("分页插件不支持包含for update的sql");
        }
    }
    
    /**
     *  获取指定需分页SQL的计数SQL(缓存处理)
     * @author  FangJun
     * @date 2016年10月17日
     * @param sql  需分页SQL
     * @return 计数SQL
     */
    public  static String getCountSql(String sql) {
        //一般涉及复杂处理，缓存结果
        String countSql = CACHE_COUNTSQL.get(sql);
        if(countSql != null){
            return countSql;
        } else {
            countSql = getSmartCountSql(sql);
            CACHE_COUNTSQL.put(sql, countSql);
        }
        return countSql;
    }
}
