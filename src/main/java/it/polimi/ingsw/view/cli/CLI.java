package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.Ui;

import java.util.Scanner;


public class CLI implements Ui {

    Scanner in = new Scanner(System.in);
    private BoardView boardView;

    //Color indicators
    private final String RESET = Colors.RESET;

    private String gridColor = Colors.BLACK_BRIGHT;
    private String gridNumberColor = Colors.YELLOW;

    private String domeColor = Colors.BLUE_BOLD;
    private String heightColor = Colors.WHITE;

    private String player0 = Colors.GREEN_BOLD;
    private String player1 = Colors.PURPLE_BOLD;
    private String player2 = Colors.CYAN_BOLD;

    private String chosenColor = Colors.RED_UNDERLINED;
    private String canOpColor = Colors.BLACK_BACKGROUND_BRIGHT;

    private String horizontalBar = gridColor + "────║───────┼───────┼───────┼───────┼───────║" + RESET;
    private String verticalBar = gridColor + "│" + RESET;
    private final String supBoard = gridColor + "────╔═══════╤═══════╤═══════╤═══════╤═══════╗" + RESET;
    private final String infBoard = gridColor + "────╚═══════╧═══════╧═══════╧═══════╧═══════╝" + RESET;
    private String verticalBoard = gridColor + "║" + RESET;


    private String extColor = Colors.WHITE;
    private String upExt = extColor + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET;
    private String downExt = extColor + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET;
    private String leftExt = extColor + "┃   " + RESET;
    private String rightExt = extColor + "   ┃" + RESET;
    private String emptyExt = extColor + "┃                                                   ┃" + RESET;
    private String barExt = extColor + "┠─── ─── ─── ─── ─── ─── ─── ─── ─── ─── ─── ─── ───┨" + RESET;


    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput() {
        return in.nextLine();
    }


    @Override
    public void showBoard(BoardView boardView) {

        this.boardView = boardView;

        System.out.println(upExt);
        System.out.println(emptyExt);
        System.out.print(leftExt + "    " + verticalBar);

        for(int i=0;i<5;i++){
            System.out.print(gridNumberColor + "   " + i + "   " + verticalBar);
        }
        System.out.println(rightExt + "\n" + leftExt + supBoard + rightExt);


        for(int i=0;i<5;i++) {
            System.out.print(leftExt + gridNumberColor + " " + i + "  " + RESET);

            for(int j=0;j<5;j++) {
                Tile t = boardView.getTile(i,j);
                if(j==0) {
                    System.out.print(verticalBoard + " ");
                } else {
                    System.out.print(verticalBar + " ");
                }


                if(t.isOccupiedByWorker()) {
                    printWorker(t);
                } else if(t.isDomePresence()) {
                    printDome(t);
                } else {
                    printBlock(t);
                }

                System.out.print(RESET + " ");
            }

            System.out.println(verticalBoard + rightExt);
            if(i==4) {
                System.out.println(leftExt + infBoard + rightExt);
            } else {
                System.out.println(leftExt + horizontalBar + rightExt);
            }

        }

        System.out.println(emptyExt);
        System.out.println(barExt);
        System.out.print(leftExt + " Current player: ");
        System.out.print(workerColor(boardView.getCurrentID()));
        System.out.format("%1$-32s", boardView.getCurrentName() + RESET + " (" + boardView.getCurrentGod() + ")");
        System.out.println(rightExt);
        System.out.println(emptyExt);
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


    public void printDome(Tile t) {
        System.out.print(domeColor + "  ^  ");
    }

    public void printBlock(Tile t) {
        if(checkCanOp(t)) {
            printCanOp();
        }
        System.out.print("  " + t.getBlockLevel() + "  ");
    }


    public boolean checkCanOp(Tile t) {
        if(boardView.getChosenWorkerID()!=-1) {
            WorkerView chosen = boardView.getWorkerList()[boardView.getChosenWorkerID()];

            if(chosen.getState()==1) {
                return chosen.getMovableList().contains(t);
            }
            if(chosen.getState()==2 || chosen.getState()==3) {
                return chosen.getBuildableList().contains(t);
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


