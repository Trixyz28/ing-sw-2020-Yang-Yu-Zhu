package it.polimi.ingsw.server;

import it.polimi.ingsw.Observer;


public interface Connection {

    void closeConnection();

    void addObservers(Observer<String> observer);

    void asyncSend(Object message);

}
