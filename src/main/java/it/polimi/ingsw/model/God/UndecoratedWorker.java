package it.polimi.ingsw.model.God;

//interfaccia estesa da tutte le divinità decoratrici

import it.polimi.ingsw.model.Tile;

public interface UndecoratedWorker {

    boolean canMove(Tile t);

    void move(Tile t);

    boolean canBuildBlock(Tile t);

    void buildBlock(Tile t);

    boolean canBuildDome(Tile t);

    void buildDome(Tile t);

}