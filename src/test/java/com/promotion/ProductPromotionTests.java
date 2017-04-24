package com.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.xml.bind.ValidationException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.product.Product;
import com.testdata.TestData;

public class ProductPromotionTests {

	@Test
	public void productPromotionTestCreateObjectSuccess() throws ValidationException {
		
		// Setup
		Random random = new Random();
		Product product = TestData.generateTestBeans();
		BigDecimal productCurrentPrice = product.getPrice();
		int amountRequiredForPromotion = random.nextInt(10) + 1;
		BigDecimal newProductPrice = productCurrentPrice.subtract(BigDecimal.valueOf(0.1));
		
		// Test
		ProductPromotion productPromotion = new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
	
		// Results
		assertNotNull(productPromotion);
		assertThat(productPromotion.getAmountRequiredForPromotion(), Is.is(amountRequiredForPromotion));
		assertThat(productPromotion.getNewProductPrice(), Is.is(newProductPrice));
		assertThat(productPromotion.getProduct(), Is.is(product));
	}
	
	@Test
	public void productPromotionTestCreateObjectSuccessZeroAmountRequired() throws ValidationException {
		
		// Setup
		Random random = new Random();
		Product product = TestData.generateTestBeans();
		BigDecimal productCurrentPrice = product.getPrice();
		int amountRequiredForPromotion = 0;
		BigDecimal newProductPrice = productCurrentPrice.subtract(BigDecimal.valueOf(0.1));
		
		// Test
		ProductPromotion productPromotion = new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
	
		// Results
		assertNotNull(productPromotion);
		assertThat(productPromotion.getAmountRequiredForPromotion(), Is.is(amountRequiredForPromotion));
		assertThat(productPromotion.getNewProductPrice(), Is.is(newProductPrice));
		assertThat(productPromotion.getProduct(), Is.is(product));	
	}
	
	@Test
	public void productPromotionTestCreateObjectFailureNullProduct() throws ValidationException {
		
		// Setup
		Random random = new Random();
		Product product = null;
		int amountRequiredForPromotion = random.nextInt(10) + 1;
		BigDecimal newProductPrice = BigDecimal.valueOf(random.nextInt());
		ValidationException validationException = null;
		
		// Test
		try {
			new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Product can not be null"));
	}
	
	@Test
	public void productPromotionTestCreateObjectFailureNegativeAmountRequired() throws ValidationException {
		
		// Setup
		Random random = new Random();
		Product product = TestData.generateTestBeans();
		BigDecimal productCurrentPrice = product.getPrice();
		int amountRequiredForPromotion = -1;
		BigDecimal newProductPrice = productCurrentPrice.subtract(BigDecimal.valueOf(0.1));
		ValidationException validationException = null;
		
		// Test
		try {
			new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("For a product promotion please select how many purchases are required before promotion is valid"));
	}
	
	@Test
	public void productPromotionTestCreateObjectFailureNegativeNewPrice() throws ValidationException {
		
		// Setup
		Random random = new Random();
		Product product = TestData.generateTestBeans();
		int amountRequiredForPromotion = random.nextInt(10) + 1;
		BigDecimal newProductPrice = BigDecimal.valueOf(-10);
		ValidationException validationException = null;
		
		// Test
		try {
			new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Results
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Price must be more than zero"));
	}
	
	@Test
	public void productPromotionTestProcessApplicablePromotion() throws ValidationException {
		
		// Setup
		Product product = TestData.generateTestBeans();
		int amountRequiredForPromotion = 2;
		BigDecimal newProductPrice = BigDecimal.valueOf(0.10);
		
		ProductPromotion productPromotion = new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
		
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(product, amountRequiredForPromotion);
		basket.put(TestData.generateTestCoke(), 1);
		basket.put(TestData.generateTestBeans(), 1);
		
		BigDecimal totalCostBeforePromotions = PromotionUtils.calculateCurrentTotalPrice(basket);
		
		// Test
		BigDecimal totalCostAfterPromotion = productPromotion.processPromotion(totalCostBeforePromotions, basket);
		
		// Result
		assertThat(totalCostAfterPromotion, Is.is(BigDecimal.valueOf(1.40).setScale(2, RoundingMode.CEILING)));
	}
	
	@Test
	public void productPromotionTestProcessNotApplicablePromotionNotEnoughQuantities() throws ValidationException {
		
		// Setup
		Product product = TestData.generateTestBeans();
		int amountRequiredForPromotion = 2;
		BigDecimal newProductPrice = BigDecimal.valueOf(0.10);
		
		ProductPromotion productPromotion = new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
		
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(product, 1);
		basket.put(TestData.generateTestCoke(), 1);
		basket.put(TestData.generateTestBeans(), 1);
		
		BigDecimal totalCostBeforePromotions = PromotionUtils.calculateCurrentTotalPrice(basket);
		
		// Test
		BigDecimal totalCostAfterPromotion = productPromotion.processPromotion(totalCostBeforePromotions, basket);
		
		// Result
		assertThat(totalCostAfterPromotion, Is.is(totalCostBeforePromotions));	
	}
	
	@Test
	public void productPromotionTestProcessNotApplicablePromotionNoProductsInBasket() throws ValidationException {
		
		// Setup
		Product product = TestData.generateTestBeans();
		int amountRequiredForPromotion = 2;
		BigDecimal newProductPrice = BigDecimal.valueOf(0.10);
		
		ProductPromotion productPromotion = new ProductPromotion(product, amountRequiredForPromotion, newProductPrice);
		
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestCoke(), 3);
		basket.put(TestData.generateTestBeans(), 1);
		
		BigDecimal totalCostBeforePromotions = PromotionUtils.calculateCurrentTotalPrice(basket);
		
		// Test
		BigDecimal totalCostAfterPromotion = productPromotion.processPromotion(totalCostBeforePromotions, basket);
		
		// Result
		assertThat(totalCostAfterPromotion, Is.is(totalCostBeforePromotions));	
	}
}
