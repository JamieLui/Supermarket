package com.product;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.ValidationException;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTests {
	
	@Test
	public void productCreateSuccess() throws ValidationException {
		
		// Setup
		Random randomGenerator = new Random();
		String productId = UUID.randomUUID().toString();
		String productName = UUID.randomUUID().toString();
		BigDecimal productPrice = BigDecimal.valueOf(randomGenerator.nextDouble());
		
		// Test
		Product product = new Product(productId, productName, productPrice);
		
		// Results
		assertNotNull(product);
		assertThat(product.getId(), Is.is(productId));
		assertThat(product.getName(), Is.is(productName));
		assertThat(product.getPrice(), Is.is(productPrice));
	}
	
	@Test
	public void productCreateSuccessZeroPrice() throws ValidationException {
		
		// Setup
		String productId = UUID.randomUUID().toString();
		String productName = UUID.randomUUID().toString();
		BigDecimal productPrice = BigDecimal.valueOf(Double.parseDouble("0"));
		
		// Test
		Product product = new Product(productId, productName, productPrice);
		
		// Results
		assertNotNull(product);
		assertThat(product.getPrice(), Is.is(productPrice));
	}
	
	@Test
	public void productCreateFailureNullId() throws ValidationException {
		
		// Setup
		ValidationException validationException = null;
		
		// Test
		try {
			new Product(null, "Beans", BigDecimal.valueOf(Double.parseDouble("0.5")));
		} catch (ValidationException e){
			validationException = e;
		}
		
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Item id must be not null or an empty"));
	}
	
	@Test
	public void productCreateFailureEmptyId() throws ValidationException {
		
		// Setup
		ValidationException validationException = null;
		
		// Test
		try {
			new Product("", "Beans", BigDecimal.valueOf(Double.parseDouble("0.5")));
		} catch (ValidationException e){
			validationException = e;
		}
		
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Item id must be not null or an empty"));
	}
	
	@Test
	public void productCreateFailureNullName() throws ValidationException {

		// Setup
		ValidationException validationException = null;
		
		// Test
		try {
			new Product("001", null, BigDecimal.valueOf(Double.parseDouble("0.5")));
		} catch (ValidationException e){
			validationException = e;
		}
		
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Name of product must not be empty or null"));
	}
	
	@Test
	public void productCreateFailureEmptyName() throws ValidationException {
		
		// Setup
		ValidationException validationException = null;
		
		// Test
		try {
			new Product("001", "", BigDecimal.valueOf(Double.parseDouble("0.5")));
		} catch (ValidationException e){
			validationException = e;
		}
		
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Name of product must not be empty or null"));
	}
	
	@Test
	public void productCreateFailureMinusPrice() throws ValidationException {
		
		// Setup
		ValidationException validationException = null;
		
		// Test
		try {
			new Product("001", "Beans", BigDecimal.valueOf(Double.parseDouble("-0.5")));
		} catch (ValidationException e){
			validationException = e;
		}
		
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Price must be not be negative value"));
	}
}
