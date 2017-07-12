package commandresults;

public class RegisterResult extends CommandResult {

    public RegisterResult(boolean success, String message){
        super.setType("register");
        super.setSuccess(success);
        super.setErrorMessage(message);
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
