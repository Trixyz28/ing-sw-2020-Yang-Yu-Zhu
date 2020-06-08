package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.Ui;

import java.util.ArrayList;
import java.util.Scanner;


public class CLI implements Ui {


    Scanner in = new Scanner(System.in);
    BoardView boardView;

    //Color indicators
    private final String RESET = Colors.RESET;

    private String edgeColor = Colors.BLACK_BRIGHT;
    private String gridNumberColor = Colors.YELLOW;

    private String domeColor = Colors.BLUE_BOLD;
    private String heightColor = Colors.WHITE;

    private String chosenColor = Colors.RED_UNDERLINED;
    private String canOpColor = Colors.BLACK_BACKGROUND_BRIGHT;

    private String horizontalBar = edgeColor + "────║───────┼───────┼───────┼───────┼───────║" + RESET;
    private String verticalBar = edgeColor + "│" + RESET;
    private final String supBoard = edgeColor + "────╔═══════╤═══════╤═══════╤═══════╤═══════╗" + RESET;
    private final String infBoard = edgeColor + "────╚═══════╧═══════╧═══════╧═══════╧═══════╝" + RESET;
    private String verticalBoard = edgeColor + "║" + RESET;



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

        System.out.print("\n" + "    " + verticalBar);

        for(int i=0;i<5;i++){
            System.out.print(gridNumberColor + "   " + i + "   " + verticalBar);
        }
        System.out.println("\n" + supBoard);


        for(int i=0;i<5;i++) {
            System.out.print(gridNumberColor + " " + i + "  " + RESET);

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

            System.out.println(verticalBoard);
            if(i==4) {
                System.out.println(infBoard);
            } else {
                System.out.println(horizontalBar);
            }

        }
        System.out.println(edgeColor + "    Current player: " + boardView.getCurrentName() + RESET);

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
                        System.out.print(boardView.getWorkerList()[i].getColor());
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

}


