package it.polimi.ingsw.model.God;

//class that implements undecorated worker

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;


public class NoGod implements UndecoratedWorker {

    private Tile position;
    private int belongToPlayer;
    private int workerID;
    private boolean godPower;
    private Conditions condition;
    private int state;  /* 0 = Waiting   1 = Moving  2 = Building */

    public NoGod (int playerID, Conditions condition){
       belongToPlayer = playerID;
       this.condition = condition;
       state = 0;
    }

   //Returns a list of available tiles where the worker can be moved to
   @Override
   public List<Tile> canMove() {  /* canMoveUp condition */
      List<Tile> tempList = new ArrayList<>();
      for (Tile tile : position.getAdjacentTiles()){
         if(position.availableToMove(tile)){
            if(condition.canMoveUp() || position.getBlockLevel() >= tile.getBlockLevel()) {
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
      setPosition(t);
   }

   //Check if the worker can build a block(not dome!) on tile t
   @Override
   public boolean canBuildBlock(Tile t) {
      return position.availableToBuild(t) && t.getBlockLevel() < 3 && getConditions().checkBuildCondition(t) ;
   }

   //Build a block on tile t
   @Override
   public void buildBlock(Tile t) {
       t.addBlockLevel();
   }

   //Check if the worker can build a dome on t
   @Override
   public boolean canBuildDome(Tile t) {
      return position.availableToBuild(t) && t.getBlockLevel() == 3;
   }

   //Build a dome on t
   @Override
   public void buildDome(Tile t) {
       t.blockToDome();
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

   @Override
   public boolean getGodPower() {
      return godPower;
   }

   @Override
   public void setGodPower(boolean b) {
      godPower = b;
   }

   @Override
   public Conditions getConditions() {
      return condition;
   }

   @Override
   public void useGodPower(boolean use) {
      if(!use){
         setGodPower(false);
      }
   }

   @Override
   public int getBelongToPlayer() {
      return belongToPlayer;
   }

   @Override
   public boolean checkWin(Tile initialTile) {
      return (condition.checkWinCondition(position) && initialTile.getBlockLevel() == 2 && getPosition().getBlockLevel()==3);
   }

   @Override
   public void nextState() {
       if(state < 2) {
          state++;
       }else {
          state = 0;
       }
   }

   @Override
   public void setState(int state) {
      this.state = state;
   }

   @Override
   public int getState() {
      return state;
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