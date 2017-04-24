package com.checkout;

import javax.xml.bind.ValidationException;

import com.product.Product;
import com.product.WeightedProduct;
import com.promotion.MultibuyPromotion;
import com.promotion.ProductPromotion;
import org.hamcrest.core.Is;
import org.junit.Test;

import com.promotion.Promotion;
import com.testdata.TestData;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SupermarketTests {

    @Test
    public void supermarketTest() throws ValidationException {

        // Setup
        List<Promotion> promotions = new ArrayList<Promotion>();
        Product productBeans = TestData.generateTestBeans();
        Product productCoke = TestData.generateTestCoke();
        WeightedProduct productOranages = TestData.generateTestProductOranges();
        promotions.add(new MultibuyPromotion(productBeans, 3));
        promotions.add(new ProductPromotion(productCoke, 2, BigDecimal.valueOf(0.5)));
        Checkout checkout = new Checkout(promotions);

        // Test
        checkout.scan(productBeans);
        checkout.scan(productBeans);
        checkout.scan(productBeans);
        checkout.scan(productCoke);
        checkout.scan(productCoke);
        checkout.scan(productOranages);

        // Results
        assertNotNull(checkout);
        assertThat(checkout.getPromotionalTotal(), Is.is(BigDecimal.valueOf(2.4).setScale(2, BigDecimal.ROUND_CEILING)));
    }
}
