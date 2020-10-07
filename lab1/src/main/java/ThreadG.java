import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class ThreadG extends Thread {
    public Integer x;
    public WritableByteChannel sinkChannel;

    private volatile boolean running = true;

    public ThreadG(WritableByteChannel sinkChannel, Integer x) {
        this.x = x;
        this.sinkChannel = sinkChannel;
    }

    @Override
    public void interrupt() {
        this.running = false;
    }

    public Integer G(Integer x) throws InterruptedException {
        if (x == 1) {
            this.sleep(2000);
            return 1;
        } else if (x == 2) {
            return 2;
        } else if (x == 3) {
            this.sleep(1000000);
            return 3;
        } else if (x == 4) {
            return 0;
        } else if (x == 5) {
            this.sleep(1000000);
            return 5;
        } else if (x == 6) {
            return 6;
        }
        return 0;
    }

    @Override
    public void run() {
        while (running) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(4);
                Integer gx = G(x);
                buffer.putInt(gx);
                buffer.flip( );
                while (buffer.hasRemaining( )) sinkChannel.write(buffer);
            } catch (InterruptedException | IOException e) {
                running = false;
                System.err.println(e);
            }
        }
    }
}
