package com.dice.game.utility;
import com.dice.game.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Utility class for ResponseEntity creation.
 */
public interface ResponseUtil {

    /**
     * Wrap the optional into a {@link ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status, or if it's empty, it
     * returns a {@link ResponseEntity} with {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @return response containing {@code maybeResponse} if present or {@link org.springframework.http.HttpStatus#NOT_FOUND}
     */
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    /**
     * Wrap the optional into a {@link ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status with the headers, or if it's
     * empty, throws a {@link NotFoundException} with status {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @param header        headers to be added to the response
     * @return response containing {@code maybeResponse} if present
     * @throws NotFoundException {@code 404 (Not found)} if {@code maybeResponse} is empty
     */
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElseThrow(() -> new NotFoundException());
    }
}