package serverfacade;

import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import commandresults.RegisterResult;
import interfaces.IServer;

/**
 * Created by glowsquirrel on 7/9/17.
 */

public class ServerFacade implements IServer{

    @Override
    public LoginResult login(String username, String password) {
        //TODO implement me
        return new LoginResult(true, "fake data");
    }

    @Override
    public RegisterResult register(String username, String password) {
        //TODO implement me
        return new RegisterResult(true, "fake data");
    }

    @Override
    public PollGamesResult pollGameList(String username) {
        //TODO implement me
        return new PollGamesResult(true, "fake data", null);
    }

    @Override
    public CommandResult createGame(String username, String gameName, int playerNum) {
        //TODO implement me
        return new CommandResult("creategame", true, "fake create");
    }

    @Override
    public CommandResult joinGame(String username, String gameName) {
        //TODO implement me
        return new CommandResult("joingame", true, "fake join");
    }

    @Override
    public CommandResult leaveGame(String username, String gameName) {
        //TODO implement me
        return new CommandResult("leavegame", true, "fake leave");
    }

    @Override
    public CommandResult startGame(String gameName) {
        //TODO implement me
        return new CommandResult("startgame", true, "fake start");
    }
}
