package serverfacade.commands;

import utils.Utils;

public class LoginCommandData extends Command {
    protected String password;

    public LoginCommandData(String username, String password) {
        super.setType(Utils.LOGIN_TYPE);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType(){
        return super.getType();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
