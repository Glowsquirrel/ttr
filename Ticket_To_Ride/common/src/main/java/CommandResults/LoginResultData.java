package CommandResults;

public class LoginResultData extends CommandResult{
    private String username;
    private String authToken;

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
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
