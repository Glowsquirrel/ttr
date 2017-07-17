package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import serverfacade.commands.ClearDatabaseCommand;
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
        Gson gson = new Gson();
        Command typeModel = null;

        //TODO add a switch statement for each Command in server Commands package
        switch (type){
            case "login": {
                typeModel = gson.fromJson(jsonObject, LoginCommand.class);
                break;
            }
            case "register": {
                typeModel = gson.fromJson(jsonObject, RegisterCommand.class);
                break;
            }
            case "pollgames": {
                typeModel = gson.fromJson(jsonObject, PollGamesCommand.class);
                break;
            }
            case "creategame": {
                typeModel = gson.fromJson(jsonObject, CreateGameCommand.class);
                break;
            }
            case "startgame": {
                typeModel = gson.fromJson(jsonObject, StartGameCommand.class);
                break;
            }
            case "leavegame": {
                typeModel = gson.fromJson(jsonObject, LeaveGameCommand.class);
                break;
            }
            case "joingame": {
                typeModel = gson.fromJson(jsonObject, JoinGameCommand.class);
                break;
            }
            case "cleardb": {
                typeModel = gson.fromJson(jsonObject, ClearDatabaseCommand.class);
                break;
            }
        }
        return typeModel;
    }
}
