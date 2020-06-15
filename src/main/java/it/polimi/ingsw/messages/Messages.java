package it.polimi.ingsw.messages;

public class Messages {

    //Communications
    public static String connectionReady = "Connection established";
    public static String connectionClosed = "Connection closed";
    public static String gameOver = "Game over!";
    public static String spectator = "Be a patient spectator, you can't send messages :D";
    public static String lose = "Hai perso!";
    public static String startMatch = "Start!";

    //Requests
    public static String Worker = "Scegli il worker con cui vuoi fare la mossa: indice 0 o 1";
    public static String Operation = " (x,y)";
    public static String Place = "Place : ";
    public static String Move = "Move : ";
    public static String Build = "Build : ";


    //Error messages
    public static String tryAgain = "Riprova!";
    public static String wrongTurn = "Non è il tuo turnoooooo!";
    public static String wrongArgument = "Invalid argument!";
    public static String wrongOperation = "Impossibile fare questa mossa!";

/*
    //GodPower messages
    public static String Artemis = "Arthemis: Move again?";
    public static String Atlas = "Atlas: Block or Dome?";
    public static String Demeter = "Demeter: Build again?";
    public static String Hephaestus = "Hephaestus: Another Block?";
    public static String Prometheus = "Prometheus: Move or Build";
    public static String Hestia = "Hestia: Build again?";
    public static String Poseidon = "Poseidon: Unmove worker Build?";
    public static String Triton = "Triton: Move again?";
*/

    //Generics


    //Nickname
    public static String nicknameRequest = "Welcome! What is your nickname?";
    public static String nicknameInUse = "Nickname in use, choose another one";
    public static String nicknameAvailable = "You can use this nickname!";


    //Lobby
    public static String canCreateLobby = "You can create a lobby\nHow many players can join this match? (2-3)";
    public static String matchStarting = "Match Starting\nPlayers in game are: ";

    //Godlist
    public static String invalidChoice = "Scelta invalida";
    public static String challengerChosen = "Il Challenger ha finito di scegliere i God! \nLa Lista dei God scelti è :";

    //Match
    public static String chooseStartPlayer = "Choose the first player!";
    public static String boardStarting = "Enjoy :D";
    public static String workerChose = "Worker chosen";
    public static String endTurn = "Il tuo turno è terminato!";


}
