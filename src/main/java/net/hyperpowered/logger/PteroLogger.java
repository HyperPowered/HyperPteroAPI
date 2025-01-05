package net.hyperpowered.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class PteroLogger extends Logger {

    public PteroLogger(String name) {
        super(name, null);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new PteroLoggerFormatter(this));
        this.addHandler(handler);
        setUseParentHandlers(false);
    }

}
