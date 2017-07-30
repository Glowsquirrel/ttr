package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.FinalRoundResult;


public class FinalRoundResultX extends FinalRoundResult implements ICommand {

    @Override
    public void execute(){
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.finalRound(playerToEndOn);
    }
}
