package fysh340.ticket_to_ride.game.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.util.ArrayList;

import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.fragments.gameplaystate.ClientState;
import model.Game;
import model.Route;
import model.TrainCard;
import serverproxy.ServerProxy;

import static model.TrainCard.RED;
import static model.TrainCard.WILD;

public class ColorChoiceDialog extends DialogFragment {
    Game mGame=Game.getGameInstance();
    ServerProxy mSP=new ServerProxy();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a card color")
                .setItems(R.array.mcolors, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        ArrayList<Integer> cards=new ArrayList<>();
                        Route route=Route.getRouteByID(mGame.getCurrentlySelectedRouteID());
                        TrainCard myColor=null;
                        switch(which) {
                            case (0):
                                myColor = TrainCard.getTrainCard(0);
                                break;
                            case (1):
                                myColor = TrainCard.getTrainCard(1);
                                break;
                            case (2):
                                myColor = TrainCard.getTrainCard(2);
                                break;
                            case (3):
                                myColor = TrainCard.getTrainCard(3);
                                break;
                            case (4):
                                myColor = TrainCard.getTrainCard(4);
                                break;
                            case (5):
                                myColor = TrainCard.getTrainCard(5);
                                break;
                            case (6):
                                myColor = TrainCard.getTrainCard(6);
                                break;
                            case (7):
                                myColor = TrainCard.getTrainCard(7);
                                break;
                            case (8):
                                myColor = TrainCard.getTrainCard(8);
                                break;

                        }
                        int colored=mGame.getMyself().getNumOfTypeCards(myColor);
                        int wild=mGame.getMyself().getNumOfTypeCards(WILD);
                        int cardsLeft=route.getLength();
                        if((colored+wild>=route.getLength()&&myColor!=WILD)||(wild>=route.getLength())) {
                            for(int i=0;i<colored;i++)
                            {
                                if(cardsLeft>0) {
                                    cardsLeft--;
                                    cards.add(TrainCard.getTrainCardKey(myColor));
                                }

                            }
                            while(cardsLeft>0)
                            {
                                cards.add(TrainCard.getTrainCardKey(WILD));
                                cardsLeft--;
                            }
                            mGame.setCardsToDiscard(cards);
//                            mSP.claimRoute(mGame.getMyself().getMyUsername(), mGame.getMyGameName(),
//                                    mGame.getCurrentlySelectedRouteID(), cards);
                            ClientState.INSTANCE.getState().claimRoute(mGame.getMyself().getMyUsername(),
                                    mGame.getMyGameName(), mGame.getCurrentlySelectedRouteID(), cards);
//                            Toast.makeText(getActivity(), "Route Claimed Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "You don't have enough cards!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }
}