package net.zoostar.md.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.ToString;
import net.zoostar.md.dao.ProductRepository;
import net.zoostar.md.exception.RecordNotFoundException;
import net.zoostar.md.model.Product;
import net.zoostar.md.service.ProductService;

@ToString
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl extends AbstractGenericService<Product, UUID> implements ProductService {

	private ProductRepository productRepository;
	
	@Autowired
	public void setGenericCrudRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	protected CrudRepository<Product, UUID> getGenericCrudRepository() {
		return productRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public Product update(Product product) {
		log.info("Updating product: {}...", product);
		Product entity = retrieveByAssetId(product.getAssetId());
		entity.setDesc(product.getDesc());
		entity.setSku(product.getSku());
		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(UUID id) {
		productRepository.deleteById(id);		
	}

	@Override
	public Product retrieveByAssetId(String assetId) {
		log.info("retrieveByAssetId({})", assetId);
		Product product = productRepository.findByAssetId(assetId);
		if(product == null) {
			throw new RecordNotFoundException("Record not found for assetId: " + assetId);
		}
		return product;
	}

	@Override
	public List<Product> retrieveByDesc(String desc) {
		log.info("retrieveByDesc({})", desc);
		List<Product> products = productRepository.findByDesc(desc);
		if(products == null) {
			products = Collections.emptyList();
		}
		return products;
	}

	@Override
	public Product deleteByAssetId(String assetId) {
		Product product = retrieveByAssetId(assetId);
		delete(product.getId());
		return product;
	}

}
