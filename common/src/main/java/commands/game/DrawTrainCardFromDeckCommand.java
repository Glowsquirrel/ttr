package commands.game;

import commands.Command;
import utils.Utils;

public class DrawTrainCardFromDeckCommand extends Command {
    protected String gameName;

    protected DrawTrainCardFromDeckCommand(){}
    public DrawTrainCardFromDeckCommand(String username, String gameName){
        super.type = Utils.DRAW_TRAIN_CARD_DECK_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
