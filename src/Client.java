/**
 * Created by nate on 9/29/16.
 */
public class  Client extends Thread{

    Banker banker;
    int nUnits;
    int nRequests;
    long minSleepMillis;
    long maxSleepMillis;

    public Client(String name, Banker banker, int nUnits,
                  int nRequests, long minSleepMillis, long maxSleepMillis){

        super(name);
        this.banker = banker;
        this.nUnits = nUnits;
        this.nRequests = nRequests;
        this.minSleepMillis = minSleepMillis;
        this.maxSleepMillis = maxSleepMillis;

    }

    /**
     * Register a claim for up to nUnits of resource with the banker.
     * Then create a loop that will be executed nRequests times;
     * each iteration will either request or release resources by
     * invoking methods in the banker.
     */
    @Override
    public void run() {

        banker.setClaim(nUnits);

        for(int i=0; i< nRequests; i++){


            if(banker.remaining() == 0){
                banker.release(banker.remaining()); // release all units
            }
            else{
                // TODO: request some units
            }

            try {
                int rand = (int)(Math.round(Math.random()*maxSleepMillis) + minSleepMillis);
                Thread.sleep(rand);
            } catch (InterruptedException e) {  e.printStackTrace();  }
        }

        banker.release(banker.remaining()); // release all the remaining resources
    }
}
