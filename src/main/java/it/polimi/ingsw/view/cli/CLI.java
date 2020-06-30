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
    private boolean lostPlayer = false;
    private String lostName = " ";

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
        System.out.println(CliPrinter.emptyLeft + CliPrinter.emptyRight);

        System.out.print(CliPrinter.leftExt + "    " + CliPrinter.verticalBar);
        for(int i=0;i<5;i++){
            System.out.print(CliPrinter.INDEX_COLOR + "   " + i + "   " + CliPrinter.verticalBar);
        }
        System.out.print(CliPrinter.centerExt);
        System.out.format("%1$-36s", "    Current Player:");
        System.out.println(CliPrinter.centerExt);

        System.out.println(CliPrinter.leftExt + CliPrinter.supBoard + CliPrinter.centerExt + CliPrinter.emptyRight);

        for(int i=0;i<9;i++) {

            if (i%2==0) {
                System.out.print(CliPrinter.leftExt + CliPrinter.INDEX_COLOR + " " + i/2 + "  " + CliPrinter.RESET);

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
                System.out.print(CliPrinter.verticalBoard + CliPrinter.centerExt);

            } else {
                System.out.print(CliPrinter.leftExt + CliPrinter.horizontalBar + CliPrinter.centerExt);
            }

            if (i==0) {
                System.out.print(workerColor(boardView.getCurrentID()));
                System.out.format("%1$-40s", "    " + boardView.getCurrentName() + CliPrinter.RESET + " (" + boardView.getCurrentGod() + ")");
            } else if (i==5 || i==7) {
                int index = i==5 ? 0 : 1;
                printPlayer(index);
            } else {
                if(i==2) {
                    System.out.format("%1$-47s",CliPrinter.LOGO_COLOR + "     (`  _   _  _│_  _   _ .  _  o  " + CliPrinter.RESET);
                } else if(i==3) {
                    System.out.format("%1$-47s",CliPrinter.LOGO_COLOR + "   * _) (_│ | |  │  (_) |  │ | | │ *" + CliPrinter.RESET);
                } else {
                    System.out.format("%1$-36s", "");
                }
            }
            System.out.println(CliPrinter.centerExt);
        }

        System.out.print(CliPrinter.leftExt + CliPrinter.infBoard + CliPrinter.centerExt);
        if(playerList.size()==3) {
            printPlayer(2);
            System.out.println(CliPrinter.centerExt);
        } else {
            System.out.println(CliPrinter.emptyRight);
        }
        System.out.println(CliPrinter.emptyLeft + CliPrinter.emptyRight);
        System.out.println(CliPrinter.downExt);
    }


    private void printWorker(Tile t) {

        for (int i=0; i < boardView.getWorkerList().length; i++) {

            if (boardView.getWorkerList()[i].isPositionSet()) {
                if (t.equals(boardView.getWorkerList()[i].getPosition())) {

                    if(checkCanOp(t)) {
                        printCanOp();
                    }
                    if (i == boardView.getChosenWorkerID()) {
                        System.out.print(CliPrinter.CHOSEN_COLOR);
                    } else {
                        System.out.print(workerColor(boardView.getWorkerList()[i].getBelongToPlayer()));
                    }

                    System.out.print("W" + i%2);

                    if(checkCanOp(t)) {
                        printCanOp();
                    } else {
                        System.out.print(CliPrinter.BLOCK_COLOR);
                    }
                    System.out.print("(" + t.getBlockLevel() + ")");
                }
            }
        }
    }


    private void printCanOp() {
        System.out.print(CliPrinter.CAN_OP_COLOR);
    }


    private void printDome() {
        System.out.print(CliPrinter.DOME_COLOR + "  ^  ");
    }


    private void printBlock(Tile t) {
        if(checkCanOp(t)) {
            printCanOp();
        }
        System.out.print("  " + t.getBlockLevel() + "  ");
    }

    private void printPlayer(int index) {
        if(playerList.get(index).equals(nickname)) {
            System.out.print("  - ");
        } else {
            System.out.print("    ");
        }

        if(!playerList.get(index).equals(lostName)) {
            System.out.format("%1$-43s", workerColor(index)+ playerList.get(index) + CliPrinter.RESET + " (" + godList[index] + ")");
        } else {
            System.out.format("%1$-43s", CliPrinter.GRID_COLOR + playerList.get(index) + " (" + godList[index] + ")" + CliPrinter.RESET);
        }
    }


    private boolean checkCanOp(Tile t) {

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

    private String workerColor(int playerID) {

        if(playerID==0) {
            return CliPrinter.PLAYER_0;
        } else if(playerID==1) {
            return CliPrinter.PLAYER_1;
        } else {
            return CliPrinter.PLAYER_2;
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
            System.out.println(workerColor(list.indexOf(str)) + str + CliPrinter.RESET);
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
            System.out.print( " (" + god.getAnswer1() + "/" + god.getAnswer2() + ")");
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
            lostName = obj.getPlayer();
            if(lostName.equals(nickname)) {
                System.out.println("You lose!");
            } else {
                System.out.println("The player " + lostName + " loses!");
            }
        }
    }

}


