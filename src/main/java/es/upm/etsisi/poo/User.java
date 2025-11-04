package es.upm.etsisi.poo;

public class User {
    /**
     * Name of the user
     */
    private String name;
    /**
     * Email of the user
     */
    private String email;

    /**
     * Constructor of the class
     * @param name
     * @param email
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName(){return name;}
    /**
     * Setter for name
     * @param name
     */
    public void setName(String name){this.name = name;}
    /**
     * Getter for name
     * @return
     */
    public String getEmail(){return email;}
    /**
     * Setter for name
     * @param email
     */
    public void setEmail(String email){this.email = email;}
}
