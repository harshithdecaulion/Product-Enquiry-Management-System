package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByDeletedFalse();


	List<Product> findByDeleted(boolean b);


	List<Product> findByDeletedAndAvailability(boolean b, boolean c);

}
