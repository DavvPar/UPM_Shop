package es.upm.etsisi.poo.validation;
import es.upm.etsisi.poo.products.EventProduct;
public class EventProductValidator implements Validator {

    @Override


    /**
     * public void validate(EventProduct product) {
     *         if(product == null){
     *             throw new IllegalArgumentException("EventProduct cannot be null");
     *         }
     *         validatePeople(product);
     *         validateExpirationDate(product);
     *         validateBasePrice(product); //no debe calcular el precio
     *     }
     */

    public boolean validate(String[] params) {
        return true;
    }

    private boolean validatePeople(String peopleStr, int maxPeople) {
        try {
            int people = Integer.parseInt(peopleStr);
            return people >= 0 && people <= maxPeople;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void validateExpirationDate(EventProduct product) {
        String date = product.getExpirationDate();

        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Expiration date is required");
        }
    }

    private void validateBasePrice(EventProduct product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
}
