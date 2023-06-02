package com.product.productapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.productapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value="SELECT * FROM product WHERE name LIKE %:name%", nativeQuery=true)
	List<Product> findByNameLike(@Param("name") Optional<String> name);
	
}
