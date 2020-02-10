package net.zoostar.md.web.controller.api;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.ToString;
import net.zoostar.md.model.Product;

@ToString
@RestController
@RequestMapping("/api/product")
public class ProductRestController extends AbstractGenericRestController<Product, UUID> {

}
