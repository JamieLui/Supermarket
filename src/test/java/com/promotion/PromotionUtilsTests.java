package com.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.xml.bind.ValidationException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.product.Product;
import com.testdata.TestData;

public class PromotionUtilsTests {

	@Test
	public void promotionUtilsCreateObject() throws ValidationException {
		
		// Setup
		PromotionUtils promotionUtils = null;
		
		// Test
		promotionUtils = new PromotionUtils();
		
		// Results
		assertNotNull(promotionUtils);
	}
	
	@Test
	public void promotionUtilsCalculateCurrentTotalPriceProcessOneArguement() throws ValidationException {
		
		// Setup
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestBeans(), 1);
		basket.put(TestData.generateTestCoke(), 1);

		// Test
		BigDecimal calculateTotalPrice = PromotionUtils.calculateCurrentTotalPrice(basket);
		
		// Results
		assertThat(calculateTotalPrice, Is.is(BigDecimal.valueOf(1.20).setScale(2, RoundingMode.CEILING)));
	}

	@Test
	public void promotionUtilsCalculateCurrentTotalPriceProcessTwoArguementsNull() throws ValidationException {
		
		// Setup
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestBeans(), 1);
		basket.put(TestData.generateTestCoke(), 1);

		// Test
		BigDecimal calculateTotalPrice = PromotionUtils.calculateCurrentTotalPrice(null, basket);
		
		// Results
		assertThat(calculateTotalPrice, Is.is(BigDecimal.valueOf(1.20).setScale(2, RoundingMode.CEILING)));
	}
	
	@Test
	public void promotionUtilsCalculateCurrentTotalPriceProcessTwoArguementsZero() throws ValidationException {
		
		// Setup
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestBeans(), 1);
		basket.put(TestData.generateTestCoke(), 1);

		// Test
		BigDecimal calculateTotalPrice = PromotionUtils.calculateCurrentTotalPrice(BigDecimal.ZERO, basket);
		
		// Results
		assertThat(calculateTotalPrice, Is.is(BigDecimal.valueOf(1.20).setScale(2, RoundingMode.CEILING)));
	}
	
	@Test
	public void promotionUtilsCalculateCurrentTotalPriceDontProcessTwoArguements() throws ValidationException {
		
		// Setup
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestBeans(), 1);
		basket.put(TestData.generateTestCoke(), 1);

		BigDecimal forceTotal = BigDecimal.TEN;
		
		// Test
		BigDecimal calculateTotalPrice = PromotionUtils.calculateCurrentTotalPrice(forceTotal, basket);
		
		// Results
		assertThat(calculateTotalPrice, Is.is(forceTotal.setScale(2, RoundingMode.CEILING)));
	}
}
