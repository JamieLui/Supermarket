package com.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.Product;
import com.product.WeightedProduct;
import com.promotion.Promotion;

import javax.xml.bind.ValidationException;

public class Checkout {
	
	private List<Promotion> promotions;
	
	// Map holding <product and quantity>
	private Map<Product, Integer> basket;
	
	public Checkout(final List<Promotion> promotions) {
		basket = new HashMap<Product, Integer>();
		setPromotions(promotions);
	}
	
	public Map<Product, Integer> getBasket() {
		return basket;
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}
	
	public void setPromotions(final List<Promotion> promotions) {
		this.promotions = promotions;
	}
	
	public BigDecimal getPromotionalTotal() {
		
		BigDecimal processedCurrentTotal = BigDecimal.ZERO;
		
		for (Promotion promotion : getPromotions()) {
			processedCurrentTotal = promotion.processPromotion(processedCurrentTotal, getBasket());
		}
		
		return processedCurrentTotal.setScale(2, RoundingMode.CEILING);
	}
	
	public void scan(final Product product) {
		
		boolean productExistsInBasket = false;
		
		for (Map.Entry<Product, Integer> entry : getBasket().entrySet()) {
		    if (entry.getKey().getId().equals(product.getId())) {
		    	getBasket().put(product, entry.getValue() + 1);
		    	productExistsInBasket = true;
		    }
		}
		
		if (!productExistsInBasket) {
			getBasket().put(product, 1);
		}
	}

	public void scan(final WeightedProduct weightedProduct) throws ValidationException{
		Product product = new Product(weightedProduct.getId(), weightedProduct.getName(), weightedProduct.getCalulatedPrice());
		getBasket().put(product, 1);
	}

	public void removeProduct(final Product product) {
		for (Map.Entry<Product, Integer> entry : getBasket().entrySet()) {
		    if (entry.getKey().getId().equals(product.getId())) {
				if ((entry.getValue() - 1) > 0) {
					getBasket().put(product, entry.getValue() - 1);
				} else {
					getBasket().remove(product);
				}
		    }
		}
	}
}
