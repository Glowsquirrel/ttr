package Handlers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import Commands.Command;
import Commands.LoginCommand;
import Commands.RegisterCommand;


class CommandDeserializer implements JsonDeserializer<Command> {

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
                typeModel.setType(type);
                break;
            }
            case "register": {
                String username = jsonObject.get("username").getAsString();
                String password = jsonObject.get("password").getAsString();
                typeModel = new RegisterCommand(username, password);
                typeModel.setType(type);
                break;
            }
        }
        return typeModel;
    }
}
