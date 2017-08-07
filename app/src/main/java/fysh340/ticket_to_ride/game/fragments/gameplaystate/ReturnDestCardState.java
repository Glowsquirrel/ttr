package fysh340.ticket_to_ride.game.fragments.gameplaystate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;

import serverproxy.ServerProxy;

/**
 * The state when the player has drawn three dest cards and needs to return anywhere from 0-2 of them.
 *
 * @author Shun Sambongi
 * @version 1.0
 * @since 2017-08-02
 */
public class ReturnDestCardState implements GamePlayState {

    private ServerProxy mServerProxy = new ServerProxy();


    @Override
    public void drawThreeDestCards(String username, String gameName) {
    }

    @Override
    public void returnDestCards(String username, String gameName, int destCards) {
        mServerProxy.returnDestCards(username, gameName, destCards);
    }

    @Override
    public void drawTrainCardFromDeck(String username, String gameName) {
    }

    @Override
    public void drawTrainCardFaceUp(String username, String gameName, int index) {
    }

    @Override
    public void claimRoute(String username, String gameName, int routeID, List<Integer> trainCards) {
    }

    @Override
    public void claimRoute(String username, String gameName, int routeID, AppCompatActivity context) {

    }
}
