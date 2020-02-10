package net.zoostar.md.service;

import java.util.List;

import net.zoostar.md.model.Product;

public interface ProductService	{
	Product retrieveByAssetId(String assetId);
	List<Product> retrieveByDesc(String desc);
}
