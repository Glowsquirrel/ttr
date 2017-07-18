package clientcommunicator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

import clientfacade.commands.CreateGameResultX;
import clientfacade.commands.JoinGameResultX;
import clientfacade.commands.LeaveGameResultX;
import clientfacade.commands.LoginResultX;
import clientfacade.commands.PollGamesResultX;
import clientfacade.commands.RegisterResultX;
import clientfacade.commands.StartGameResultX;
import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import model.UnstartedGame;
import utils.Utils;

/**
 * Server Side. Gets a command as a JSON string and makes it a real command.
 */
public class CommandResultSerializer implements JsonDeserializer<CommandResult> {

    @Override
    public CommandResult deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("type");
        String type = jsonType.getAsString();

        Gson gson = new Gson();
        CommandResult typeModel = null;

        //TODO add a switch statement for each CommandResultX type
        switch (type){
            case Utils.LOGIN_TYPE: {
                typeModel = gson.fromJson(jsonObject, LoginResultX.class);
                break;
            }
            case Utils.REGISTER_TYPE: {
                typeModel = gson.fromJson(jsonObject, RegisterResultX.class);
                break;
            }
            case Utils.POLL_TYPE: {
                typeModel = gson.fromJson(jsonObject, PollGamesResultX.class);
                break;
            }
            case Utils.CREATE_TYPE: {
                typeModel = gson.fromJson(jsonObject, CreateGameResultX.class);
                break;
            }
            case Utils.START_TYPE: {
                typeModel = gson.fromJson(jsonObject, StartGameResultX.class);
                break;
            }
            case Utils.LEAVE_TYPE: {
                typeModel = gson.fromJson(jsonObject, LeaveGameResultX.class);
                break;
            }
            case Utils.JOIN_TYPE: {
                typeModel = gson.fromJson(jsonObject, JoinGameResultX.class);
                break;
            }
        }
        return typeModel;
    }
}
