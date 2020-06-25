package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.WorkerView;

import java.util.Scanner;


public class CLI implements Ui {

    Scanner in = new Scanner(System.in);
    private BoardView boardView;
    private String nickname;
    private boolean current;

    //Color indicators
    private final String RESET = Colors.RESET;

    private final String gridColor = Colors.BLACK_BRIGHT;
    private final String gridNumberColor = Colors.YELLOW;

    private final String domeColor = Colors.BLUE_BOLD;
    private final String heightColor = Colors.WHITE;

    private final String player0 = Colors.CYAN_BOLD;
    private final String player1 = Colors.PURPLE_BOLD;
    private final String player2 = Colors.GREEN_BOLD;

    private String chosenColor = Colors.RED_UNDERLINED;
    private String canOpColor = Colors.BLACK_BACKGROUND_BRIGHT;

    private String horizontalBar = gridColor + "────║───────┼───────┼───────┼───────┼───────║" + RESET;
    private String verticalBar = gridColor + "│" + RESET;
    private final String supBoard = gridColor + "────╔═══════╤═══════╤═══════╤═══════╤═══════╗" + RESET;
    private final String infBoard = gridColor + "────╚═══════╧═══════╧═══════╧═══════╧═══════╝" + RESET;
    private String verticalBoard = gridColor + "║" + RESET;

    private String extColor = Colors.WHITE;
    private String upExt = extColor + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET;
    private String downExt = extColor + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET;
    private String leftExt = extColor + "┃   " + RESET;
    private String rightExt = extColor + "   ┃" + RESET;
    private String emptyExt = extColor + "┃                                                   ┃" + RESET;



    @Override
    public void showMessage(String message) {

        if(message.equals(Messages.Place) || message.equals(Messages.Move) || message.equals(Messages.Build)) {
            System.out.println(message + Messages.Operation);
        } else {
            System.out.println(message);
        }

    }

    @Override
    public String getInput() {
        return in.nextLine();
    }


    @Override
    public void showGameMsg(GameMessage message) {
        System.out.println(message.getMessage());
    }

    @Override
    public void showObj(Obj obj) {
        String command = obj.getTag();


        if (command.equals("playerList")) {
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

        } else if(command.equals("currentList")) {
            if(obj.getList().size()!=0) {
                System.out.println("Actual cards:");
                for(String str : obj.getList()) {
                    System.out.println(str);
                }
            }
        } else if(command.equals("board")) {
            showBoard(obj.getBoardView(),obj.getBoardView().getCurrentName().equals(nickname));
        } else if(command.equals("gMsg")) {
            System.out.println(obj.getGameMessage().getMessage());
        }
        else {
            System.out.println(obj.getMessage());
        }
    }


    public void showBoard(BoardView boardView,boolean command) {

        this.boardView = boardView;
        this.current = command;

        System.out.println(upExt);

        System.out.print(emptyExt);
        System.out.format("%1$52s",rightExt + "\n");

        System.out.print(leftExt + "    " + verticalBar);

        for(int i=0;i<5;i++){
            System.out.print(gridNumberColor + "   " + i + "   " + verticalBar);
        }
        System.out.print(rightExt);
        System.out.format("%1$52s",rightExt + "\n");

        System.out.print(leftExt + supBoard + rightExt);
        System.out.format("%1$52s",rightExt + "\n");


        for(int i=0;i<9;i++) {

            if (i%2==0) {
                System.out.print(leftExt + gridNumberColor + " " + i/2 + "  " + RESET);

                for (int j=0;j<5;j++) {
                    Tile t = boardView.getTile(i/2,j);

                    if(j==0) {
                        System.out.print(verticalBoard + " ");
                    } else {
                        System.out.print(verticalBar + " ");
                    }

                    if (t.isOccupiedByWorker()) {
                        printWorker(t);
                    } else if (t.isDomePresence()) {
                        printDome();
                    } else {
                        printBlock(t);
                    }

                    System.out.print(RESET + " ");
                }

                System.out.print(verticalBoard + rightExt);


            } else {
                System.out.print(leftExt + horizontalBar + rightExt);
            }


            if (i == 1) {
                System.out.format("%1$-36s", "    Current Player:");
                System.out.println(rightExt);
            } else if (i == 3) {
                System.out.print(workerColor(boardView.getCurrentID()));
                System.out.format("%1$-40s", "    " + boardView.getCurrentName() + RESET + " (" + boardView.getCurrentGod() + ")");
                System.out.println(rightExt);
            } else {
                System.out.format("%1$52s",rightExt + "\n");
            }
        }

        System.out.print(leftExt + infBoard + rightExt);
        System.out.format("%1$52s",rightExt + "\n");
        System.out.print(emptyExt);
        System.out.format("%1$52s",rightExt + "\n");
        System.out.println(downExt);
    }


    public void printWorker(Tile t) {

        for (int i = 0; i < boardView.getWorkerList().length; i++) {

            if (boardView.getWorkerList()[i].isPositionSet()) {
                if (t.equals(boardView.getWorkerList()[i].getPosition())) {

                    if(checkCanOp(t)) {
                        printCanOp();
                    }
                    if (i == boardView.getChosenWorkerID()) {
                        System.out.print(chosenColor);
                    } else {
                        System.out.print(workerColor(boardView.getWorkerList()[i].getBelongToPlayer()));
                    }

                    System.out.print("W" + i%2);

                    if(checkCanOp(t)) {
                        printCanOp();
                    } else {
                        System.out.print(heightColor);
                    }
                    System.out.print("(" + t.getBlockLevel() + ")");
                }
            }

        }
    }


    public void printCanOp() {
        System.out.print(canOpColor);
    }


    public void printDome() {
        System.out.print(domeColor + "  ^  ");
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
            return player0;
        } else if(playerID==1) {
            return player1;
        } else {
            return player2;
        }
    }

}


