package com.testdata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import javax.xml.bind.ValidationException;

import com.product.Product;
import com.product.WeightedProduct;
import com.promotion.ProductPromotion;

public class TestData {

	public static double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static Product generateTestBeans() throws ValidationException {
		Product product = new Product("001", "Beans", BigDecimal.valueOf(Double.parseDouble("0.5")));
		return product;
	}
	
	public static Product generateTestCoke() throws ValidationException {
		Product product = new Product("002", "Coke", BigDecimal.valueOf(Double.parseDouble("0.7")));
		return product;
	}
	
	public static WeightedProduct generateTestProductOranges() throws ValidationException {
		WeightedProduct product = new WeightedProduct("003", "Oranges", Double.parseDouble("0.2"), BigDecimal.valueOf(Double.parseDouble("1.99")));
		return product;
	}


	public static Product selectRandomTestProduct() throws ValidationException {

		Random random = new Random();
		int selectedTestProduct = random.nextInt(2);

		if (selectedTestProduct == 0) {
			return generateTestBeans();
		}

		return generateTestCoke();
	}
	
	public static ProductPromotion generateTestRandomProductPromotion() throws ValidationException {
		
		Random random = new Random();
		Product randomProduct = selectRandomTestProduct();
		BigDecimal randomPrice = BigDecimal.valueOf(Double.valueOf(randomProduct.getPrice().toString()) - 0.1);
		
		ProductPromotion productPromotion = new ProductPromotion(randomProduct, random.nextInt(5) + 1, randomProduct.getPrice().subtract(randomPrice));
		return productPromotion;
	}
}
