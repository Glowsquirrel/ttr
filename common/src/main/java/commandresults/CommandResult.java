package commandresults;


public class CommandResult {
    private String type;
    private boolean success;
    private String errorMessage;

    public CommandResult(){} //default constructor... to be deleted
    public CommandResult(String type, boolean success, String errorMessage){
        this.type = type;
        this.success = success;
        this.errorMessage = errorMessage;
    }
    public String getType() {
        return type;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
