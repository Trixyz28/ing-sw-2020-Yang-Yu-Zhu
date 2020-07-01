package it.polimi.ingsw.model.God;

//class that implements undecorated worker

import it.polimi.ingsw.model.Tile;

import java.util.List;
/**
 * This is the class that describes the basic methods which applies if there's no God Power involved.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class NoGod implements UndecoratedWorker {

    private Tile position;
    private int belongToPlayer;
    private boolean godPower;
    private Conditions condition;
    private int state;  /* 0 = Waiting   1 = Moving  2 = Building 3 = using other workers */

   /**
    *  Creates a <code>NoGod</code> with the specified attributes.
    * @param playerID Variable that indicates the player who owns the worker.
    * @param condition Variable that introduces special conditions based on specific Gods'powers.
    */
    public NoGod (int playerID, Conditions condition){
       belongToPlayer = playerID;
       this.condition = condition;
       state = 0;
    }

   //Check if the worker can move on Tile t
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean canMove(Tile t) {  /* canMoveUp condition */
      return position.availableToMove(t) && condition.checkMoveCondition(position, t);
   }


   //Move the worker from position to t
   /**
    * {@inheritDoc}
    */
   @Override
   public void move(Tile t) {
      position.setOccupiedByWorker(false);
      setPosition(t);
   }


   //Check if the worker can build a block(not dome!) on tile t
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean canBuildBlock(Tile t) {
      return position.availableToBuild(t) && t.getBlockLevel() < 3 && getConditions().checkBuildCondition(t) ;
   }

   //Build a block on tile t
   /**
    * {@inheritDoc}
    */
   @Override
   public void buildBlock(Tile t) {
       t.addBlockLevel();
   }

   //Check if the worker can build a dome on t
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean canBuildDome(Tile t) {
      return position.availableToBuild(t) && t.getBlockLevel() == 3;
   }

   //Build a dome on t
   /**
    * {@inheritDoc}
    */
   @Override
   public void buildDome(Tile t) {
       t.blockToDome();
   }

   //Get the current position of the worker
   /**
    * {@inheritDoc}
    */
   @Override
   public Tile getPosition() {
      return position;
   }

   //Set the current position
   /**
    * {@inheritDoc}
    */
   @Override
   public void setPosition(Tile t) {
      position = t;
      t.setOccupiedByWorker(true);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean getGodPower() {
      return godPower;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setGodPower(boolean b) {
      godPower = b;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Conditions getConditions() {
      return condition;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void useGodPower(boolean use) {
      if(!use){
         setGodPower(false);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getBelongToPlayer() {
      return belongToPlayer;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean checkWin(Tile initialTile) {
      return (condition.checkWinCondition(position) && initialTile.getBlockLevel() == 2 && getPosition().getBlockLevel()==3);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void nextState() {
       if(state < 2) {
          state++;
       }else {
          state = 0;
       }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setState(int state) {
      this.state = state;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getState() {
       if(godPower){
          /* waiting for answer */
          return 0;
       }
      return state;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Tile> getAdjacentTiles() {
      return position.getAdjacentTiles();
   }


}