import java.util.ArrayList;

/**
 * Created by nate on 9/29/16.
 */
class Driver {

    private static final int numClients = 30;
    private static final int bankResources = 30;
    private static final int randnUnitsMax = 10;
    private static final int nRequests =  10;

    private static final int minSleepMillis = 10;
    private static final int maxSleepMillis = 300;


    public static void main(String[] args) {

        Banker banker = new Banker(bankResources);

        ArrayList<Thread> allThreads = new ArrayList<>();

        // creates client objects and adds it to the "allThreads" arraylist.
        for(int i=0;i<numClients;i++){
            int randUnits = (int)Math.round(Math.random()*randnUnitsMax)+1;
            allThreads.add(new Client(i+"",banker,randUnits,nRequests,minSleepMillis,maxSleepMillis));
        }

        allThreads.forEach(thread -> thread.start() ); // starts all threads
    }
}
