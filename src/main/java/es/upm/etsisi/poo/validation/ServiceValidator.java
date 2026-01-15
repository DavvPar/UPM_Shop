package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.Utils;
import es.upm.etsisi.poo.enums.ServiceType;

public class ServiceValidator implements Validator {

    @Override
    public boolean validate(String[] params) {
        return (validateExpirationDate(params[0]) && validateType(params[1]));
    }

    private boolean validateType(String type){
        try {
            ServiceType.valueOf(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean validateExpirationDate(String date){
        return Utils.validDate(date);
    }
}
