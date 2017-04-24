package com.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import javax.xml.bind.ValidationException;

import com.product.Product;

public class ProductPromotion implements Promotion {
	
	private Product product;
	private int amountRequiredForPromotion;
	private BigDecimal newProductPrice;
	
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
		if (amountRequiredForPromotion < 0) {
			throw new ValidationException("For a product promotion please select how many purchases are required before promotion is valid");
		}
		
		this.amountRequiredForPromotion = amountRequiredForPromotion;
	}

	public BigDecimal getNewProductPrice() {
		return newProductPrice;
	}

	public void setNewProductPrice(final BigDecimal newProductPrice) throws ValidationException {
		if (newProductPrice.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationException("Price must be more than zero");
		}
		
		this.newProductPrice = newProductPrice;
	}

	public ProductPromotion(final Product product, final int amountRequiredForPromotion, final BigDecimal newProductPrice) throws ValidationException {
		setProduct(product);
		setAmountRequiredForPromotion(amountRequiredForPromotion);
		setNewProductPrice(newProductPrice);
	}

	@Override
	public BigDecimal processPromotion(final BigDecimal currentPrice, final Map<Product, Integer> basket) {

		BigDecimal totalPrice = PromotionUtils.calculateCurrentTotalPrice(currentPrice, basket);
		
		for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
		    if (entry.getKey().getId().equals(getProduct().getId())) {
		    	
			    int quantity = entry.getValue();
			    
			    if (quantity >= getAmountRequiredForPromotion()) {
			    	totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
			    	totalPrice = totalPrice.add(getNewProductPrice().multiply(BigDecimal.valueOf(quantity)));
			    }
		    }
		}
		
		return totalPrice.setScale(2, RoundingMode.CEILING);
	}
}
