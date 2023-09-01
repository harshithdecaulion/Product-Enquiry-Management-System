package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.model.Product;

@Service
public class UserProductServiceImpl implements UserProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	
//	public List<Product> getAvailableProducts() {
//	       return productRepository.findByDeleted(false); 
//	   }
	
	public List<Product> getAvailableProducts() {
	    return productRepository.findByDeletedAndAvailability(false, true);
	}
	
}
