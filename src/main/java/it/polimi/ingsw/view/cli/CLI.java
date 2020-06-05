package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.Ui;

import java.util.ArrayList;
import java.util.Scanner;


public class CLI implements Ui {


    Scanner in = new Scanner(System.in);

    //Color indicators
    private final String RESET = Colors.RESET;

    private String edgeColor = Colors.BLACK_BRIGHT;
    private String gridNumberColor = Colors.YELLOW;

    private String domeColor = Colors.BLUE_BOLD;
    private String heightColor = Colors.BLACK;

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

                /*
                if(t.isAdjacentTo(chosenWorker.getPosition()) && chosenWorker.getPosition().availableToMove(t)) {
                    printCanOp(t);
                } else {*/
                    if(t.isOccupiedByWorker()) {
                        printWorker(t,boardView.getWorkerList(),boardView.getChosenWorkerID());
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

    }


    public void printWorker(Tile t,WorkerView[] workerList,int chosenWorkerID) {

        for (int i = 0; i < workerList.length; i++) {

            if (workerList[i].isPositionSet()) {
                if (t.equals(workerList[i].getPosition())) {

                    if (i == chosenWorkerID) {
                        System.out.print(chosenColor);
                    } else {
                        System.out.print(workerList[i].getColor());
                    }

                    System.out.print("W" + i%2 + RESET + "(" + t.getBlockLevel() + ")");
                }
            }

        }
    }


    public void printCanOp(Tile t) {
        System.out.print(canOpColor);
        printBlock(t);
        System.out.print(RESET);
    }


    public void printDome(Tile t) {
        System.out.print(domeColor + "  \u0394  ");
    }

    public void printBlock(Tile t) {
        System.out.print("  " + t.getBlockLevel() + "  ");
    }

}


