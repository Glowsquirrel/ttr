package commandresults;

public class RegisterResultData extends CommandResult {

    private String username;
    private String authToken;

    public String getUsername() {
        return username;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }
    public String getType(){
        return super.getType();
    }
    public boolean isSuccess(){
        return super.isSuccess();
    }
    public String getErrorMessage(){
        return super.getErrorMessage();
    }
}
