package serverfacade;

import java.util.ArrayList;
import java.util.List;

import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import interfaces.IServer;
import model.UnstartedGame;

public class ServerFacade implements IServer{

    @Override
    public LoginResult login(String username, String password) {
        //TODO implement me
        return new LoginResult(true, "fake data", "myusername");
    }

    @Override
    public CommandResult register(String username, String password) {
        //TODO implement me
        return new CommandResult("register", true, "this was fake");
    }

    @Override
    public PollGamesResult pollGameList(String username) {
        //TODO implement me
        List<UnstartedGame> gamesList = new ArrayList<>();

        UnstartedGame game1 = new UnstartedGame();
        game1.setName("MY GAME 1");
        game1.setPlayersIn(2);
        game1.setPlayersNeeded(4);
        List<String> myPlayers1 = new ArrayList<>();
        myPlayers1.add("The Terminator");
        myPlayers1.add("Harry Potter");
        game1.setUsernames(myPlayers1);

        UnstartedGame game2 = new UnstartedGame();
        game2.setName("I have the high ground and a long game name");
        game2.setPlayersIn(3);
        game2.setPlayersNeeded(5);
        List<String> myPlayers2 = new ArrayList<>();
        myPlayers2.add("Obi-wan Kenobi");
        myPlayers2.add("DefinitelyNotAVelociraptorButIWouldntTrustMe");
        myPlayers2.add("PenguinMaster2.0");
        game2.setUsernames(myPlayers2);

        UnstartedGame game3 = new UnstartedGame();
        game3.setName("TTR is life");
        game3.setPlayersIn(3);
        game3.setPlayersNeeded(3);
        List<String> myPlayers3 = new ArrayList<>();
        myPlayers3.add("Never gonna give you");
        myPlayers3.add("up. Never gonna");
        myPlayers3.add("let you down.");
        game3.setUsernames(myPlayers3);

        gamesList.add(game1);
        gamesList.add(game2);
        gamesList.add(game3);
        return new PollGamesResult(true, "fake data", gamesList);
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
