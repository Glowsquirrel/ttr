package clientcommunicator;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import model.UnstartedGame;

/**
 * Server Side. Gets a command as a JSON string and makes it a real command.
 */
class CommandResultSerializer implements JsonDeserializer<CommandResult> {

    @Override
    public CommandResult deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("type");
        String type = jsonType.getAsString();

        CommandResult typeModel = null;

        //TODO add a switch statement for each Command in server Commands package
        switch (type){
            case "login": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                String username = jsonObject.get("username").getAsString();
                typeModel = new LoginResult(success, username, errorMessage);
                break;
            }
            case "register": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                typeModel = new CommandResult("register", success, errorMessage);
                break;
            }
            case "pollgames": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                String gameListString = jsonObject.get("gameList").getAsString();
                Type listType = new TypeToken<List<UnstartedGame>>() {}.getType();
                List<UnstartedGame> gameList = new Gson().fromJson(gameListString, listType);
                typeModel = new PollGamesResult(success, errorMessage, gameList);
                break;
            }
            case "creategame": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                typeModel = new CommandResult("creategame", success, errorMessage);
                break;
            }
            case "startgame": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                typeModel = new CommandResult("startgame", success, errorMessage);
                break;
            }
            case "leavegame": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                typeModel = new CommandResult("leavegame", success, errorMessage);
                break;
            }
            case "joingame": {
                boolean success = jsonObject.get("success").getAsBoolean();
                String errorMessage = jsonObject.get("errorMessage").getAsString();
                typeModel = new CommandResult("joingame", success, errorMessage);
                break;
            }
        }
        return typeModel;
    }
}
