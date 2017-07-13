package commandresults;

public class CommandResult {
    private String type;
    private boolean success;
    private String message;

    public CommandResult(){} //default constructor... to be deleted
    public CommandResult(String type, boolean success, String message){
        this.type = type;
        this.success = success;
        this.message = message;
    }
    public String getType() {
        return type;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
