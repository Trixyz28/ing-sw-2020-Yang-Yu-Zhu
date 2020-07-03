package it.polimi.ingsw.model;


/**
 * Board of the game where the game is played.
 * <p></p>
 * Matrix 5 x 5 made of <code>Tile</code> objects.
 * "Worker" to "Tile" ratio is 1-to-1.
 * In every <code>Tile</code> a "block" or "dome" can be built.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Board {

    //map 5x5 blocks
    private final Tile[][] map;

    //Constructor for the map

    /**
     * Creates a <code>Board</code> with the specified attributes.
     */
    public Board() {
        map = new Tile[5][5];
        initializeTiles();
    }


    //Initialize the empty board

    /**
     * Creates and sets all the tiles of the board.
     */
    public void initializeTiles(){
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                map[i][j] = new Tile();

                map[i][j].setRow(i);
                map[i][j].setColumn(j);
            }
        }

        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                setAdjacentList(map[i][j]);
            }
        }
    }


    //get() of the map

    /**
     * Gets the current board.
     * @return The matrix of tiles.
     */
    public Tile[][] getMap() {
        return map.clone();
    }


    //get() of a specific tile in the map

    /**
     * Gets a single <code>Tile</code> of the board.
     * @param i Row Value of the chosen <code>Tile</code>.
     * @param j Column Value of the chosen <code>Tile</code>.
     * @return A single <code>Tile</code> of the current <code>Board</code>.
     */
    public Tile getTile(int i, int j) {
        return map[i][j];
    }


    //Fill the adjacent tiles list for a tile

    /**
     * Creates a list of all the adjacent tiles near the chosen <code>Tile</code>.
     * @param t The chosen <code>Tile</code>
     */
    public void setAdjacentList(Tile t) {

        int row = t.getRow();
        int column = t.getColumn();

        for(int i=-1;i<2 && row+i<5;i++) {
            for(int j=-1;j<2 && column+j<5;j++) {

                if(row+i>=0 && column+j>=0 && t.isAdjacentTo(getTile(row+i,column+j))) {
                    t.getAdjacentTiles().add(map[row+i][column+j]);
                }
            }
        }
    }


}
