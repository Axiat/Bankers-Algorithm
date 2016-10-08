/**
 * Created by nate on 9/29/16.
 */
class  Client extends Thread{

    private Banker banker;
    private int nUnits;
    private int nRequests;
    private long minSleepMillis;
    private long maxSleepMillis;
    private String name;

    public Client(String name, Banker banker, int nUnits,
                  int nRequests, long minSleepMillis, long maxSleepMillis){

        super(name);
        this.name = name;
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
                if(banker.allocated() == 0){
                    //System.out.println("Thread " + name + " is done"); // for verification purposes
                    break; // our end case
                }
                banker.release(banker.allocated()); // release all units
            }
            else{
                banker.request((banker.remaining()-banker.allocated()));
            }

            try {
                int rand = (int)(Math.round(Math.random()*maxSleepMillis) + minSleepMillis);
                Thread.sleep(rand);
            } catch (InterruptedException e) {  e.printStackTrace();  }

        }
    }
}
