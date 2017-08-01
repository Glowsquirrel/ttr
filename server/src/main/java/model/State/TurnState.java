package model.State;

import model.GamePlayException;

/**
 * Created by sjrme on 7/29/17.
 */

public interface TurnState {

    public void switchState(CommandType commandType) throws GamePlayException;
    public String getPrettyName();
}
