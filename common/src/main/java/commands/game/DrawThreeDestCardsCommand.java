package commands.game;

import commands.Command;
import utils.Utils;

public class DrawThreeDestCardsCommand extends Command {
    protected String gameName;

    protected DrawThreeDestCardsCommand(){}
    public DrawThreeDestCardsCommand(String username, String gameName){
        super.type = Utils.DRAW_DEST_CARDS_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
