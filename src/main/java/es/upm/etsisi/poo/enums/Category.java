package es.upm.etsisi.poo.enums;

import java.io.Serializable;

public class Category implements Serializable {

    private CategoryType type;

    private double discountMerch = 0;
    private double discountStationery = 0.05;
    private double discountClothes = 0.07;
    private double discountBook = 0.10;
    private double discountElectronic = 0.03;
    public Category() {
        this.type = null;
    }
    public Category(CategoryType type) {
        this.type = type;
    }

    public CategoryType getType() {
        return this.type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public double getDiscount() {
        double discount = 0;
        switch (getType()){
            case ELECTRONICS -> discount = discountElectronic;
            case STATIONERY -> discount = discountStationery;
            case CLOTHES -> discount = discountClothes;
            case BOOK -> discount = discountBook;
            case MERCH -> discount = discountMerch;
        }
        return discount;
    }

    void setDiscount(double discount) {
        switch (getType()){
            case ELECTRONICS -> discountElectronic = discount;
            case STATIONERY -> discountStationery = discount;
            case CLOTHES -> discountClothes = discount;
            case BOOK ->  discountBook = discount;
            case MERCH -> discountMerch = discount;
        }
    }

    @Override
    public String toString() {
        return "category: " + type;
    }
}