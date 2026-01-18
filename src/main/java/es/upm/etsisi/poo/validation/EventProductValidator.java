package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class EventProductValidator implements Validator {

    @Override
    public boolean validate(String[] params) {
        if (params.length == 0) {
            return false;
        }
        if (params.length == 6) {
            return validId(params[0]) && validName(params[1]) && validPrice(params[2]) &&
                    validateExpirationDate(params[3], params[4]) &&
                    validatePeople(params[5], 100);
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

    private boolean validName(String name){
        try{
            return name.length() < 100;
        }catch(IllegalArgumentException e){
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            LocalDate expDate = LocalDate.parse(expDateStr, formatter);
            LocalDateTime expirationDateTime = expDate.atStartOfDay();

            LocalDateTime now = LocalDateTime.now();

            long hoursDifference = ChronoUnit.HOURS.between(now, expirationDateTime);

            if (typeProduct.equalsIgnoreCase("Food")) {
                if (hoursDifference >= 72) {
                    valid = true;
                }
            } else if (typeProduct.equalsIgnoreCase("Meeting")) {
                if (hoursDifference >= 12) {
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
