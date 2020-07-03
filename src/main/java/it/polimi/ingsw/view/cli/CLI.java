package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.WorkerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the CLI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class CLI implements Ui {

    private BoardView boardView;
    private String nickname;
    private boolean current;
    private String lostName = " ";

    private List<String> playerList;
    private String[] godList;

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void showObj(Obj obj) {
        String command = obj.getTag();

        switch (command) {
            case Tags.SET_NAME -> nickname = obj.getMessage();
            case Tags.COMPLETE_LIST -> printCompleteList(obj.getList());
            case Tags.CURRENT_LIST -> printCurrentList(obj.getList());
            default -> System.out.println(obj.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     *Prints the worker for the CLI.
     * @param t Variable that indicates the <code>Tile</code> where the worker is.
     */
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

    /**
     *Prints the "move" and "build" operation possibility for the CLI.
     */
    private void printCanOp() {
        System.out.print(CliPrinter.CAN_OP_COLOR);
    }

    /**
     *Prints the Block for the CLI.
     */
    private void printDome() {
        System.out.print(CliPrinter.DOME_COLOR + "  ^  ");
    }

    /**
     *Prints the Block for the CLI.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     */
    private void printBlock(Tile t) {
        if(checkCanOp(t)) {
            printCanOp();
        }
        System.out.print("  " + t.getBlockLevel() + "  ");
    }

    /**
     *Prints the workers of the players for the CLI.
     * @param index Variable that indicates which worker is being printed.
     */
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

    /**
     *Checks if on the <code>Tile</code> selected can be done a "move" or "build" operation.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if a "move" or "build" operation can be done, otherwise <code>false</code>.
     */
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

    /**
     *Prints different colors for the different players.
     * @param playerID Variable that represents the ID of the player at issue.
     * @return A string of a particular color that is shown on the CLI of the client.
     */
    private String workerColor(int playerID) {

        if(playerID==0) {
            return CliPrinter.PLAYER_0;
        } else if(playerID==1) {
            return CliPrinter.PLAYER_1;
        } else {
            return CliPrinter.PLAYER_2;
        }
    }

    /**
     *Prints the complete GodList for the CLI for the player at issue.
     * @param list Variables that is a list of strings that represents all the God Cards.
     */
    public void printCompleteList(List<String> list) {
        System.out.println("You can choose godcards from this list:");
        for(String str : list) {
            System.out.println(str);
        }
    }

    /**
     *Prints the current GodList for the CLI for the player at issue.
     * @param list Variables that is a list of strings that represents all the available God Cards.
     */
    public void printCurrentList(List<String> list) {
        if(!list.isEmpty()) {
            System.out.println("Actual cards:");
            for(String str : list) {
                System.out.println(str);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlePlayerList(List<String> list) {
        playerList = list;
        godList = new String[playerList.size()];

        System.out.println(Messages.matchStarting);
        for(String str : list) {
            System.out.println(workerColor(list.indexOf(str)) + str + CliPrinter.RESET);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleTurn(String message) {
        System.out.println("Turn of " + message);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDefineGod(String message) {
        System.out.println("The challenger chooses: " + message);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleChooseGod(Obj obj) {
        System.out.println("The player " + obj.getPlayer() + " chooses " + obj.getMessage() + "!");
        godList[playerList.indexOf(obj.getPlayer())] = obj.getMessage();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleBoardMsg(String message) {
        System.out.print(message);
        if(message.equals(Messages.move) || message.equals(Messages.Build)) {
            System.out.println(Messages.operation);
        } else {
            System.out.println("");
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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


