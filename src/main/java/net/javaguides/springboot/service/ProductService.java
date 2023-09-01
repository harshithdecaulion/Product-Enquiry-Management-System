package net.javaguides.springboot.service;

import java.util.List;

import net.javaguides.springboot.model.Product;

public interface ProductService {

	List<Product> getAllUndeletedProducts();

	void softDeleteProduct(Long productId);

	Product getProductById(Long id);

	void saveProduct(Product product);

	void deleteProduct(Long id);

	void updateProduct(Product updatedProduct);


}