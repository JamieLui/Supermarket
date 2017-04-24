package com.promotion;

import com.product.Product;
import com.testdata.TestData;
import org.hamcrest.core.Is;
import org.junit.Test;

import javax.xml.bind.ValidationException;
import java.util.Random;

import static org.junit.Assert.*;


public class MultibuyPromotionTest {

    @Test
    public void productPromotionTestCreateObjectSuccess() throws ValidationException {

        // Setup
        Random random = new Random();
        Product product = TestData.generateTestBeans();
        int amountRequiredForPromotion = random.nextInt(10) + 1;

        // Test
        MultibuyPromotion productPromotion = new MultibuyPromotion(product, amountRequiredForPromotion);

        // Results
        assertNotNull(productPromotion);
        assertThat(productPromotion.getAmountRequiredForPromotion(), Is.is(amountRequiredForPromotion));
        assertThat(productPromotion.getProduct(), Is.is(product));
    }

    @Test
    public void productPromotionTestCreateObjectFailZeroAmountForPromotion() throws ValidationException {

        // Setup
        Product product = TestData.generateTestBeans();
        int amountRequiredForPromotion = 0;
        ValidationException validationException = null;

        // Test
        try {
            new MultibuyPromotion(product, amountRequiredForPromotion);
        } catch (ValidationException e) {
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("For a product promotion please select how many purchases are required before promotion is valid"));
    }

    @Test
    public void productPromotionTestCreateObjectFailNullProduct() throws ValidationException {

        // Setup
        Random random = new Random();
        Product product = null;
        int amountRequiredForPromotion = random.nextInt(10) + 1;
        ValidationException validationException = null;

        // Test
        try {
            new MultibuyPromotion(product, amountRequiredForPromotion);
        } catch (ValidationException e) {
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Product can not be null"));
    }

}