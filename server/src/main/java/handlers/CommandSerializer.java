package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import serverfacade.commands.ClearDatabaseCommandX;
import serverfacade.commands.Command;
import serverfacade.commands.CreateGameCommandX;
import serverfacade.commands.JoinGameCommandX;
import serverfacade.commands.LeaveGameCommandX;
import serverfacade.commands.LoginCommandX;
import serverfacade.commands.PollGamesCommandX;
import serverfacade.commands.RegisterCommandX;
import serverfacade.commands.StartGameCommandX;

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
                typeModel = gson.fromJson(jsonObject, LoginCommandX.class);
                break;
            }
            case "register": {
                typeModel = gson.fromJson(jsonObject, RegisterCommandX.class);
                break;
            }
            case "pollgames": {
                typeModel = gson.fromJson(jsonObject, PollGamesCommandX.class);
                break;
            }
            case "creategame": {
                typeModel = gson.fromJson(jsonObject, CreateGameCommandX.class);
                break;
            }
            case "startgame": {
                typeModel = gson.fromJson(jsonObject, StartGameCommandX.class);
                break;
            }
            case "leavegame": {
                typeModel = gson.fromJson(jsonObject, LeaveGameCommandX.class);
                break;
            }
            case "joingame": {
                typeModel = gson.fromJson(jsonObject, JoinGameCommandX.class);
                break;
            }
            case "cleardb": {
                typeModel = gson.fromJson(jsonObject, ClearDatabaseCommandX.class);
                break;
            }
        }
        return typeModel;
    }
}
