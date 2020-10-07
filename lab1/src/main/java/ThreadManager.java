import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ThreadManager extends Thread {
    public Integer x;

    public ThreadManager(Integer x) {
        this.x = x;
    }

    @SneakyThrows
    @Override
    public void run() {
        Pipe pipeF = Pipe.open( );
        WritableByteChannel writableByteChannelF = pipeF.sink();
        ReadableByteChannel readableByteChannelF = pipeF.source();

        Pipe pipeG = Pipe.open( );
        WritableByteChannel writableByteChannelG = pipeG.sink();
        ReadableByteChannel readableByteChannelG = pipeG.source();

        ThreadF threadF = new ThreadF(writableByteChannelF, x);
        ThreadG threadG = new ThreadG(writableByteChannelG, x);

        threadF.start();
        threadG.start();

        ByteBuffer byteBufferF = ByteBuffer.allocate(4);
        ByteBuffer byteBufferG = ByteBuffer.allocate(4);

        Integer fx = -1;
        Integer gx = -1;

        while (fx == -1 || gx == -1) {
            while (byteBufferF.hasRemaining()) readableByteChannelF.read(byteBufferF);
            byteBufferF.flip();
            fx = byteBufferF.getInt();
            byteBufferF.clear();

            while (byteBufferG.hasRemaining()) readableByteChannelG.read(byteBufferG);
            byteBufferG.flip();
            gx = byteBufferG.getInt();
            byteBufferG.clear();

            if (gx == 0 || fx == 0) {
                threadF.interrupt();
                threadG.interrupt();

                if (gx == 0) {
                    System.err.println("gx = 0");
                } else {
                    System.err.println("fx = 0");
                }

                System.out.println("gx = " + gx);
                System.out.println("fx = " + fx);

                break;
            }

            if (gx != 0) {
                System.out.println("G finished");
                System.out.println("gx = " + gx);
                System.out.println("fx = " + fx);
            }
            if (fx != 0) {
                System.out.println("F finished");
                System.out.println("gx = " + gx);
                System.out.println("fx = " + fx);
            }

        }

    }
}
