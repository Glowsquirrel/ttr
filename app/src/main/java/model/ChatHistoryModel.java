package model;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import interfaces.Observable;
import interfaces.Observer;

/**
 * Created by Rachael on 7/22/2017.
 */

public class ChatHistoryModel implements Observable{
    public static final ChatHistoryModel myChat=new ChatHistoryModel();
    private ChatHistoryModel(){
        chatList=new ArrayList<>();
        historyList=new ArrayList<>();
    };
    private ArrayList<Observer> observers = new ArrayList<>();
    private List<String> chatList;
    private List<String> historyList;
    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer deleteObserver) {
        int observerIndex = observers.indexOf(deleteObserver);
        if(observerIndex>=0) {
            observers.remove(observerIndex);
        }
    }

    @Override
    public void notifyObserver() {

        Handler uiHandler = new Handler(Looper.getMainLooper()); //gets the UI thread
        Runnable runnable = new Runnable() { //
            @Override
            public void run() {
                for (int i = 0; i < observers.size(); i++){
                    observers.get(i).update();
                }
            }
        };
        uiHandler.post(runnable); //do the run() method in previously declared runnable on UI thread

    }
    private boolean chat;

    public boolean isChat() {
        return chat;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }
    public void switchChat(){
        if(chat)
        {
            chat=false;
        }
        else
        {
            chat=true;
        }
    };
    public void addChat(String username, String message)
    {
        chatList.add(username+": "+message);
        notifyObserver();
    }


    public void addHistory(String username, String message)
    {

        historyList.add(username+": "+message);
        notifyObserver();
    }
    public List<String> getChatStrings()
    {
        return chatList;
    }
    public List<String> getHistoryStrings()
    {
        return historyList;
    }

}
