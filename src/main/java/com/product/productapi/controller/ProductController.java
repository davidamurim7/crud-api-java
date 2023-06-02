package com.product.productapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.productapi.model.Product;
import com.product.productapi.repository.ProductRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/getProducts")
	public List<Product> list(@RequestParam("filter") Optional<String> name) {
		if(!name.isPresent()) {
			return productRepository.findAll();
		}else {
			return productRepository.findByNameLike(name);
		}
	}
	
	@GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getById(@PathVariable(value = "id") long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
            return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PostMapping("/saveProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public Product insert(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@PutMapping("/saveProduct/{id}")
	public ResponseEntity<Product> Put(@PathVariable(value = "id") long id, @Validated @RequestBody Product newProduct)
    {
        Optional<Product> oldProduct = productRepository.findById(id);
        if(oldProduct.isPresent()){
        	Product product = oldProduct.get();
        	product.setName(newProduct.getName());
        	product.setUnit(newProduct.getUnit());
        	product.setQuantity(newProduct.getQuantity());
        	product.setCost(newProduct.getCost());
        	product.setPerishable(newProduct.isPerishable());
        	product.setValidity(newProduct.getValidity());
        	product.setFabrication(newProduct.getFabrication());
        	productRepository.save(product);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
        	productRepository.delete(product.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
