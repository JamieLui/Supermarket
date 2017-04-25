package com.promotion;

import com.product.Product;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class MultibuyPromotion implements Promotion {

	private Product product;
	private int amountRequiredForPromotion;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) throws ValidationException {

		if (product == null) {
			throw new ValidationException("Product can not be null");
		}

		this.product = product;
	}

	public int getAmountRequiredForPromotion() {
		return amountRequiredForPromotion;
	}

	public void setAmountRequiredForPromotion(final int amountRequiredForPromotion) throws ValidationException {
		if (amountRequiredForPromotion <= 0) {
			throw new ValidationException("For a product promotion please select how many purchases are required before promotion is valid");
		}

		this.amountRequiredForPromotion = amountRequiredForPromotion;
	}

	public MultibuyPromotion(final Product product, final int amountRequiredForPromotion) throws ValidationException {
		setProduct(product);
		setAmountRequiredForPromotion(amountRequiredForPromotion);
	}

	@Override
	public BigDecimal processPromotion(final BigDecimal currentPrice, final Map<Product, Integer> basket) {

		BigDecimal totalPrice = PromotionUtils.calculateCurrentTotalPrice(currentPrice, basket);
		
		for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
		    if (entry.getKey().getId().equals(getProduct().getId())) {
		    	
			    int quantity = entry.getValue();

			    int removeProductPriceThisManyTimes = quantity / getAmountRequiredForPromotion();

			    totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(removeProductPriceThisManyTimes)));

		    }
		}
		
		return totalPrice.setScale(2, RoundingMode.CEILING);
	}
}
