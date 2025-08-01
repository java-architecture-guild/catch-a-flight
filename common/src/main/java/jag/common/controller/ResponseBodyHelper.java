// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * A helper class for creating standardized REST controller error responses.
 */
@Slf4j
public final class ResponseBodyHelper {
    // Private constructor to prevent instantiation
    private ResponseBodyHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Creates a standardized 500 Internal Server Error response.
     *
     * @param request The HttpServletRequest associated with the error.
     * @param cause   The Throwable cause of the error.
     * @return A ResponseEntity containing the error details.
     */
    public static ResponseEntity<ErrorResponse> internalServerErrorBody(HttpServletRequest request, Throwable cause) {
        log.error("Unexpected error: ", cause);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(OffsetDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Internal Server Error")
                        .message(cause.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Creates a standardized 400 Bad Request response.
     *
     * @param request The HttpServletRequest associated with the error.
     * @param message The specific error message for the bad request.
     * @return A ResponseEntity containing the error details.
     */
    public static ResponseEntity<ErrorResponse> badRequestBody(HttpServletRequest request, String message) {
        log.debug("Bad request error: ", message);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(OffsetDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Validation Error")
                        .message(message)
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Data Transfer Object (DTO) for standard error responses.
     */
    @Getter
    @Builder
    static final class ErrorResponse {
        private final OffsetDateTime timestamp;
        private final int status;
        private final String error;
        private final String message;
        private final String path;
    }
}