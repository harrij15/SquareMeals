package sm;

/**
 * Created by putriz on 4/10/2016.
 * This class stores a user's imformation
 */
public class User {

    private String name;
    private String password;
    private String email;

    /**
     * Public constructor
     */
    public User(){
        name = "name";
        password = "password";
        email = "email";
    }

    // METHODS
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
