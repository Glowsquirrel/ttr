package clientcommunicator;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

import clientfacade.game.*;
import clientfacade.menu.*;
import results.Result;
import utils.Utils;

/**
 * Server Side. Gets a command as a JSON string and makes it a real command.
 */
public class CommandResultXSerializer implements JsonDeserializer<Result> {

    @Override
    public Result deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("type");
        String type = jsonType.getAsString();

        Gson gson = new Gson();
        Result typeModel = null;

        switch (type){
            case Utils.LOGIN_TYPE:
                typeModel = gson.fromJson(jsonObject, LoginResultX.class);
                break;
            case Utils.REGISTER_TYPE:
                typeModel = gson.fromJson(jsonObject, RegisterResultX.class);
                break;
            case Utils.POLL_TYPE:
                typeModel = gson.fromJson(jsonObject, PollGamesResultX.class);
                break;
            case Utils.CREATE_TYPE:
                typeModel = gson.fromJson(jsonObject, CreateGameResultX.class);
                break;
            case Utils.JOIN_TYPE:
                typeModel = gson.fromJson(jsonObject, JoinGameResultX.class);
                break;
            case Utils.LEAVE_TYPE:
                typeModel = gson.fromJson(jsonObject, LeaveGameResultX.class);
                break;
            case Utils.START_TYPE:
                typeModel = gson.fromJson(jsonObject, StartGameResultX.class);
                break;
            case Utils.MESSAGE_TYPE:
                typeModel = gson.fromJson(jsonObject, MessageResultX.class);
                break;
            case Utils.DRAW_DEST_CARDS_TYPE:
                typeModel = gson.fromJson(jsonObject, DrawThreeDestCardsResultX.class);
                break;
            case Utils.RETURN_FIRST_DEST_CARD_TYPE:
                typeModel = gson.fromJson(jsonObject, ReturnFirstDestCardResultX.class);
                break;
            case Utils.RETURN_DEST_CARDS_TYPE:
                typeModel = gson.fromJson(jsonObject, ReturnDestCardsResultX.class);
                break;
            case Utils.DRAW_TRAIN_CARD_DECK_TYPE:
                typeModel = gson.fromJson(jsonObject, DrawTrainCardFromDeckResultX.class);
                break;
            case Utils.DRAW_TRAIN_CARD_FACEUP_TYPE:
                typeModel = gson.fromJson(jsonObject, DrawTrainCardFromFaceUpResultX.class);
                break;
            case Utils.CLAIM_ROUTE_TYPE:
                typeModel = gson.fromJson(jsonObject, ClaimRouteResultX.class);
                break;
            case Utils.CHAT_TYPE:
                typeModel = gson.fromJson(jsonObject, ChatResultX.class);
                break;

        }
        return typeModel;
    }
}
