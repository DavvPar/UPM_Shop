package es.upm.etsisi.poo;

public class Clients {
    private String name;
    private String DNI;
    private String email;
    private int cashId;

    public Clients(String name, String DNI, String email, int cashId){
        this.cashId = cashId;
        this.DNI = DNI;
        this.email = email;
        this.name = name;
    }
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getDNI(){return DNI;}
    public void setDNI(String DNI){this.DNI = DNI;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public int getCashId(){return cashId;}
    public void setCashId(int id){cashId = id;}

}
