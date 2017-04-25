package com.product;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeightedProduct {

    private String id;
    private String name;
    private double weightKg;
    private BigDecimal weightPricePerKg;

    public WeightedProduct(final String id, final String name, final double weightKg, final BigDecimal weightPricePerKg) throws ValidationException {
        setId(id);
        setName(name);
        setWeightKg(weightKg);
        setWeightPricePerKg(weightPricePerKg);
    }

    public BigDecimal getCalulatedPrice() {
        BigDecimal weight = BigDecimal.valueOf(getWeightKg());
        BigDecimal calculateWeightPrice = weight.multiply(getWeightPricePerKg());
        return calculateWeightPrice.setScale(2, RoundingMode.CEILING);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) throws ValidationException {

        if (id == null || id.isEmpty()) {
            throw new ValidationException("Item id must be not null or an empty");
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) throws ValidationException {

        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name of product must not be empty or null");
        }

        this.name = name;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(final double weightKg) throws ValidationException {

        if (weightKg <= 0) {
            throw new ValidationException("Weight must be null or must not be negative value");
        }

        this.weightKg = weightKg;
    }

    public BigDecimal getWeightPricePerKg() {
        return weightPricePerKg;
    }

    public void setWeightPricePerKg(final BigDecimal weightPricePerKg) throws ValidationException {

        if (weightPricePerKg.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("WeightPricePerKg must be not be negative value");
        }

        this.weightPricePerKg = weightPricePerKg;
    }
}
