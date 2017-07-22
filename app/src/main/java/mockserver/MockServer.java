package mockserver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import clientfacade.ClientFacade;
import commands.Command;
import commands.game.ChatCommand;
import commands.game.ClaimRouteCommand;
import commands.game.DrawThreeDestCardsCommand;
import commands.game.DrawTrainCardFromDeckCommand;
import commands.game.ReturnDestCardsCommand;
import commands.game.ReturnFirstDestCardCommand;
import commands.game.StartGameCommand;
import commands.menu.CreateGameCommand;
import commands.menu.JoinGameCommand;
import commands.menu.LeaveGameCommand;
import commands.menu.LoginCommand;
import commands.menu.RegisterCommand;
import model.RunningGame;
import model.UnstartedGame;
import utils.Utils;

public class MockServer {
    private static List<UnstartedGame> unstartedGameList = new ArrayList<>();
    private static List<RunningGame> runningGameList = new ArrayList<>();
    private static Map<String, String> loginMap = new HashMap<>();

    static{

    }

    public void doCommand(Command command) {
        String myJsonString;
        ClientFacade clientFacade = new ClientFacade();
        switch (command.getType()) {
            case Utils.LOGIN_TYPE: {
                LoginCommand myCommand = (LoginCommand) command;
                if (loginMap.containsKey(myCommand.getUsername())) {
                    if (myCommand.getPassword().equals(loginMap.get(myCommand.getUsername()))) {
                        clientFacade.loginUser(myCommand.getUsername(), myCommand.getPassword(), null);
                    }
                } else
                    clientFacade.postMessage("fake login rejected");
                break;
            }
            case Utils.REGISTER_TYPE: {
                RegisterCommand myCommand = (RegisterCommand) command;
                if (!loginMap.containsKey(myCommand.getUsername())) {
                    loginMap.put(myCommand.getUsername(), myCommand.getPassword());
                    clientFacade.registerUser(command.getUsername(), "password", "fake register successful", null);
                }
                else
                    clientFacade.postMessage("fake register already exists");
                break;
            }
            case Utils.POLL_TYPE: {
                clientFacade.updateSingleUserGameList(command.getUsername(), unstartedGameList, runningGameList);
                break;
            }
            case Utils.CREATE_TYPE: {
                CreateGameCommand mycommand = (CreateGameCommand) command;
                UnstartedGame mygame = new UnstartedGame(mycommand.getGameName(), 1);
                for (int i = 0; i < unstartedGameList.size(); i++) {
                    if (unstartedGameList.get(i).getGameName().equals(mycommand.getGameName())) {
                        clientFacade.postMessage("game already exists");
                        return;
                    }
                }
                for (int i = 0; i < runningGameList.size(); i++) {
                    if (runningGameList.get(i).getGameName().equals(mycommand.getGameName())) {
                        clientFacade.postMessage("game already exists");
                        return;
                    }
                }
                unstartedGameList.add(mygame);
                clientFacade.updateSingleUserGameList(command.getUsername(), unstartedGameList, runningGameList);
                break;
            }
            case Utils.JOIN_TYPE: {
                JoinGameCommand mycommand = (JoinGameCommand)command;
                for (int i = 0; i < unstartedGameList.size(); i++){
                    if (mycommand.getGameName().equals(unstartedGameList.get(i).getGameName())){
                        unstartedGameList.get(i).addPlayer(mycommand.getUsername());
                        clientFacade.joinGame(mycommand.getUsername(), unstartedGameList.get(i).getGameName());
                        clientFacade.updateSingleUserGameList(command.getUsername(), unstartedGameList, runningGameList);
                        return;
                    }
                }
                clientFacade.postMessage("could not join game");
                break;
            }
            case Utils.LEAVE_TYPE: {
                LeaveGameCommand mycommand = (LeaveGameCommand) command;
                for (int i = 0; i < unstartedGameList.size(); i++) {
                    if (mycommand.getGameName().equals(unstartedGameList.get(i).getGameName())) {
                        clientFacade.leaveGame(mycommand.getUsername(), unstartedGameList.get(i).getGameName());
                        unstartedGameList.get(i).removePlayer(mycommand.getUsername());
                        unstartedGameList.remove(i);
                        clientFacade.updateSingleUserGameList(command.getUsername(), unstartedGameList, runningGameList);
                        return;
                    }
                }
                clientFacade.postMessage("could not leave game");
                break;
            }
            case Utils.START_TYPE: {
                StartGameCommand mycommand = (StartGameCommand) command;
                for (int i = 0; i < unstartedGameList.size(); i++) {
                    if (mycommand.getGameName().equals(unstartedGameList.get(i).getGameName())) {
                        List<String> playerNames = new ArrayList<>();
                        playerNames.add(mycommand.getUsername());
                        List<Integer> destCards = new ArrayList<>();
                        destCards.add(10);
                        destCards.add(15);
                        destCards.add(5);
                        List<Integer> trainCards = new ArrayList<>();
                        trainCards.add(50);
                        trainCards.add(77);
                        trainCards.add(22);
                        List<Integer> faceUpCards = new ArrayList<>();
                        faceUpCards.add(5);
                        faceUpCards.add(0);
                        faceUpCards.add(66);
                        clientFacade.startGame(mycommand.getUsername(), mycommand.getGameName(), playerNames, destCards, trainCards, faceUpCards);
                    }
                }
                break;
            }
            case Utils.REJOIN_TYPE:
                break;
            case Utils.MESSAGE_TYPE: {
                ChatCommand mycommand = (ChatCommand)command;
                clientFacade.addChat(mycommand.getUsername(), mycommand.getMessage());
                break;
            }
            case Utils.DRAW_DEST_CARDS_TYPE: {
                DrawThreeDestCardsCommand mycommand = (DrawThreeDestCardsCommand) command;
                List<Integer> destCards = new ArrayList<>();
                destCards.add(0);
                destCards.add(1);
                destCards.add(2);
                clientFacade.drawDestCards(mycommand.getUsername(), destCards);
                break;
            }
            case Utils.RETURN_FIRST_DEST_CARD_TYPE: {
                ReturnFirstDestCardCommand mycommand = (ReturnFirstDestCardCommand)command;
                clientFacade.returnFirstDestCards(mycommand.getUsername(), mycommand.getDestCard());
                break;
            }
            case Utils.RETURN_DEST_CARDS_TYPE: {
                ReturnDestCardsCommand mycommand = (ReturnDestCardsCommand)command;
                clientFacade.returnDestCards(mycommand.getUsername(), mycommand.getDestCards());
                break;
            }
            case Utils.DRAW_TRAIN_CARD_DECK_TYPE: {
                DrawTrainCardFromDeckCommand mycommand = (DrawTrainCardFromDeckCommand)command;
                Random rand = new Random();
                int card = rand.nextInt(100) + 1;
                clientFacade.drawTrainCardDeck(mycommand.getUsername(), card);
                break;
            }
            case Utils.DRAW_TRAIN_CARD_FACEUP_TYPE: {

                break;
            }
            case Utils.CLAIM_ROUTE_TYPE: {
                ClaimRouteCommand mycommand = (ClaimRouteCommand)command;
                clientFacade.claimRoute(mycommand.getUsername(), mycommand.getRouteID());
                break;
            }
            case Utils.CHAT_TYPE: {
                ChatCommand mycommand = (ChatCommand)command;
                clientFacade.addChat(mycommand.getUsername(), mycommand.getMessage());
                break;
            }
            default:
                myJsonString = "Error parsing Json String. Check ClientCommunicator";
        }

    }
}