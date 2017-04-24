package com.promotion;

import java.math.BigDecimal;
import java.util.Map;

import com.product.Product;

public interface Promotion {
	
	public BigDecimal processPromotion(final BigDecimal currentPrice, final Map<Product, Integer> basket);
	
}
