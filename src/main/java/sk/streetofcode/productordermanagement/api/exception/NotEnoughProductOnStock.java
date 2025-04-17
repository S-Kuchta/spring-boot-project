package sk.streetofcode.productordermanagement.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughProductOnStock extends RuntimeException {

    public NotEnoughProductOnStock(String message) {
        super(message);
    }
}
