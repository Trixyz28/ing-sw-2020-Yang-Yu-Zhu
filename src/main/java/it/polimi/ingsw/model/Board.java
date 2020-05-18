package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable {

    //map creation 5x5 blocks
    private Tile[][] map = new Tile[5][5];


    //Constructor for the map
    public Board() {
        initializeTiles();
    }


    //get() of the map
    public Tile[][] getMap() {
        return map;
    }


    //Initialize the empty board
    public void initializeTiles(){
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                map[i][j] = new Tile();

                map[i][j].setRow(i);
                map[i][j].setColumn(j);

            }
        }
    }

    //Fill the adjacent tiles list for a tile
    public List<Tile> getAdjacentList(Tile t) {

        ArrayList<Tile> adjacentList = new ArrayList<>();
        int row = t.getRow();
        int column = t.getColumn();

        for(int i=-1;i<2 && row+i<5;i++) {
            for(int j=-1;j<2 && column+j<5;j++) {

                if(row+i>=0 && column+j>=0 && t.isAdjacentTo(getTile(row+i,column+j))) {
                    adjacentList.add(map[row+i][column+j]);
                }
            }
        }
        return adjacentList;
    }


    public Tile getTile(int i, int j) {
        return map[i][j];
    }

    //da implementare insieme all'init controller metodi che permettono ai giocatori
 // di mettere i workers e aggiornare la board es: public void mapUpdate()

}
