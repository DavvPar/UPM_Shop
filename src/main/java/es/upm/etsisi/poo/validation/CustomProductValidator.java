package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.products.CustomProduct;

public class CustomProductValidator implements Validator<CustomProduct> {
    @Override
    public void validate(CustomProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("CustomProduct cannot be null");
        }

        validateCategory(product);
        validateMaxPersonalization(product);
        validatePrice(product);
    }

    private void validateCategory(CustomProduct product) {
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Category is required");
        }
    }

    private void validateMaxPersonalization(CustomProduct product) {
        int max = product.getMaxPers();

        if (max < 0) {
            throw new IllegalArgumentException("Max personalizations cannot be negative");
        }

        int current = product.getPersonalization().isEmpty()
                ? 0
                : product.getPersonalization().split("--p").length - 1;

        if (current > max) {
            throw new IllegalArgumentException(
                    "Exceeded max personalization limit (" + max + ")"
            );
        }
    }

    private void validatePrice(CustomProduct product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
}
