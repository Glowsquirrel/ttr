package commandresults;

public class LoginResult extends CommandResult{

    public LoginResult(boolean success, String username, String message){
        super.setType("login");
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
}
