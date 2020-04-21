package it.polimi.ingsw;

public interface Observer<T> {

    void update(T message);

}
