package net.zoostar.md.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.zoostar.md.dao.ProductRepository;
import net.zoostar.md.model.Product;

@Service
public class ProductServiceImpl extends AbstractGenericService<Product, UUID> {

	private ProductRepository productRepository;
	
	@Autowired
	public void setGenericCrudRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	protected CrudRepository<Product, UUID> getGenericCrudRepository() {
		return productRepository;
	}

}
