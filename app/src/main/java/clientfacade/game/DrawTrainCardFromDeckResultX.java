package clientfacade.game;


import clientfacade.ClientFacade;
import results.game.DrawTrainCardFromDeckResult;
import interfaces.ICommand;

public class DrawTrainCardFromDeckResultX extends DrawTrainCardFromDeckResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.drawTrainCardDeck(super.username, super.trainCard);
    }
}
