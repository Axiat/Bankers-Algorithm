import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nate on 9/29/16.
 */
public class Banker {

    ConcurrentHashMap<String, Claim>  allocatedResources = new ConcurrentHashMap<>();

    int nUnits;
    public Banker(int nUnits){
        this.nUnits = nUnits;
    }

    /**
     * The current  thread (available via static method Thread. currentThread())
     * attempts to register a remainingClaim for up to nUnits units of resource.
     * @param claim
     */
    public synchronized void setClaim(int claim){
        /*System.exits if a remainingClaim is already registered with that thread,
        *  or the remainingClaim is not positive or if the remainingClaim is greater than
        *  the available resources*/
        if(claim < 0 || claim > nUnits || allocatedResources.contains(Thread.currentThread().getName())){
            System.exit(1);
        }

        nUnits -= claim; // subtract remainingClaim from available resources
        String currentName = Thread.currentThread().getName();
        allocatedResources.put(currentName,new Claim(claim,0));
        System.out.println("Thread " + currentName + " sets a remainingClaim for: " + claim + " nUnits" );

    }

    /**
     * The current thread requests nUnits more resources.
     * @param nUnits
     * @return
     */
    public synchronized boolean request(int nUnits){
        String currentName = Thread.currentThread().getName();
        if( !(nUnits > 0|| nUnits < allocatedResources.get(currentName).getRemainingClaim() || allocatedResources.contains(currentName)    )){
            System.exit(1);
        }

        System.out.println("Thread " + currentName + " requests " + nUnits + " units" );

        // check if allocation is a safe state




        return false; // TODO: remove later
    }

    /**
     * The current thread releases nUnits resources.
     * @param nUnits
     */
    public void release(int nUnits){

    }

    /**
     * Returns the number of units allocated to the current thread.
     * @return
     */
    public int allocated(){
        return allocatedResources.get(Thread.currentThread().getName()).getCurrentAllocation();
    }

    /**
     * Returns the number of units remaining in the current thread's remainingClaim.
     * @return
     */
    public int remaining(){
        return allocatedResources.get(Thread.currentThread().getName()).getRemainingClaim();

    }

}



/**
 * Created by nate on 10/3/16.
 * This class is used by the banker to keep track of a threads current remainingClaim
 * and remaining remainingClaim.
 */
class Claim {
    private final int currentAllocation;
    private final int remainingClaim;

    public Claim( int claim, int currentAllocation){
        this.currentAllocation = currentAllocation;
        this.remainingClaim = claim;
    }

    public int getCurrentAllocation() {
        return currentAllocation;
    }

    public int getRemainingClaim() {
        return remainingClaim;
    }
}
