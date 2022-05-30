package org.openhab.binding.arcam.internal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcamCommandFinder {
    private final Logger logger = LoggerFactory.getLogger(ArcamCommandFinder.class);

    public byte[] getCommandFromCode(ArcamCommandCode code, List<ArcamCommand> list) {
        for (ArcamCommand command : list) {
            if (command.code.equals(code)) {
                return command.data;
            }
        }

        logger.warn("Could not find ArcamCommandfor code: {}", code);
        return null;
    }

}
