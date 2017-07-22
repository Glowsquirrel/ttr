package commands.game;

import commands.Command;
import utils.Utils;

public class ReturnFirstDestCardCommand extends Command {
    protected String gameName;
    protected int destCard;

    protected ReturnFirstDestCardCommand(){}
    public ReturnFirstDestCardCommand(String username, String gameName, int destCard){
        super.type = Utils.RETURN_FIRST_DEST_CARD_TYPE;
        super.username = username;
        this.gameName = gameName;
        this.destCard = destCard;
    }

    public int getDestCard() {
        return destCard;
    }
}
