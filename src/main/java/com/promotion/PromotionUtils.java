package com.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import com.product.Product;

public class PromotionUtils {

	public static BigDecimal calculateCurrentTotalPrice(final Map<Product, Integer> basket) {

		BigDecimal totalPrice = BigDecimal.ZERO;

		for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
		    Product product = entry.getKey();
		    int quantity = entry.getValue();
			totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
		}

		return totalPrice.setScale(2, RoundingMode.CEILING);
	}
	
	public static BigDecimal calculateCurrentTotalPrice(final BigDecimal currentTotalPrice, final Map<Product, Integer> basket) {
		
		BigDecimal totalPrice = currentTotalPrice;
		
		if (currentTotalPrice == null || currentTotalPrice.equals(BigDecimal.ZERO)) {
			totalPrice = calculateCurrentTotalPrice(basket);
		}
		
		return totalPrice.setScale(2, RoundingMode.CEILING);
	}
}
