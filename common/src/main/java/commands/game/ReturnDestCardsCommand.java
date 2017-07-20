package commands.game;

import java.util.List;

import commands.Command;
import utils.Utils;

public class ReturnDestCardsCommand extends Command {
    protected String gameName;
    protected List<Integer> destCards;

    protected ReturnDestCardsCommand(){}
    public ReturnDestCardsCommand(String username, String gameName, List<Integer> destCards){
        super.type = Utils.RETURN_DEST_CARDS_TYPE;
        super.username = username;
        this.gameName = gameName;
        this.destCards = destCards;
    }
}
