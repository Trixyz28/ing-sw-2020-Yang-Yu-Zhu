package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;

import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Tile;

import java.io.PrintStream;
import java.util.Scanner;


public class View extends Observable implements Observer, Runnable {

    private Scanner scanner;
    private PrintStream outputStream;
    private boolean endGame = false;
    private int currentID;


    public View(){
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
    }


    @Override
    public void update(Object message) {
        if(message instanceof String[]){
            showComplete((String[])message);
        }
    }

    @Override
    public void run() {
        while(!endGame) {

            outputStream.println("Player " + currentID + "'s turn");


            chooseWorker();
        }
    }

    private void chooseWorker() {
        outputStream.println("Which worker do you want to choose?");


    }

    //printare GodList completo
    private void showComplete(String[] completeList){
        outputStream.println("Le divinità che puoi scegliere sono:");
        for(String s : completeList){
            outputStream.println(s);
        }
    }



    private void operation() {

        while(true) {
            outputStream.println("Where do you want to move?");
            String str = scanner.next();

            try {
                String[] inputs = str.split(",");
                move(Integer.parseInt(inputs[0]),Integer.parseInt(inputs[1]));
                break;

            } catch (NumberFormatException e) {
                outputStream.println("Command error!");
            }

        }
    }

    private void move(int row, int col) {
        setChanged();
        notify();
    }

    private void build() {

    }



    private void print(Map map) {
        Tile[][] tiles = map.getMap();
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){

                if(tiles[i][j].isDomePresence()) {
                    outputStream.print("D ");
                }
                else if (tiles[i][j].isOccupiedByWorker()) {
                    outputStream.print("W ");
                }
                else {
                    outputStream.print(tiles[i][j].getBlockLevel() + " ");
                }
            }
            outputStream.println();
        }
        outputStream.println();
    }
}
