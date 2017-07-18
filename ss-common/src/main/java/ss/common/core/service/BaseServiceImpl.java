package ss.common.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ss.common.core.entity.BaseEntity;
import ss.common.core.mapper.BaseMapper;
import ss.common.util.StringUtils;


@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class BaseServiceImpl<D extends BaseMapper<T>, T extends BaseEntity<T>> implements BaseService<T> {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	@Transactional(readOnly = false)
	@Override
	public void insert(T entity) {
		entity.preInsert();
		dao.insert(entity);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void save(T entity) {
		int result = 0;
		if(StringUtils.isNotBlank(entity.getUuid())){
			entity.preInsert();
			result =  dao.insert(entity);
		}else{
			result =  dao.update(entity);
		}
		if(result == 0){
			throw new RuntimeException("No saved records");
		}
	}
	
}
