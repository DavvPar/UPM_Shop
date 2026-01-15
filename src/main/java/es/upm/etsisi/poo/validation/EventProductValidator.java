package es.upm.etsisi.poo.validation;
import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.ProductType;
import es.upm.etsisi.poo.products.EventProduct;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    public static boolean validatePlanningTime(ProductType typeProduct, String expirationDate) {
        //YYYY-MM-DD-HH:MM
        String[] currentTimeString = Utils.getTime().trim().split("[:-]");
        String[] time = expirationDate.trim().split("[:-]");
        LocalDateTime now =  LocalDateTime.of(Integer.parseInt(currentTimeString[0]),Integer.parseInt(currentTimeString[1]),
                Integer.parseInt(currentTimeString[2]),Integer.parseInt(currentTimeString[3]),Integer.parseInt(currentTimeString[4]));
        LocalDateTime Date = LocalDateTime.of(Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]),
                0,0);
        long HourD = ChronoUnit.HOURS.between(now,Date);
        boolean isValid = true;
        if (typeProduct == ProductType.Food) {
            if (HourD < 72) {
                System.out.println("Error adding product");
                isValid = false;
            }
        }else{
            if (HourD< 12){
                System.out.println("Error adding meeting");
                isValid=false;
            }
        }
        return isValid;
    }
    private void validatePeople(EventProduct product) {
        int people = product.getPeople();
        int max = product.getMAX_PEOPLE();

        if (people < 0 || people > max) {
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
