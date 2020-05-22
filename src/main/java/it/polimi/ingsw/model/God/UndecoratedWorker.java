package it.polimi.ingsw.model.God;

//interfaccia estesa da tutte le divinità decoratrici

import it.polimi.ingsw.model.Tile;

import java.util.List;

public interface UndecoratedWorker {

    List<Tile> canMove(boolean canMoveUp);

    void move(Tile t);

    boolean canBuildBlock(Tile t);

    void buildBlock(Tile t);

    boolean canBuildDome(Tile t);

    void buildDome(Tile t);

    Tile getPosition();

    void setPosition(Tile t);

}