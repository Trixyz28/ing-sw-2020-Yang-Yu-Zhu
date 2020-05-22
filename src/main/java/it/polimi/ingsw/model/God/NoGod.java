package it.polimi.ingsw.model.God;

//class that implements undecorated worker

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;


public class NoGod implements UndecoratedWorker {

    private Tile position;
    private int belongToPlayer;
    private int workerID;


   //Returns a list of available tiles where the worker can be moved to
   @Override
   public List<Tile> canMove(boolean canMoveUp) {  /* canMoveUp condition */
      List<Tile> tempList = new ArrayList<>();
      for (Tile tile : position.getAdjacentTiles()){
         if(position.availableToMove(tile)){
            if(canMoveUp || position.getBlockLevel() >= tile.getBlockLevel()) {  
               tempList.add(tile);
            }
         }
      }
      return tempList;
   }


   //Move the worker from position to t
   @Override
   public void move(Tile t) {
      position.setOccupiedByWorker(false);
      t.setOccupiedByWorker(true);
      position = t;
   }

   //Check if the worker can build a block(not dome!) on tile t
   @Override
   public boolean canBuildBlock(Tile t) {
      return position.availableToBuild(t) && t.getBlockLevel() < 3;
   }

   //Build a block on tile t
   @Override
   public void buildBlock(Tile t) {
      t.setBlockLevel(t.getBlockLevel()+1);
   }

   //Check if the worker can build a dome on t
   @Override
   public boolean canBuildDome(Tile t) {
      return position.availableToBuild(t) && t.getBlockLevel() == 3;
   }

   //Build a dome on t
   @Override
   public void buildDome(Tile t) {
      t.setDomePresence(true);
   }

   //Get the current position of the worker
   @Override
   public Tile getPosition() {
      return position;
   }

   //Set the current position
   @Override
   public void setPosition(Tile t) {
      position = t;
      t.setOccupiedByWorker(true);
   }



 /*all basic methods
    void move(){
        //metodo move() che lavora sulla lista dei worker
    };
    void canMove();
    void buildBlock();
    void canBuildBlock();
    void buildDome();
    void canBuildDome();
    void conditionsCreator();


  */

}