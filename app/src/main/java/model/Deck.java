package model;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import interfaces.Observable;
import interfaces.Observer;

/**
 *  <h1>DeckPresenter Model</h1>
 *  Models the details that are presented as available (face up) deck cards, as well as
 *  destination cards available when the player chooses to draw.
 *
 *  @author     Nathan Finch
 *  @since      7-22-17
 */
public class Deck implements Observable{
    private static class LazyDeckHelper {
        private static final Deck DECK_INSTANCE = new Deck();
    }

    public static Deck getInstance() {
        return LazyDeckHelper.DECK_INSTANCE;
    }

    private List<Integer> mFaceUpCards;
    private List<Integer> mDestinationCards;

    private List<Observer> mObservers;

    private Deck() {
        mFaceUpCards = new ArrayList<>();
        mDestinationCards = new ArrayList<>();
        mObservers = new ArrayList<>();
    }

    public void setAvailableDestCards(List<Integer> newCards) {
        mDestinationCards = newCards;
    }

    public void setAvailableFaceUpCards(List<Integer> newCards) {
        mFaceUpCards = newCards;
    }

    public List<Integer> getFaceUpCards() {
        return mFaceUpCards;
    }

    public List<Integer> getDestinationCards() {
        return mDestinationCards;
    }

    @Override
    public void register(Observer o) {
        mObservers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        int observerIndex = mObservers.indexOf(o);
        if(observerIndex >= 0) {
            mObservers.remove(observerIndex);
        }
    }

    @Override
    public void notifyObserver() {
            Handler uiHandler = new Handler(Looper.getMainLooper());

            Runnable runnable = new Runnable() { //
                @Override
                public void run() {
                    for (int i = 0; i < mObservers.size(); i++){
                        mObservers.get(i).update();
                    }
                }
            };

            uiHandler.post(runnable);
    }
    private boolean iHaveDifferentTrainDeckSize = false;
    public boolean iHaveDifferentTrainDeckSize() {
        return iHaveDifferentTrainDeckSize;
    }
    public void iHaveDifferentTrainDeckSize(boolean hasChanged) {
        iHaveDifferentTrainDeckSize = hasChanged;
    }

    public void setTrainCardDeckSize(int newSize) {
        trainCardDeckSize = newSize;
    }
    public int getTrainCardDeckSize() {
        return trainCardDeckSize;
    }

    //Begin trainCardDeckSize flag
    private boolean newTrainCardDeckSize = false;
    public boolean trainCardDeckSizeHasChanged() {
        return newTrainCardDeckSize;
    }
    public void trainCardDeckSizeHasChanged(boolean hasChanged) {
        newTrainCardDeckSize = hasChanged;
        Game.getGameInstance().notifyObserver();
    }
    //end trainCardDeckSize flags
    private int trainCardDeckSize;
    private int destinationCardDeckSize;
    public void setDestinationCardDeckSize(int newSize) {
        destinationCardDeckSize = newSize;
    }
    public int getDestinationCardDeckSize() {
        return destinationCardDeckSize;
    }

}
