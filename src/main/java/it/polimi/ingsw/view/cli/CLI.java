package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.WorkerView;

import java.util.ArrayList;
import java.util.Scanner;


public class CLI implements Ui {

    Scanner in = new Scanner(System.in);
    private BoardView boardView;
    private String nickname;
    private boolean current;



    private ArrayList<String> playerList;
    private String[] godList;


    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }


    @Override
    public void showObj(Obj obj) {

        String command = obj.getTag();

        if (command.equals(Tags.playerList)) {
            playerList = obj.getList();
            godList = new String[playerList.size()];

                System.out.println(Messages.matchStarting);

            for(String str : obj.getList()) {
                System.out.println(str);
            }

        } else if (command.equals("setName")) {
            nickname = obj.getMessage();

        } else if(command.equals("turn")) {
            //System.out.println("Turn of " + obj.getMessage());

        } else if(command.equals("completeList")) {
            System.out.println("You can choose godcards from this list:");
            for(String str : obj.getList()) {
                System.out.println(str);
            }

        } else if(command.equals("defineGod")) {
            System.out.println("The challenger chooses: " + obj.getMessage());

        } else if(command.equals("chooseGod")) {
            System.out.println("The player "+ obj.getPlayer() + " chooses " + obj.getMessage() + "!");
            godList[playerList.indexOf(obj.getPlayer())] = obj.getMessage();

        } else if(command.equals("currentList")) {
            if(obj.getList().size()!=0) {
                System.out.println("Actual cards:");
                for(String str : obj.getList()) {
                    System.out.println(str);
                }
            }
        } else if(command.equals(Tags.board)) {
            showBoard(obj.getBoardView(),obj.getBoardView().getCurrentName().equals(nickname));
        } else if(command.equals("gMsg")) {
            System.out.println(obj.getGameMessage().getMessage());
            if(obj.getGameMessage().getMessage().equals(Messages.worker)) {
                System.out.println(Messages.workerIndex);
            }

        } else if(command.equals("end")) {
            if(obj.getMessage().equals("win")) {
                if(obj.getPlayer().equals(nickname)) {
                    System.out.println("You win!");
                } else {
                    System.out.println("The winner is " + obj.getPlayer() + "!");
                }
            } else {
                if(obj.getPlayer().equals(nickname)) {
                    System.out.println("You lose!");
                } else {
                    System.out.println("The player " + obj.getPlayer() + " loses!");
                }
            }
        } else if(command.equals(Tags.boardMsg)) {
            System.out.print(obj.getMessage());

            if(obj.getMessage().equals(Messages.move) || obj.getMessage().equals(Messages.Build)) {
                System.out.println(Messages.operation);
            } else {
                System.out.println("");
            }
        } else {
            System.out.println(obj.getMessage());
        }
    }


    public void showBoard(BoardView boardView,boolean command) {

        this.boardView = boardView;
        this.current = command;

        System.out.println(CliPrinter.upExt);

        System.out.print(CliPrinter.emptyExt);
        System.out.format("%1$52s",CliPrinter.rightExt + "\n");

        System.out.print(CliPrinter.leftExt + "    " + CliPrinter.verticalBar);

        for(int i=0;i<5;i++){
            System.out.print(CliPrinter.gridNumberColor + "   " + i + "   " + CliPrinter.verticalBar);
        }
        System.out.print(CliPrinter.rightExt);
        System.out.format("%1$-36s", "    Current Player:");
        System.out.println(CliPrinter.rightExt);

        System.out.print(CliPrinter.leftExt + CliPrinter.supBoard + CliPrinter.rightExt);
        System.out.format("%1$52s",CliPrinter.rightExt + "\n");


        for(int i=0;i<9;i++) {

            if (i%2==0) {
                System.out.print(CliPrinter.leftExt + CliPrinter.gridNumberColor + " " + i/2 + "  " + CliPrinter.RESET);

                for (int j=0;j<5;j++) {
                    Tile t = boardView.getTile(i/2,j);

                    if(j==0) {
                        System.out.print(CliPrinter.verticalBoard + " ");
                    } else {
                        System.out.print(CliPrinter.verticalBar + " ");
                    }

                    if (t.isOccupiedByWorker()) {
                        printWorker(t);
                    } else if (t.isDomePresence()) {
                        printDome();
                    } else {
                        printBlock(t);
                    }

                    System.out.print(CliPrinter.RESET + " ");
                }

                System.out.print(CliPrinter.verticalBoard + CliPrinter.rightExt);


            } else {
                System.out.print(CliPrinter.leftExt + CliPrinter.horizontalBar + CliPrinter.rightExt);
            }


            if (i == 0) {
                System.out.print(workerColor(boardView.getCurrentID()));
                System.out.format("%1$-40s", "    " + boardView.getCurrentName() + CliPrinter.RESET + " (" + boardView.getCurrentGod() + ")");

            } else if (i==4) {
                System.out.format("%1$-47s","  - " + CliPrinter.player0 + playerList.get(0) + CliPrinter.RESET + " (" + godList[0] + ")");
            } else if (i==6) {
                System.out.format("%1$-47s","  - " + CliPrinter.player1 + playerList.get(1) + CliPrinter.RESET + " (" + godList[1] + ")");

            } else {

                if (i==8 && playerList.size()==3) {
                    System.out.format("%1$-47s", "  - " + CliPrinter.player2 + playerList.get(2) + CliPrinter.RESET + " (" + godList[2] + ")");
                } else {
                    System.out.format("%1$-36s", "");
                }
            }

            System.out.println(CliPrinter.rightExt);
        }

        System.out.print(CliPrinter.leftExt + CliPrinter.infBoard + CliPrinter.rightExt);
        System.out.format("%1$52s",CliPrinter.rightExt + "\n");
        System.out.print(CliPrinter.emptyExt);
        System.out.format("%1$52s",CliPrinter.rightExt + "\n");
        System.out.println(CliPrinter.downExt);
    }


    public void printWorker(Tile t) {

        for (int i = 0; i < boardView.getWorkerList().length; i++) {

            if (boardView.getWorkerList()[i].isPositionSet()) {
                if (t.equals(boardView.getWorkerList()[i].getPosition())) {

                    if(checkCanOp(t)) {
                        printCanOp();
                    }
                    if (i == boardView.getChosenWorkerID()) {
                        System.out.print(CliPrinter.chosenColor);
                    } else {
                        System.out.print(workerColor(boardView.getWorkerList()[i].getBelongToPlayer()));
                    }

                    System.out.print("W" + i%2);

                    if(checkCanOp(t)) {
                        printCanOp();
                    } else {
                        System.out.print(CliPrinter.heightColor);
                    }
                    System.out.print("(" + t.getBlockLevel() + ")");
                }
            }
        }
    }


    public void printCanOp() {
        System.out.print(CliPrinter.canOpColor);
    }


    public void printDome() {
        System.out.print(CliPrinter.domeColor + "  ^  ");
    }

    public void printBlock(Tile t) {
        if(checkCanOp(t)) {
            printCanOp();
        }
        System.out.print("  " + t.getBlockLevel() + "  ");
    }


    public boolean checkCanOp(Tile t) {

        if(current) {
            if(boardView.getChosenWorkerID()!=-1) {
                WorkerView chosen = boardView.getWorkerList()[boardView.getChosenWorkerID()];

                if(chosen.getState()==1) {
                    return chosen.getMovableList().contains(t);
                }
                if(chosen.getState()==2 || chosen.getState()==3) {
                    return chosen.getBuildableList().contains(t);
                }
            }
        }

        return false;
    }

    public String workerColor(int playerID) {

        if(playerID==0) {
            return CliPrinter.player0;
        } else if(playerID==1) {
            return CliPrinter.player1;
        } else {
            return CliPrinter.player2;
        }
    }

}


