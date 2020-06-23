package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;

public class Sender {

    private Client client;

    public Sender(Client client) {
        this.client = client;
    }

    public void sendMessage(String str) {
        client.sendInput(str);
    }

}
