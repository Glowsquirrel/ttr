package model;

/**
 * Created by sjrme on 7/26/17.
 */
public class StartedGameTest {
/*
    private static StartedGame startedGame;
    @BeforeClass
    public static void setUpGame() {
        UnstartedGame unstartedGame = new UnstartedGame();
        unstartedGame.addPlayer("Player1");
        unstartedGame.addPlayer("Player2");
        startedGame = new StartedGame(unstartedGame);
        startedGame.preGameSetup(unstartedGame.getUsernamesInGame());
        startedGame.printBoardState();
    }
    @Test
    public void mockGame() {

        checkReturnDestState("Player2");// all Fail
        checkReturnDestState("Player1");// all Fail but Last
        checkReturnDestState("Player1");// all Fail
        checkReturnDestState("Player2");// all Fail but last

        startedGame.printBoardState();


        startedGame.drawTrainCardFromDeck("Player1");
        startedGame.drawTrainCardFromDeck("Player2");

        startedGame.printBoardState();


    }

    public boolean checkReturnDestState(String playerTurn) {
        try{
            startedGame.drawThreeDestCards("Player1");
        } catch(GamePlayException ge){}
        try{
            startedGame.drawTrainCardFromDeck("Player1");
        } catch(GamePlayException ge){}
        try{
            startedGame.drawTrainCardFromFaceUp("Player1", 0);
        } catch(GamePlayException ge){}
        try{
            startedGame.claimRoute("Player1", 0, new ArrayList<Integer>());
        } catch(GamePlayException ge){
            System.out.println("Correct!");
        }
        try{
            startedGame.returnDestCard("Player1");
        } catch(GamePlayException ge){
            System.out.println("Correct!");
        }
        return true;
    }

    public void checkDrawDestCardsState(String playerTurn) {

    }

    public void checkDrawTCFromDeckState(String playerTurn){

    }

    public void checkDrawLocmotiveState(String playerTurn) {

    }

    public void checkNonLocomotive(String playerTurn) {

    }

    public void checkClaimRouteState(String playerTurn) {

    }

*/
}
