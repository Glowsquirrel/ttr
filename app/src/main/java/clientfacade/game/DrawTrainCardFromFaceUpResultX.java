package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.DrawTrainCardFromFaceUpResult;
import interfaces.ICommand;

public class DrawTrainCardFromFaceUpResultX extends DrawTrainCardFromFaceUpResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.drawTrainCardFaceUp(super.username, super.trainCard);
    }
}
