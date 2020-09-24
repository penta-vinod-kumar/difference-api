package com.waes.vinod.differenceapi.model;

/**
 * Enum that defines the possible responses.
 */
public enum DifferenceType {
    /**
     * Represents both sides of the diff document are equal.
     */
    EQUALS,

    /**
     * Represents both sides of the diff document are different size.
     */
    DIFFERENT_SIZE,

    /**
     * Represents both sides of the diff document are different of content.
     */
    DIFFERENT_CONTENT
}
