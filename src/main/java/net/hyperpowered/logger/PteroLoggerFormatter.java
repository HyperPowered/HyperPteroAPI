package net.hyperpowered.logger;

import lombok.AllArgsConstructor;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@AllArgsConstructor
public class PteroLoggerFormatter extends Formatter {

    private Logger logger;

    @Override
    public String format(LogRecord record) {
        return "[" + this.logger.getName() + "] [" + record.getLevel().getName() + "] " + record.getMessage();
    }

}
