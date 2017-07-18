package ss.common.core.entity;

import java.io.Serializable;
import java.util.UUID;

import ss.common.orm.Page;
import ss.common.util.StringUtils;

/**
 * 实体类基类
 * @author mutou
 * @date 2017年7月12日
 */

public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键:UUID
	 */
	protected String uuid;
	
	/**
	 * 当前实体分页对象
	 */
	protected Page<T> page;
	
	/**
	 * 保存数据库前预处理
	 */
	public void preInsert(){
		this.uuid =StringUtils.isBlank(this.uuid)?genUuid():this.uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String genUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}