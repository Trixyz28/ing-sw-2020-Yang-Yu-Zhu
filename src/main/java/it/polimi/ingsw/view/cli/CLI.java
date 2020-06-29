package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.WorkerView;

import java.util.ArrayList;


public class CLI implements Ui {

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

        switch (command) {
            case Tags.setName -> nickname = obj.getMessage();
            case Tags.completeList -> printCompleteList(obj.getList());
            case Tags.currentList -> printCurrentList(obj.getList());
            default -> System.out.println(obj.getMessage());
        }
    }


    @Override
    public void updateBoard(BoardView boardView) {

        this.boardView = boardView;
        this.current = boardView.getCurrentName().equals(nickname);

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

    public void printCompleteList(ArrayList<String> list) {
        System.out.println("You can choose godcards from this list:");
        for(String str : list) {
            System.out.println(str);
        }
    }

    public void printCurrentList(ArrayList<String> list) {
        if(list.size()!=0) {
            System.out.println("Actual cards:");
            for(String str : list) {
                System.out.println(str);
            }
        }
    }

    @Override
    public void handlePlayerList(ArrayList<String> list) {
        playerList = list;
        godList = new String[playerList.size()];

        System.out.println(Messages.matchStarting);
        for(String str : list) {
            System.out.println(str);
        }
    }

    @Override
    public void handleTurn(String message) {
        System.out.println("Turn of " + message);
    }

    @Override
    public void handleDefineGod(String message) {
        System.out.println("The challenger chooses: " + message);
    }

    @Override
    public void handleChooseGod(Obj obj) {
        System.out.println("The player " + obj.getPlayer() + " chooses " + obj.getMessage() + "!");
        godList[playerList.indexOf(obj.getPlayer())] = obj.getMessage();
    }

    @Override
    public void handleBoardMsg(String message) {
        System.out.print(message);
        if(message.equals(Messages.move) || message.equals(Messages.Build)) {
            System.out.println(Messages.operation);
        } else {
            System.out.println("");
        }
    }


    @Override
    public void handleGameMsg(String message) {

        System.out.print(message);
        if(message.equals(Messages.worker)) {
            System.out.print(Messages.workerIndex);
        } else if (message.equals(Messages.confirmWorker)) {
            System.out.print(" (YES/NO)");
        } else {
            GodPowerMessage god = GodPowerMessage.valueOf(boardView.getCurrentGod());
            System.out.print( "(" + god.getAnswer1() + "/" + god.getAnswer2() + ")");
        }
        System.out.println("");
    }


    @Override
    public void endGame(Obj obj) {
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
    }

}


