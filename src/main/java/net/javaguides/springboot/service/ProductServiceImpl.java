package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
		@Autowired
	    public ProductServiceImpl(ProductRepository productRepository) {
	        this.productRepository = productRepository;
	    }

		@Override
		 public List<Product> getAllUndeletedProducts() {
		        return productRepository.findByDeletedFalse();
		    }


		
		 @Override
	       public void softDeleteProduct(Long prodId) {
	           Optional<Product> optionalProduct = productRepository.findById(prodId);

	           if (optionalProduct.isPresent()) {
	               Product product = optionalProduct.get();
	               product.setDeleted(true);
	               productRepository.save(product);
	           } else {
	               System.out.println("Id Not Found");
	           }
	       }

	    @Override
		public Product getProductById(Long id) {
	        return productRepository.findById(id).orElse(null);
	    }

	    @Override
		public void saveProduct(Product product) {
	        productRepository.save(product);
	    }

	    @Override
		public void deleteProduct(Long id) {
	        productRepository.deleteById(id);
	    }
	    
	    @Override
	    public void updateProduct(Product updatedProduct) {
	        Product existingProduct = getProductById(updatedProduct.getProdId());
	        if (existingProduct != null) {
	            existingProduct.setNoOfProductsInStock(updatedProduct.getNoOfProductsInStock());
	            existingProduct.setAvailability(updatedProduct.getAvailability());
	            productRepository.save(existingProduct);
	        }
	    }

	}

