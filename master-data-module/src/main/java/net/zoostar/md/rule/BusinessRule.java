package net.zoostar.md.rule;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public interface BusinessRule<T extends Persistable<ID>, ID extends Serializable> {
	void apply(T persistable) throws BusinessRuleException;
}
