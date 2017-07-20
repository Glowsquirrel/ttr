package commands.game;

import commands.Command;
import utils.Utils;

public class ClaimRouteCommand extends Command {
    protected String gameName;
    protected int routeID;

    protected ClaimRouteCommand(){}
    public ClaimRouteCommand(String username, String gameName, int routeID){
        super.type = Utils.CLAIM_ROUTE_TYPE;
        super.username = username;
        this.gameName = gameName;
        this.routeID = routeID;
    }
}
