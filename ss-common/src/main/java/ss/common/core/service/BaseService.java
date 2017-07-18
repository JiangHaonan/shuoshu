package ss.common.core.service;

public interface BaseService<T> {
	
	/**
	 * 插入数据
	 * @param entity
	 * @return 
	 */
	void insert(T entity);
	
	/**
	 * 保存数据
	 * @param entity
	 * @return
	 */
	void save(T entity);
	
}
