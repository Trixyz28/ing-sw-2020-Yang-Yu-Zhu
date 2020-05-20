package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.Ui;

import java.util.ArrayList;
import java.util.Scanner;


public class CLI implements Ui {

    Scanner in = new Scanner(System.in);

    //Color indicators
    private final String RESET = Colors.RESET;

    private final String horizontalBar = Colors.BLACK_BRIGHT + "---------------------------------------------" + Colors.RESET;
    private final String verticalBar = Colors.BLACK_BRIGHT + "|" + RESET;
    private String gridNumberColor = Colors.YELLOW;

    private String[] workerColors = new String[3];
    private String worker0 = Colors.GREEN_BOLD;
    private String worker1 = Colors.PURPLE_BOLD;
    private String worker2 = Colors.CYAN_BOLD;

    private String domeColor = Colors.BLUE_BOLD;
    private String heightColor = Colors.BLACK;

    private String chosenColor = Colors.RED_UNDERLINED;
    private String canOpColor = Colors.BLACK_BACKGROUND_BRIGHT;

    public CLI() {
        setWorkerColors();
    }


    private void setWorkerColors() {
        workerColors[0] = worker0;
        workerColors[1] = worker1;
        workerColors[2] = worker2;
    }


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

        System.out.println();
        System.out.print("    " + verticalBar);

        for(int i=0;i<5;i++){
            System.out.print(gridNumberColor + "   " + i + "   " + verticalBar);
        }
        System.out.println("");
        System.out.println(horizontalBar);


        for(int i=0;i<5;i++) {
            System.out.print(gridNumberColor + " " + i + "  " + RESET);

            for(int j=0;j<5;j++) {
                Tile t = boardView.getTile(i,j);
                System.out.print(verticalBar + " ");

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

            System.out.println(verticalBar);
            System.out.println(horizontalBar);

        }

    }


    public void printWorker(Tile t,WorkerView[] workerList,int chosenWorkerID) {

        for (int i = 0; i < workerList.length; i++) {

            if (workerList[i].isPositionSet()) {
                if (t.equals(workerList[i].getPosition())) {

                    if (i == chosenWorkerID) {
                        System.out.print(chosenColor);
                    } else {
                        System.out.print(workerColors[i/2]);
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
        System.out.print(domeColor + "  D  ");
    }

    public void printBlock(Tile t) {
        System.out.print("  " + t.getBlockLevel() + "  ");
    }


}


