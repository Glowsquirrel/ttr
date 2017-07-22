package results.game;

import results.Result;
import utils.Utils;

public class ClaimRouteResult extends Result {
    protected int routeID;

    protected ClaimRouteResult(){}
    public ClaimRouteResult(String username, int routeID){
        super.type = Utils.CLAIM_ROUTE_TYPE;
        super.username = username;
        this.routeID = routeID;
    }
}
