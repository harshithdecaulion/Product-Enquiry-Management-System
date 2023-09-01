package net.javaguides.springboot.service;

import java.util.List;

import net.javaguides.springboot.model.Product;

public interface UserProductService {
	
	List<Product> getAvailableProducts();
}