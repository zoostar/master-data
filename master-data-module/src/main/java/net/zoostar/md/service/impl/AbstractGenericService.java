package net.zoostar.md.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import net.zoostar.md.service.GenericService;

public abstract class AbstractGenericService<T extends Persistable<ID>, ID extends Serializable>
implements GenericService<T, ID> {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected void preValidate(T persistable) {
		
	}
	
	public T create(T persistable) {
		log.info("Creating a new record: {}...", persistable);
		preValidate(persistable);
		return getGenericCrudRepository().save(persistable);
	}

	protected abstract CrudRepository<T, ID> getGenericCrudRepository();

}
