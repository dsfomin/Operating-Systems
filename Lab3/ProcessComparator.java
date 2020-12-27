import java.util.Comparator;

public class ProcessComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        sProcess process1 = (sProcess) o1;
        sProcess process2 = (sProcess) o2;
        return process1.ioblocking - process2.ioblocking;
    }
}
