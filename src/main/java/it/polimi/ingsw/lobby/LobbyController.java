package it.polimi.ingsw.lobby;

import it.polimi.ingsw.Observer;


public class LobbyController implements Observer {

    private Lobbies lobbies;
    private LobbyView lobbyView;

    public LobbyController(Lobbies lobbies, LobbyView lobbyView) {
        this.lobbies = lobbies;
        this.lobbyView = lobbyView;
    }


    @Override
    public void update(Object message) {
        if (message.equals("setup")) {
            System.out.println("Hi, first player!");
            String name = setNickname();
            intoLobby(name);

            for(int i=2;i<lobbies.getLobbyList().get(0).getLobbyPlayersNumber()+1;i++) {
                System.out.println("Player " + i + " ");
                name = setNickname();
                intoLobby(name);
            }

        }
    }

    public String setNickname() {

        boolean flag;
        String s;

        do {
            flag = true;
            s = lobbyView.readNickname();

            for (String name : lobbies.getPlayerList()) {
                if (s.equals(name)) {
                    System.out.println("Nickname in use, choose another one");
                    flag = false;
                }
            }
        } while(!flag);

        lobbies.addPlayer(s);

        return s;
    }


    public void intoLobby(String name) {

        if(!lobbies.checkAvailableLobby()){
            int num;

            num = lobbyView.readPlayerNumber();
            lobbies.createLobby(num);
        } else {
            lobbies.joinLobby(name);
        }
    }


    //on server runs a method that checks if a lobby is full or not and then starts the game on the current lobby







}