package net.zoostar.md.service;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public interface GenericService<T extends Persistable<ID>, ID extends Serializable> {

	T create(T persistable);

}
