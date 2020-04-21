package it.polimi.ingsw.model;

public class Map {

    //map creation 5x5 blocks
    private Tile[][] map = new Tile[5][5];


    //Constructor for the map
    public Map() {

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
                map[i][j].setRow(i);
                map[i][j].setColumn(j);

                map[i][j].setBlockLevel(0);

                map[i][j].setOccupiedByWorker(false);
                map[i][j].setDomePresence(false);
            }
        }
    }

 //da implementare insieme all'init controller metodi che permettono ai giocatori
 // di mettere i workers e aggiornare la board es: public void mapUpdate()

}
