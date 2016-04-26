package sm;

import java.util.ArrayList;

/**
 * Created by putriz on 4/10/2016.
 * This class stores a user's imformation
 */
public class User {

    private String name;
    private final String username; // user cannot change username after
    private String password;
    private String email;
    //private ArrayList<String> preferences;
    private String preference; // for now the user can only have one preference

    /**
     * Constructor
     */
    public User(String username){
        name = "name";
        this.username = username;
        password = "password";
        email = "email";
    }

    public User(String username, String name, String password, String email){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        //this.preferences = new ArrayList<String>();
        this.preference = "None"; // default preference
    }

    // METHODS
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

//    public ArrayList<String> getPreferences() { return preferences; }
//
//    /**
//     *
//     * @return
//     */
//    public String getStringPreferences() {
//        String s = "";
//        for (int i = 0; i < preferences.size(); i++) {
//            s += preferences.get(i);
//        }
//        return s;
//    }

//    /**
//     *
//     * @param preference
//     */
//    public void setInitialPreference(String preference) {
//        if (!preference.equals("None")) {
//            this.preferences.add(preference);
//        }
//    }


    /**
     *
     * @return
     */
    public String getPreference() { return this.preference; }

    /**
     *
     * @param preference
     */
    public void setPreference(String preference) {
        this.preference = preference;
    }

//    public void addPreferences(String preferences) {
//
//    }

}
