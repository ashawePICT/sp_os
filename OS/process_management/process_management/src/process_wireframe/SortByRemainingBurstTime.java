package process_wireframe;
import java.util.Comparator;

public class SortByRemainingBurstTime implements Comparator<Process> {
    // Used for sorting in ascending order
    public int compare(Process a, Process b) 
    { 
        return (int)a.get_remaining_time() - (int)b.get_remaining_time(); 
    } 
} 