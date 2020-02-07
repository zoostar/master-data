package net.zoostar.md.web.controller.api;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;
import org.springframework.http.ResponseEntity;

public abstract class AbstractGenericController<T extends Persistable<ID>, ID extends Serializable> {

	protected ResponseEntity<T> create(T persistable) {
		return null;
	}
}
