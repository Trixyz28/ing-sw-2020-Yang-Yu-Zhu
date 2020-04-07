package it.polimi.ingsw.model;

public class Map {

    private Tile[][] map = new Tile[5][5];
    public Tile[][] getMap() {
        return map;
    }


    public Map() {
        initializeTiles();
    }


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



}
