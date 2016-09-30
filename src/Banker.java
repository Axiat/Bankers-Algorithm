/**
 * Created by nate on 9/29/16.
 */
public class Banker {

    int nUnits;
    public Banker(int nUnits){
        this.nUnits = nUnits;
    }

    /**
     * The current  thread (available via static method Thread. currentThread())
     * attempts to register a claim for up to nUnits units of resource.
     * @param nUnits
     */
    public void setClaim(int nUnits){

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
        return 0;
    }

}
