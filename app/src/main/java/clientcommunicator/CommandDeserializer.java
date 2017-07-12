package clientcommunicator;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import commands.Command;
import commands.PollGamesCommandData;


class CommandDeserializer implements JsonDeserializer<Command> {

    private static Logger logger = Logger.getLogger("serverlog");

    @Override
    public Command deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("type");
        String type = jsonType.getAsString();

        Command typeModel = null;

        //TODO add a switch statement for each Command in client Commands package
        switch (type){
            case "poll": {
                String username = jsonObject.get("username").getAsString();
                typeModel = new PollGamesCommandData(username);
                break;
            }
        }
        return typeModel;
    }
}
