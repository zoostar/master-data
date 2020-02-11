package net.zoostar.md.rule;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

import net.zoostar.md.rule.exception.BusinessRuleException;

public interface BusinessRule<T extends Persistable<ID>, ID extends Serializable> {
	void apply(T persistable) throws BusinessRuleException;
}
