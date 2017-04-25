package com.product;

import com.testdata.TestData;
import com.testdata.TestUtils;
import org.hamcrest.core.Is;
import org.junit.Test;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;


public class WeightedProductTest {


    @Test
    public void weightedProductCreateSuccess() throws ValidationException {

        // Setup
        Random randomGenerator = new Random();
        String id = UUID.randomUUID().toString();
        String productName = UUID.randomUUID().toString();
        double weightKg = TestUtils.round(randomGenerator.nextDouble());
        BigDecimal weightPricePerKg = BigDecimal.valueOf(randomGenerator.nextDouble());


        // Test
        WeightedProduct product = new WeightedProduct(id, productName, weightKg, weightPricePerKg);

        // Results
        assertNotNull(product);
        assertThat(product.getId(), Is.is(id));
        assertThat(product.getName(), Is.is(productName));
        assertThat(product.getWeightKg(), Is.is(weightKg));
        assertThat(product.getWeightPricePerKg(), Is.is(weightPricePerKg));
        assertThat(product.getCalulatedPrice(), Is.is(BigDecimal.valueOf(weightKg).multiply(weightPricePerKg).setScale(2, RoundingMode.CEILING)));
    }

    @Test
    public void weightedProductCreateSuccessZeroPrice() throws ValidationException {

        // Setup
        Random randomGenerator = new Random();
        String id = UUID.randomUUID().toString();
        String productName = UUID.randomUUID().toString();
        double weightKg = TestUtils.round(randomGenerator.nextDouble() + 0.01);
        BigDecimal weightPricePerKg = BigDecimal.ZERO;


        // Test
        WeightedProduct product = new WeightedProduct(id, productName, weightKg, weightPricePerKg);

        // Results
        assertNotNull(product);
        assertThat(product.getId(), Is.is(id));
        assertThat(product.getName(), Is.is(productName));
        assertThat(product.getWeightKg(), Is.is(weightKg));
        assertThat(product.getWeightPricePerKg(), Is.is(weightPricePerKg));
        assertThat(product.getCalulatedPrice(), Is.is(BigDecimal.ZERO.setScale(2, RoundingMode.CEILING)));

    }

    @Test
    public void weightedProductCreateFailureNullId() throws ValidationException {

        // Setup
        ValidationException validationException = null;

        // Test
        try {
            new WeightedProduct(null, "Oranges", Double.parseDouble("0.2"), BigDecimal.valueOf(Double.parseDouble("1.99")));
        } catch (ValidationException e){
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Item id must be not null or an empty"));
    }

    @Test
    public void weightedProductCreateFailureEmptyId() throws ValidationException {

        // Setup
        ValidationException validationException = null;

        // Test
        try {
            new WeightedProduct("", "Oranges", Double.parseDouble("0.2"), BigDecimal.valueOf(Double.parseDouble("1.99")));
        } catch (ValidationException e){
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Item id must be not null or an empty"));
    }

    @Test
    public void weightedProductCreateFailureNullName() throws ValidationException {

        // Setup
        ValidationException validationException = null;

        // Test
        try {
            new WeightedProduct("1", null, Double.parseDouble("0.2"), BigDecimal.valueOf(Double.parseDouble("1.99")));
        } catch (ValidationException e){
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Name of product must not be empty or null"));
    }

    @Test
    public void weightedProductCreateFailureEmptyName() throws ValidationException {

        // Setup
        ValidationException validationException = null;

        // Test
        try {
            new WeightedProduct("1", "", Double.parseDouble("0.2"), BigDecimal.valueOf(Double.parseDouble("1.99")));
        } catch (ValidationException e){
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Name of product must not be empty or null"));
    }

    @Test
    public void weightedProductCreateFailureZeroWeight() throws ValidationException {

        // Setup
        ValidationException validationException = null;

        // Test
        try {
            new WeightedProduct("1", "Oranges", Double.parseDouble("0"), BigDecimal.valueOf(Double.parseDouble("1.99")));
        } catch (ValidationException e){
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Weight must be null or must not be negative value"));
    }

    @Test
    public void weightedProductCreateFailureNegativeWeight() throws ValidationException {

        // Setup
        ValidationException validationException = null;

        // Test
        try {
            new WeightedProduct("1", "Oranges", Double.parseDouble("-2"), BigDecimal.valueOf(Double.parseDouble("1.99")));
        } catch (ValidationException e){
            validationException = e;
        }

        // Results
        assertNotNull(validationException);
        assertThat(validationException.getMessage(), Is.is("Weight must be null or must not be negative value"));
    }
}
