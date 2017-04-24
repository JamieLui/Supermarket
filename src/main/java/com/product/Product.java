package com.product;

import java.math.BigDecimal;

import javax.xml.bind.ValidationException;


public class Product {
	
	private String id;
	private String name;
	private BigDecimal price;
	
	public Product(final String id, final String name, final BigDecimal price) throws ValidationException {
		setId(id);
		setName(name);
		setPrice(price);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(final String id) throws ValidationException {
		
		if (id == null || id.isEmpty()) {
			throw new ValidationException("Item id must be not null or an empty");
		}
		
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) throws ValidationException {
		
		if (name == null || name.isEmpty()) {
			throw new ValidationException("Name of product must not be empty or null");
		}
		
		this.name = name;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(final BigDecimal price) throws ValidationException {
		
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationException("Price must be not be negative value");
		}
		
		this.price = price;
	}
	
}
