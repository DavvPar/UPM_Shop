package es.upm.etsisi.poo.validation;

import es.upm.etsisi.poo.products.Service;

public class ServiceValidator implements Validator<Service> {
    @Override
    public void validate(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }

        validateType(service);
        validateExpirationDate(service);
        validateId(service);
    }

    private void validateType(Service service) {
        if (service.getType() == null) {
            throw new IllegalArgumentException("Service type is required");
        }
    }

    private void validateExpirationDate(Service service) {
        String date = service.getExpirationDate();

        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Expiration date is required");
        }
    }

    private void validateId(Service service) {
        if (service.getID() == null || service.getID().isBlank()) {
            throw new IllegalArgumentException("Service ID is required");
        }
    }
}
