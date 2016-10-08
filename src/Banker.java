
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by nate on 9/29/16.
 */
class Banker {
    private int nUnits;
    private HashMap<String, Claim> allocatedResources = new HashMap<>();

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
        if(claim < 0 || claim > nUnits || allocatedResources.containsKey(Thread.currentThread().getName())){
            System.exit(1);

        }

        String currentName = Thread.currentThread().getName();
        allocatedResources.put(currentName,new Claim(claim,0));
        System.out.println("Thread " + currentName + " sets a Claim for: " + claim + " nUnits" );

    }

    /**
     * The current thread requests nUnits more resources.
     * @param nUnits
     * @return
     */
    public synchronized boolean request(int nUnits){
        String currentName = Thread.currentThread().getName();
        if( !(nUnits > 0|| nUnits < allocatedResources.get(currentName).getRemainingClaim() || allocatedResources.containsKey(currentName)    )){
            System.exit(1);
        }

        System.out.println("Thread " + currentName + " requests " + nUnits + " units" );

        /*
         * This part of the code checks whether or not we would be entering a bad state
         */
        boolean isSafe = isSafeState();

        if(!isSafe){
                System.out.println("Thread " + currentName + " waits");

                while(!isSafeState()){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            System.out.println("Thread " + currentName + " awakened");
        }

        /* If we make it here, that means our system is in a safe state*/


        Claim old = allocatedResources.get(currentName);
        Claim updatedClaim = new Claim(old.getRemainingClaim()-nUnits,old.getCurrentAllocation() + nUnits);
        allocatedResources.put(currentName, updatedClaim);
        System.out.println("Thread " + currentName + " has " + allocatedResources.get(currentName).getCurrentAllocation() + " units allocated" );
        return isSafe;
    }

    /**
     * This method determines whether we are in a safe state to allocate resources
     * @return
     */
    private synchronized boolean isSafeState(){
        // Arraylist of all the claims in our map of map<Thread name, claim object>
        ArrayList<Claim> allClaims = new ArrayList<>(allocatedResources.values());
        Collections.sort(allClaims);


        /*
         * This part of the code checks whether or not we would be entering a bad state
         */
        int totalAllocated = 0;
        boolean isSafeState = true;
        for(Claim claim : allClaims){
            if(claim.getRemainingClaim() + totalAllocated > this.nUnits){
                isSafeState = false;
                break;
            }
            totalAllocated += claim.getCurrentAllocation();
        }

        return isSafeState;
    }

    /**
     * The current thread releases nUnits resources.
     * @param nUnits
     */
    public synchronized void release(int nUnits){
        String currName = Thread.currentThread().getName();
        if(!allocatedResources.containsKey(currName) || nUnits < 0
                || nUnits > allocatedResources.get(currName).getCurrentAllocation()){
            System.exit(1);
        }

        System.out.println("Thread " + currName + " releases: " + nUnits + " units");
        Claim oldClaim = allocatedResources.get(currName);
        allocatedResources.put(currName, new Claim(oldClaim.getRemainingClaim(),oldClaim.getCurrentAllocation()-nUnits));
        notifyAll();
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
class Claim implements Comparable {
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

    /**
     * we implement the comparable interface se we can use java's built in sort
     * functions
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        Claim other = (Claim)o; // casts object to claim
        if(this.getRemainingClaim() < other.getRemainingClaim()){
            return -1;
        }
        if(this.getRemainingClaim() > other.getRemainingClaim()){
            return 1;
        }
        else{
            return 0;
        }
    }

}
