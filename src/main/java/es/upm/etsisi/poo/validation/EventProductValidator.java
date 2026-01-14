package es.upm.etsisi.poo.validation;
import es.upm.etsisi.poo.products.EventProduct;
public class EventProductValidator implements Validator<EventProduct> {

    @Override
    public void validate(EventProduct product) {
        if(product == null){
            throw new IllegalArgumentException("EventProduct cannot be null");
        }
        validatePeople(product);
        validateExpirationDate(product);
        validateBasePrice(product); //no debe calcular el precio
    }

    private void validatePeople(EventProduct product) {
        int people = product.getPeople();
        int max = product.getMAX_PEOPLE();

        if (people <= 0 || people > max) {
            throw new IllegalArgumentException(
                    "People must be between 1 and " + max
            );
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
