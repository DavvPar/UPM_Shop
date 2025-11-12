package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ComplexProduct extends Product{
    private Date expirationDate;
    private int people;
    private final int MAX_PEOPLE = 100;

    /**
     * Constructor of the Class Product.
     * Checks if the entering values are valid for a Product.
     *
     * @param ID       product ID
     * @param name     product name
     * @param price    product price
     */
    public ComplexProduct(int ID, String name, double price, Date expirationDate, int people) {
        super(ID, name, price);
        if(people <= 0 || people > MAX_PEOPLE)
            throw new IllegalArgumentException("El numero de participantes debe estar entre 1 y " + MAX_PEOPLE);
        //TODO REVISAR
        Date now = new Date();
        if(expirationDate.before(now))
            throw new IllegalArgumentException("La fecha de caducidad no puede ser anterior a hoy");
        this.expirationDate = expirationDate;
        this.people = people;
    }

    private boolean validatePlanningTime(String typeProduct, Date expirationDate){
        boolean validate = false;
        Date now = new Date();

        //Diferencia en milisegundos (es como Date guarda los datos)
        long difms = expirationDate.getTime() - now.getTime();

        //Convertimos a horas
        long difHours = difms / (1000 * 60 * 60);
        if(typeProduct.equalsIgnoreCase("addMeeting")){
            if(difHours >= 12)
                validate = true;
        }
        else if(typeProduct.equalsIgnoreCase("addFood")){
            //72h = 3 dias
            if(difHours >= 72)
                validate = true;
        }
        return validate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        if(people <= 0 || people > MAX_PEOPLE)
            throw new IllegalArgumentException("El numero de participantes debe estar entre 1 y " + MAX_PEOPLE);
        this.people = people;
    }

    public int getMAX_PEOPLE() {
        return MAX_PEOPLE;
    }


    //TODO SIN ACABAR
    @Override
    public String toString() {
        return super.toString().replace("}","") +
                "expirationDate=" + expirationDate +
                ", people=" + people +
                '}';
    }

    /*public String toString(){
        return "{Product" +
                ", id:" + getID() +
                ", name:'" + getName() + '\'' +
                ", price:" + String.format("%.2f", getPrice()) +
                ", expirationDate:" + expirationDate +
                ", people:" + people +
                '}';
    }*/
}
