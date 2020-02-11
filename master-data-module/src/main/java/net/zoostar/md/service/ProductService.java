package net.zoostar.md.service;

import java.util.List;
import java.util.UUID;

import net.zoostar.md.model.Product;

public interface ProductService	extends GenericService<Product, UUID> {
	Product retrieveByAssetId(String assetId);
	List<Product> retrieveByDesc(String desc);
	Product deleteByAssetId(String assetId);
}
