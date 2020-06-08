package it.polimi.ingsw.model.God;

//interfaccia estesa da tutte le divinità decoratrici

import it.polimi.ingsw.model.Tile;

import java.util.List;

public interface UndecoratedWorker {

    boolean canMove(Tile t);

    void move(Tile t);

    boolean canBuildBlock(Tile t);

    void buildBlock(Tile t);

    boolean canBuildDome(Tile t);

    void buildDome(Tile t);

    Tile getPosition();

    void setPosition(Tile t);

    boolean getGodPower();

    void setGodPower(boolean b);

    Conditions getConditions();

    void useGodPower(boolean use);

    int getBelongToPlayer();

    boolean checkWin(Tile initialTile);

    void nextState();

    void setState(int state);

    int getState();

}