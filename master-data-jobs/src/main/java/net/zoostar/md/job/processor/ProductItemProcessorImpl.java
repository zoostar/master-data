package net.zoostar.md.job.processor;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Product;

@Slf4j
public class ProductItemProcessorImpl implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product product) throws Exception {
		log.info("Processing item: {}", product);
		
		if(product.isNew()) {
			product.setId(UUID.randomUUID());
		}
		
		log.info("{}", "Processing item complete.");
		return product;
	}

}
