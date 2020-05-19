package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Print {

    Board board;
    List<WorkerSample> workerList0;
    List<WorkerSample> workerList1;
    List<WorkerSample> workerList2;
    WorkerSample chosenWorker;
    List<WorkerSample>[] totalList;

    public Print() {
        board = new Board();
        workerList0 = new ArrayList<>();
        workerList1 = new ArrayList<>();
        workerList2 = new ArrayList<>();

        setWorker();
        totalList = new List[3];
        totalList[0] = workerList0;
        totalList[1] = workerList1;
        totalList[2] = workerList2;

        setBoard();

        workerColors = new String[3];
        setColors();
    }

    public void setWorker() {
        WorkerSample w1 = new WorkerSample();
        w1.setPosition(board.getTile(2,2));
        board.getTile(2,2).setOccupiedByWorker(true);
        workerList0.add(w1);

        WorkerSample w2 = new WorkerSample();
        w2.setPosition(board.getTile(3,2));
        board.getTile(3,2).setOccupiedByWorker(true);
        workerList1.add(w2);

        WorkerSample w3 = new WorkerSample();
        w3.setPosition(board.getTile(0,0));
        board.getTile(0,0).setOccupiedByWorker(true);
        workerList2.add(w3);

        WorkerSample w4 = new WorkerSample();
        w4.setPosition(board.getTile(3,0));
        board.getTile(3,0).setOccupiedByWorker(true);
        workerList0.add(w4);

        WorkerSample w5 = new WorkerSample();
        w5.setPosition(board.getTile(0,4));
        board.getTile(0,4).setOccupiedByWorker(true);
        workerList1.add(w5);

        chosenWorker = w2;
    }

    public void setBoard() {
        board.getTile(0,1).setDomePresence(true);
        board.getTile(4,4).setDomePresence(true);
        board.getTile(0,3).setBlockLevel(1);
        board.getTile(3,3).setBlockLevel(2);
        board.getTile(4,1).setBlockLevel(1);
        board.getTile(2,4).setBlockLevel(3);
    }

    public void setColors() {
        workerColors[0]=worker0;
        workerColors[1]=worker1;
        workerColors[2]=worker2;
    }
    private String RESET = Colors.RESET;

    private String horizontalBar = Colors.BLACK_BRIGHT + "---------------------------------------------" + Colors.RESET;
    private String verticalBar = Colors.BLACK_BRIGHT + "|" + RESET;

    private String gridNumberColor = Colors.YELLOW;
    private String[] workerColors;
    private String worker0 = Colors.GREEN_BOLD;
    private String worker1 = Colors.PURPLE_BOLD;
    private String worker2 = Colors.CYAN_BOLD;
    private String chosenColor = Colors.RED_UNDERLINED;
    private String domeColor = Colors.BLUE_BOLD;
    private String heightColor = Colors.BLACK;
    private String canOpColor = Colors.BLACK_BACKGROUND_BRIGHT;


    public void printBoard() {

        System.out.println("");
        System.out.print("    " + verticalBar);
        for(int i=0;i<5;i++){
            System.out.print(gridNumberColor + "   " + i + "   " + verticalBar);
        }
        System.out.println("");
        System.out.println(horizontalBar);


        for(int i=0;i<5;i++) {

            System.out.print(gridNumberColor + " " + i + "  " + RESET);

            for(int j=0;j<5;j++) {
                Tile t = board.getTile(i,j);
                System.out.print(verticalBar + " ");

                if(t.isAdjacentTo(chosenWorker.getPosition()) && chosenWorker.getPosition().availableToMove(t)) {
                    printCanOp(t);
                } else {
                    if(t.isOccupiedByWorker()) {
                        printWorker(t);
                    } else if(t.isDomePresence()) {
                        System.out.print(domeColor + "  D  ");
                    } else {
                        System.out.print("  " + board.getTile(i,j).getBlockLevel() + "  ");
                    }
                }

                System.out.print(RESET + " ");
            }

            System.out.println(verticalBar);
            System.out.println(horizontalBar);

        }

    }


    public void printWorker(Tile t) {

        for(int i=0;i<3;i++) {
            for(int j=0;j<2;j++) {

                if(j<totalList[i].size()) {
                    if(t.equals(totalList[i].get(j).getPosition())) {
                        if(t.equals(chosenWorker.getPosition())) {
                            System.out.print(chosenColor);
                        } else {
                            System.out.print(workerColors[i]);
                        }
                        System.out.print("W" + j + RESET + "(" + t.getBlockLevel() + ")");
                    }
                }
            }
        }

    }


    public void printCanOp(Tile t) {
        System.out.print(canOpColor + "  " + t.getBlockLevel() + "  ");
    }


}
