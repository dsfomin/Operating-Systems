import java.io.IOException;
import java.util.Scanner;

// 1 - f begore g, f != 0 => result
// 2 - g begore f, g != 0 => result
// 3 - fx = 0, g hangs => result
// 4 - gx = 0, f hangs => result
// 3 - fx != 0, g hangs => result
// 4 - gx != 0, f hangs => result

public class Main {
    public static void main (String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Integer x = scanner.nextInt();

        ThreadManager threadManager = new ThreadManager(x);
        threadManager.start();
    }
}

//import java.io.IOException;
//import java.nio.channels.Pipe;
//import java.nio.channels.ReadableByteChannel;
//import java.nio.channels.WritableByteChannel;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) throws InterruptedException, IOException {
//
//        Scanner in = new Scanner(System.in);
//        Integer x = in.nextInt();
//
//        Pipe FtoG = Pipe.open();
//        WritableByteChannel FtoGSinkChannel = FtoG.sink();
//        ReadableByteChannel FtoGSourceChannel = FtoG.source();
//
//        Pipe GtoF = Pipe.open();
//        WritableByteChannel GtoFSinkChannel = GtoF.sink();
//        ReadableByteChannel GtoFSourceChannel = GtoF.source();
//
//        ThreadF threadF = new ThreadF(x, FtoGSinkChannel, GtoFSourceChannel);
//        ThreadG threadG = new ThreadG(x, GtoFSinkChannel, FtoGSourceChannel);
//
//        threadF.start();
//        threadG.start();
//
//        System.out.println(threadF.fx);
//        System.out.println(threadG.gx);
//    }
//}
