import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.Scanner;

// 1 - fx before gx => result fx => result gx => operation fx * gx
// 2 - gx before fx => result gx => result fx => operation fx * gx
// 3 - fx = 0, g hangs => info that fx = 0 => operation 0
// 4 - gx = 0, f hangs => info that gx = 0 => operation 0
// 5 - fx != 0, g hangs => result fx => waiting
// 6 - gx != 0, f hangs => result gx => waiting

public class Main {

    public static boolean fIsComputed = false;
    public static boolean gIsComputed = false;
    private static final Reference<Integer> fResult = new Reference<>();
    private static final Reference<Integer> gResult = new Reference<>();

    static int x = 0;

    public static void main(String[] args) {
        Functions functions = new LibFunctions();
        KeyListener.run();
        Scanner in = new Scanner(System.in);
        System.out.println("== CTRL+C to stop program ==");

        while (true) {
            fIsComputed = false;
            gIsComputed = false;

            try {
                System.out.print("x = ");
                x = in.nextInt();
                Pipe fPipe = createNonBlockingPipe();
                Pipe gPipe = createNonBlockingPipe();

                Thread fThread = new ThreadManager(functions::functionF, x, fPipe.sink());
                Thread gThread = new ThreadManager(functions::functionG, x, gPipe.sink());

                fThread.start();
                gThread.start();

                while (!fIsComputed || !gIsComputed) {
                    if (!fIsComputed) {
                        fIsComputed = printIfComputed(fPipe, fResult, "f");
                        if (fIsComputed && checkIfZero(fResult.getValue())) {
                            break;
                        }
                    }

                    if (!gIsComputed) {
                        gIsComputed = printIfComputed(gPipe, gResult, "g");
                        if (gIsComputed && checkIfZero(gResult.getValue())) {
                            break;
                        }
                    }
                }

                fThread.interrupt();
                gThread.interrupt();

                Integer binaryOperationResult = binaryOperation(fResult.getValue(), gResult.getValue());
                System.out.printf("Binary operation result is %s\n", binaryOperationResult);
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int inputX() {
        System.out.print("x = ");
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        }
    }

    public static Pipe createNonBlockingPipe() throws IOException {
        Pipe pipe = Pipe.open();
        pipe.source().configureBlocking(false);
        return pipe;
    }

    public static boolean printIfComputed(Pipe pipe, Reference<Integer> result, String computationName) throws IOException {
        if (checkIfComputed(pipe, result)) {
            Integer resultValue = result.getValue();
            System.out.printf("%s has been computed. Result is %s\n",
                    computationName, resultValue != null ? resultValue : "undefined");
            return true;
        }
        return false;
    }

    public static boolean checkIfComputed(Pipe pipe, Reference<Integer> result) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        if (pipe.source().read(buffer) > 0) {
            result.setValue(buffer.flip().remaining() == 4 ? buffer.getInt() : null);
            return true;
        }
        return false;
    }

    public static boolean checkIfZero(Integer value) {
        return value != null && value == 0;
    }

    public static Integer binaryOperation(Integer fResult, Integer gResult) {
        if (checkIfZero(fResult) || checkIfZero(gResult)) {
            return 0;
        }

        if (fResult == null || gResult == null) {
            return null;
        }

        return fResult * gResult;
    }
}
