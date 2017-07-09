package Model;


public class AuthToken {
    /**
     * The username of the User who belongs to the Authentication Token.
     */
    private String username;
    /**
     * The Authentication token in the form of a String.
     */
    private String authToken;

    public AuthToken(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
