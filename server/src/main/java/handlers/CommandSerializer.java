package handlers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import serverfacade.commands.Command;
import serverfacade.commands.CreateGameCommand;
import serverfacade.commands.JoinGameCommand;
import serverfacade.commands.LeaveGameCommand;
import serverfacade.commands.LoginCommand;
import serverfacade.commands.PollGamesCommand;
import serverfacade.commands.RegisterCommand;
import serverfacade.commands.StartGameCommand;

/**
 * Server Side. Gets a command as a JSON string and makes it a real command.
 */
class CommandSerializer implements JsonDeserializer<Command> {

    private static Logger logger = Logger.getLogger("serverlog");

    @Override
    public Command deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("type");
        String type = jsonType.getAsString();

        Command typeModel = null;

        //TODO add a switch statement for each Command in server Commands package
        switch (type){
            case "login": {
                String username = jsonObject.get("username").getAsString();
                String password = jsonObject.get("password").getAsString();
                typeModel = new LoginCommand(username, password);
                break;
            }
            case "register": {
                String username = jsonObject.get("username").getAsString();
                String password = jsonObject.get("password").getAsString();
                typeModel = new RegisterCommand(username, password);
                break;
            }
            case "poll": {
                String username = jsonObject.get("username").getAsString();
                typeModel = new PollGamesCommand(username);
                break;
            }
            case "creategame": {
                String username = jsonObject.get("username").getAsString();
                String gameName = jsonObject.get("gameName").getAsString();
                String numberPlayers = jsonObject.get("numPlayers").getAsString();
                int numPlayers;
                try {
                    numPlayers = Integer.parseInt(numberPlayers);
                }catch(NumberFormatException ex){
                    logger.warning(numberPlayers + " is not an integer");
                    return null;
                }
                typeModel = new CreateGameCommand(username, gameName, numPlayers);
                break;
            }
            case "startgame": {
                String gameName = jsonObject.get("gameName").getAsString();
                typeModel = new StartGameCommand(gameName);
                break;
            }
            case "leavegame": {
                String username = jsonObject.get("username").getAsString();
                String gameName = jsonObject.get("gameName").getAsString();
                typeModel = new LeaveGameCommand(username, gameName);
                break;
            }
            case "joingame": {
                String username = jsonObject.get("username").getAsString();
                String gameName = jsonObject.get("gameName").getAsString();
                typeModel = new JoinGameCommand(username, gameName);
                break;
            }
        }
        return typeModel;
    }
}
