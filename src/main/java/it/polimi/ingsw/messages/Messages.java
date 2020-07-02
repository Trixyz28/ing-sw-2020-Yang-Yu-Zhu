package it.polimi.ingsw.messages;
/**
 * Class GodPowerMessage that is used by the implementation between clients and server for God's powers.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Messages {

    //Communications
    /**
     *Message that indicates that the connection was closed.
     */
    public static String connectionClosed = "Connection closed";

    /**
     *Message that indicates that the game is over.
     */
    public static String gameOver = "Game over!";

    /**
     *Message that indicates that the player at issue isn't the one who needs to make a move.
     */
    public static String spectator = "Be a patient spectator :D";

    /**
     *Message that indicates that the player lost.
     */
    public static String lose = "Hai perso!";

    //Requests
    /**
     *Message that indicates the player to choose a worker.
     */
    public static String worker = "Choose a Worker to move";

    /**
     *Message that indicates to select one specific worker.
     */
    public static String workerIndex = " (index 0 or 1)";

    /**
     *Message that asks to the player if he is sure with the choice of the worker.
     */
    public static String confirmWorker = "Confirm your choice";

    /**
     *Message that indicates the coordinates of the operation.
     */
    public static String operation = " (x,y)";

    /**
     *Message that indicates to the player to place the worker.
     */
    public static String place = "Place Worker ";

    /**
     *Message that indicates the player to do the Move action.
     */
    public static String move = "Move";

    /**
     *Message that indicates the player to do the Build action.
     */
    public static String Build = "Build";


    //Error messages
    /**
     *Message that indicates the player to try again.
     */
    public static String tryAgain = "Try again!";

    /**
     *Message that indicates the player that it's not his turn.
     */
    public static String wrongTurn = "It isn't your turn!";

    /**
     *Message that indicates the argument was not valid.
     */
    public static String wrongArgument = "Invalid argument!";

    /**
     *Message that indicates this is an invalid operation.
     */
    public static String wrongOperation = "Invalid operation";


    //Nickname
    /**
     *Message that indicates the player to give a nickname.
     */
    public static String nicknameRequest = "Welcome! What is your nickname?";

    /**
     *Message that indicates the player to choose another nickname.
     */
    public static String nicknameInUse = "Nickname in use, choose another one";


    /**
     *Message that indicates that the nickname chosen by the player can't be used.
     */
    public static String invalidNickname = "Invalid nickname (length bound: 1-16 characters)";


    /**
     *Message that indicates that the nickname chosen by the player can be used.
     */
    public static String nicknameAvailable = "You can use this nickname!";


    //Lobby
    /**
     *Message that indicates the player to create a lobby and to choose a number of players for the lobby.
     */
    public static String canCreateLobby = "You can create a lobby\nHow many players can join this match? (2-3)";

    /**
     *Message that indicates the player to chose one type of available lobby type.
     */
    public static String lobbyPlayerNumber = "Choose 2 or 3";

    /**
     *Message that indicates the match has started.
     */
    public static String matchStarting = "Match Starting\nPlayers in game are: ";

    //Challenger
    /**
     *Message that indicates the player that he is the challenger.
     */
    public static String challenger = "You are the challenger!";

    //Godlist
    /**
     *Message that indicates it is an invalid choice.
     */
    public static String invalidChoice = "Invalid choice";

    /**
     *Message that indicates that the challenger has chosen all the God Powers for the God Power list.
     */
    public static String challengerChosen = "The Challenger defined all God Powers!";

    /**
     *Message that indicates the player to choose a God Power out of the list.
     */
    public static String godRequest = "Choose your God Power";

    //Match
    /**
     *Message that indicates the challenger to choose the first player.
     */
    public static String chooseStartPlayer = "Choose the first player!";

    /**
     *Message that indicates which worker was chosen by the player.
     */
    public static String workerChose = "Worker chosen";

    /**
     *Message that indicates it is the turn of the player at issue.
     */
    public static String yourTurn = "Ecco il tuo turno!";

    /**
     *Message that indicates that the turn of the player at issue has terminated.
     */
    public static String endTurn = "Il tuo turno è terminato!";


    //Miscellaneous
    /**
     *Message that indicates that the chosen tile is invalid for the move or build action.
     */
    public static String invalidTile = "Invalid tile!";

    /**
     *Message that indicates that the chosen tile is occupied by a worker.
     */
    public static String occupiedTile = "Tile occupied!";


    /**
     *Message that indicates the player he has chosen a wrong index of the workers.
     */
    public static String invalidWorker = "Invalid Worker index!";

    /**
     *Message that indicates the player to choose another worker.
     */
    public static String anotherWorker = "Choose another Worker!";



}