import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class ThreadF extends Thread {
    public Integer x;
    public WritableByteChannel sinkChannel;

    private volatile boolean running = true;

    public ThreadF(WritableByteChannel sinkChannel, Integer x) {
        this.x = x;
        this.sinkChannel = sinkChannel;
    }

    @Override
    public void interrupt() {
        this.running = false;
    }

    public Integer F(Integer x) throws InterruptedException {
        if (x == 1) {
            return 1;
        } else if (x == 2) {
            this.sleep(2000);
            return 2;
        } else if (x == 3) {
            return 0;
        } else if (x == 4) {
            this.sleep(1000000);
            return 4;
        } else if (x == 5) {
            return 5;
        } else if (x == 6) {
            this.sleep(1000000);
            return 6;
        }
        return 0;
    }

    @Override
    public void run() {
        while (running) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(4);
                Integer fx = F(x);
                buffer.putInt(fx);
                buffer.flip( );
                while (buffer.hasRemaining( )) sinkChannel.write(buffer);
            } catch (InterruptedException | IOException e) {
                running = false;
                System.err.println(e);
            }
        }

    }

}
