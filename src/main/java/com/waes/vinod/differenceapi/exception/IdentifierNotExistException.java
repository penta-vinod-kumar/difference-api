package com.waes.vinod.differenceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when provided identifier does not exit.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The given identifier does not exist")
public class IdentifierNotExistException extends Exception {
}
