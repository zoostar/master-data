package net.zoostar.md.web.controller.api;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.ToString;
import net.zoostar.md.model.Product;
import net.zoostar.md.service.GenericService;
import net.zoostar.md.service.ProductService;

@ToString
@RestController
@RequestMapping("/api/product")
public class ProductRestController extends AbstractGenericRestController<Product, UUID> {
	
	protected Job jobIngestProduct;
	
	private ProductService genericManager;
	
	@Autowired
	public void setGenericManager(ProductService genericManager) {
		this.genericManager = genericManager;
	}
	
	@Autowired
	public void setJobIngestProduct(Job jobIngestProduct) {
		log.debug("setJobIngestProduct({})", jobIngestProduct);
		this.jobIngestProduct = jobIngestProduct;
	}

	@GetMapping(path = "/ingest")
	public ResponseEntity<JobExecution> ingest() throws JobExecutionAlreadyRunningException,
	JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		return triggerIngestionJob(jobIngestProduct, new JobParameters());
	}
	
	@Override
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> create(@RequestBody Product product) {
		return super.create(product);
	}
	
	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> update(@RequestBody Product product) {
		return super.update(product);
	}
	
	@GetMapping(path = "/delete")
	public ResponseEntity<Product> delete(@RequestParam(name = "assetId", required = true) String assetId) {
		return new ResponseEntity<>(getProductManager().deleteByAssetId(assetId), HttpStatus.OK);
	}

	@Override
	public GenericService<Product, UUID> getGenericManager() {
		return getProductManager();
	}

	public ProductService getProductManager() {
		return genericManager;
	}
}
