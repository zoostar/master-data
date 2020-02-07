package net.zoostar.md.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import net.zoostar.md.model.Product;

public interface ProductRepository extends CrudRepository<Product, UUID> {
	Product findByAssetId(String assetId);
	List<Product> findByDesc(String desc);
}
