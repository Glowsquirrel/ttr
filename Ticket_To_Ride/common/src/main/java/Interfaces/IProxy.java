package Interfaces;


public interface IProxy {

    public boolean login(String username, String password);
    public boolean register(String username, String password);
    // TODO add more functions to be called by client but executed on server
}

