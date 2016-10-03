
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
     * attempts to register a claim for up to nUnits units of resource.
     * @param claim
     */
    public void setClaim(int claim){
        /*System.exits if a claim is already registered with that thread,
        *  or the claim is not positive or if the claim is greater than
        *  the available resources*/
        if(claim < 0 || claim > nUnits || allocatedResources.contains(Thread.currentThread().getName())){
            System.exit(1);
        }
        else{
            String currentName = Thread.currentThread().getName();
            allocatedResources.put(currentName,new Claim(claim,0));
        }
    }

    /**
     * The current thread requests nUnits more resources.
     * @param nUnits
     * @return
     */
    public boolean request(int nUnits){
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
        return 0; // TODO: remove later
    }

    /**
     * Returns the number of units remaining in the current thread's claim.
     * @return
     */
    public int remaining(){
        return nUnits;
    }

}



/**
 * Created by nate on 10/3/16.
 * This class is used by the banker to keep track of a threads current claim
 * and remaining claim.
 */
class Claim {
    private final int currentAllocation;
    private final int claim;

    public Claim( int claim, int currentAllocation){
        this.currentAllocation = currentAllocation;
        this.claim = claim;
    }


    public int getCurrentAllocation() {
        return currentAllocation;
    }

    public int getClaim() {
        return claim;
    }
}
