package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class EventProductValidator implements Validator {

    @Override
    public boolean validate(String[] params) {
        if (params.length == 0) {
            return false;
        }
        if (params.length == 5) {
            return validId(params[0]) && validPrice(params[1]) &&
                    validateExpirationDate(params[2], params[3]) &&
                    validatePeople(params[4], 100);
        }
        return false;
    }

    private boolean validId(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validPrice(String priceStr) {
        try {
            double price = Double.parseDouble(priceStr);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateExpirationDate(String expDateStr, String typeProduct) {
        try {
            boolean valid = false;
            if (Utils.validDate(expDateStr)) {
                valid = false;
            }
            LocalDate expDate = LocalDate.parse(expDateStr);
            LocalDateTime expirationDateTime = expDate.atStartOfDay();

            LocalDateTime now = LocalDateTime.now();

            long hoursDifference = ChronoUnit.HOURS.between(now, expirationDateTime);

            if (typeProduct.equalsIgnoreCase("Food")) {
                if (hoursDifference < 72) {
                    valid = true;
                }
            } else if (typeProduct.equalsIgnoreCase("Meeting")) {
                if (hoursDifference < 12) {
                    valid = true;
                }
            }
            return valid;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean validatePeople(String peopleStr, int maxPeople) { //???
        try {
            int people = Integer.parseInt(peopleStr);
            return people >= 0 && people <= maxPeople;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
