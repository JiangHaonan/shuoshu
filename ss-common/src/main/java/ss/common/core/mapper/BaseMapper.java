package ss.common.core.mapper;


import java.util.List;

/**
 * @author mutou
 * @date 2017年7月12日
 */
public interface BaseMapper<T> {
	
	/**
	 * 插入数据
	 * @param entity
	 * @return 
	 */
	int insert(T entity);
	
	/**
	 * 批量插入
	 * @param list
	 */
	int insertBatch(List<T> list);
		
	/**
	 * 删除数据
	 * @param id
	 * @return 
	 */
	int delete(String id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteBatch(String [] ids);
	
	/**
	 * 逻辑删除（更新is_delete字段为1）
	 * @param id
	 */
	int deleteLogic(String id);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return 
	 */
	int update(T entity);
	
	/**
	 * 批量更新
	 * @param list
	 */
	int updateBatch(List<T> list);
	
	/**
	 * 查询所有数据列表
	 * @param model
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：model.setPage(new Page<T>());
	 * @param model
	 * @return
	 */
	List<T> findList(T model);
	
	/**
	 * 查询数据记录
	 * @param model
	 * @return
	 */
	int getCount(T model);
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	T get(String id);
	
}