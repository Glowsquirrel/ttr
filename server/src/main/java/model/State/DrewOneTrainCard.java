package model.State;

import model.GamePlayException;
import model.StartedGame;

/**
 * Created by sjrme on 7/29/17.
 */

public class DrewOneTrainCard implements TurnState{

    private StartedGame game;

    public DrewOneTrainCard(StartedGame game) {
        this.game = game;
    }

    @Override
    public void switchState(CommandType commandType) throws GamePlayException {
        switch (commandType) {
            case DRAW_TRAIN_CARD_FROM_DECK: {
                game.advancePlayerTurn();
                game.setTurnState(new BeforeTurn(game));
                break;
            }
            case FACEUP_NON_LOCOMOTIVE: {
                game.advancePlayerTurn();
                game.setTurnState(new BeforeTurn(game));
                break;
            }
            default:
                throw new GamePlayException("Illegal move. You may only draw train cards.");
        }
    }

    @Override
    public String getPrettyName(){
        return null;
    }

}
