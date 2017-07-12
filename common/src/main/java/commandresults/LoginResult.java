package commandresults;

public class LoginResult extends CommandResult{
    private String username;

    public LoginResult(boolean success, String username, String message){
        super.setType("login");
        super.setSuccess(success);
        super.setErrorMessage(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
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
