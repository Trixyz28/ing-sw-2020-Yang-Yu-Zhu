package it.polimi.ingsw.model;

public class Map {

    private Tile[][] board = new Tile[5][5];

    public Map() {
        initializeTiles();
    }


    public void initializeTiles(){
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                board[i][j].setRow(i);
                board[i][j].setColumn(j);

                board[i][j].setBlockLevel(0);

                board[i][j].setOccupiedByWorker(false);
                board[i][j].setDomePresence(false);
            }
        }
    }



}
