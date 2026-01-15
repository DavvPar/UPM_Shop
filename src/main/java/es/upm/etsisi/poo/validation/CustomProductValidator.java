package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.enums.CategoryType;
import es.upm.etsisi.poo.products.ProductList;

public class CustomProductValidator implements Validator{
    @Override
    public boolean validate(String[] params) {
        if (params.length == 0){
            return false;
        }
        if (params.length == 3){
            return validId(params[0]) && validCategoryType(params[1]) && validPrice(params[2]);
        }
        if (params.length == 4){
            return validId(params[0]) && validCategoryType(params[1])
                    && validPrice(params[2]) && validMaxPers(params[3]);
        }
        return false;
    }

    private boolean validPrice(String priceStr){
        try {
            double price = Double.parseDouble(priceStr);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validId(String idStr){
        try {
            int id = Integer.parseInt(idStr);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validCategoryType(String type){
        try {
            CategoryType.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean validMaxPers (String maxPersStr){
        try {
            int maxPers = Integer.parseInt(maxPersStr);
            return maxPers >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
