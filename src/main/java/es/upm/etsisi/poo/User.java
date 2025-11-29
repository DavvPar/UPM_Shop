package es.upm.etsisi.poo;

/**
 * User is an abstract class created to then declare new types
 * of user such as clients and cashiers, that extend from User
 */
public abstract class User {
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
     * @param name name of the user
     * @param email email of the user with a certain format
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
     * @param name name of the User
     */
    public void setName(String name){this.name = name;}
    /**
     * Getter for name
     * @return name of the User
     */
    public String getEmail(){return email;}
    /**
     * Setter for email
     * @param email of the User
     */
    public void setEmail(String email){this.email = email;}

    /**
     * Abstract method to get the identifier of the user
     * Each extension from user will define this method
     * @return user identifier
     */
    public abstract String getIdentifier();

    /**
     * Abstract method toString for users
     * Each extension from user will define this method
     */
    public abstract String toString();
}
