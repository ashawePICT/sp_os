package process_wireframe;
import java.util.Comparator;

public class SortByPriority implements Comparator<Process> {
    // Used for sorting in ascending order
    public int compare(Process a, Process b) 
    { 
        return (int)a.get_priority() - (int)b.get_priority(); 
    } 
} 