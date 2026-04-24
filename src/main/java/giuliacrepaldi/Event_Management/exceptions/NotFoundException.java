package giuliacrepaldi.Event_Management.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("The id " + id + " was not found");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
