package it.polimi.ingsw.model;


// da sistemare (rimane una classe con lista che associa ogni tile della mappa con un worker di un player(attributes)
public class Worker {


    private int belongToPlayer;

    private int workerID;

    private String workerType;

    private Tile currentPosition;

    public Worker(int belongToPLayer,int workerID,String workerType,Tile currentPosition){
       this.belongToPlayer = belongToPLayer;
       this.workerID = workerID;
       this.workerType = workerType;
       this.currentPosition = currentPosition;
    }




    //initializing list
    public void initializeWorkerList{

        public ArrayList<Worker> WorkersList = new Arraylist<Worker>()

        for (int i = 0; i < matchPlayersList.size(); i++) {
            for (int j = 0; j < workerList.size(); j++) {
                    list.add(new Worker(match.PlayersList.get(i).getPlayerID,j,match.PlayersList.get(i).getGodCard,match.workerList.get(j)
                            .getWorkerPosition));
            }
        }

    }



    //get() the currentPosition
    public Tile getCurrentPosition(Worker w) {
        return w.currentPosition;
    }

    //method that return the worker in the tile
    public NoGod getTileWorker(Tile t){

        for (int i = 0; i < WorkerList.size(); i++){
            if( worker.getCurrenPosition == t){
                return NoGod;
            }
        }


}
