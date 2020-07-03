package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;

import java.util.List;

/**
 * Interface that implements the user interface of the game.
 * <p></p>
 * User Interface are both the CLI and the GUI.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public interface Ui {

    /**
     *Displays a parameter encapsulated in an <code>Obj</code> object.
     * @param message Variable that was is encapsulated in the obj and needs to be show.
     */
    void showObj(Obj message);

    //Display a message

    /**
     *Displays a message on the user interface.
     * @param str Variable that needs to be shown to the user.
     */
    void showMessage(String str);

    /**
     *Handles the list of the players.
     * @param list Variables that is made by a list of many <code>Player</code> objects.
     */
    void handlePlayerList(List<String> list);

    /**
     *Handles the turns flow.
     * @param message Variable that represents nickname of the player that owns the incoming turn.
     */
    void handleTurn(String message);

    /**
     *Handles the Gods chosen.
     * @param message Variable that represents the name of the God chosen.
     */
    void handleDefineGod(String message);

    /**
     *Handles the choice of a God by a player.
     * @param obj Variable that represents both the name of the player and the name of the God at issue.
     */
    void handleChooseGod(Obj obj);

    /**
     *Updates the board.
     * @param boardView Variable that represents the last updated boardView and needs to be used as blueprints for all the boards of the UIs.
     */
    void updateBoard(BoardView boardView);

    /**
     *Shows a parameter that is used for Model updating.
     * @param message Variable that represents the message that needs to be shown to the user.
     */
    void handleBoardMsg(String message);

    /**
     *Shows a parameter that is used for Model updating and needs a specific answer.
     * @param message Variable that represents the message that needs to be shown to the user.
     */
    void handleGameMsg(String message);

    /**
     *Shows a parameter that is used to show the results of the game.
     * @param obj Variable that represents the results of a game.
     */
    void endGame(Obj obj);
}
