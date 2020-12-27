import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyListener {
    private static GlobalKeyboardHook keyboardHook;

    KeyListener() {

    }

    public static void run() {
        if (keyboardHook == null)
            keyboardHook = new GlobalKeyboardHook(true);

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                // Close program manager
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_C && event.isControlPressed()) {
                    System.out.println("CTRL + C pressed - program was stopped");
                    System.out.print((Main.fIsComputed ? "" : "f wasn't computed\n") + (Main.gIsComputed ? "" : "g wasn't computed\n"));
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
            }
        });
    }
}
