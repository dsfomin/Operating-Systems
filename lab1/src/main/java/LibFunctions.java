
import spos.lab1.demo.IntOps;

public class LibFunctions implements Functions {

    public Integer functionF(int x) {
        try {
            return IntOps.funcF(x - 1);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Integer functionG(int x) {
        try {
            return IntOps.funcG(x - 1);
        }
        catch (Exception e) {
            return null;
        }
    }
}