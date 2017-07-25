package model;

import java.util.HashSet;
import java.util.Set;

import interfaces.Observable;
import interfaces.Observer;

/**
 * TODO: description
 *
 * @author Shun Sambongi
 * @version 1.0
 * @since 7/25/17
 */
public class ServerError implements Observable {

    private String mMessage;
    private Set<Observer> mObservers = new HashSet<>();

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public void register(Observer o) {
        mObservers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        mObservers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : mObservers) {
            observer.update();
        }
    }
}
