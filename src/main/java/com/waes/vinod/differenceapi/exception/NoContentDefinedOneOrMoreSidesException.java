package com.waes.vinod.differenceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the data from one of the sides is empty.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The given identifier does not have data on one of their side.")
public class NoContentDefinedOneOrMoreSidesException extends Exception {
}
