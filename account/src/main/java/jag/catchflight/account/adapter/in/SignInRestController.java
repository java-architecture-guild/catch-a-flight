// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.adapter.in;

import jag.catchflight.account.port.in.SignInUseCase;
import jag.catchflight.common.annotations.hexagonal.InboundAdapter;
import jag.catchflight.sharedkernel.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static jag.catchflight.account.port.in.SignInUseCase.SignInCommand;
import static jag.catchflight.account.port.in.SignInUseCase.SignInResult.AuthenticationFailure;
import static jag.catchflight.account.port.in.SignInUseCase.SignInResult.InternalFailure;
import static jag.catchflight.common.controller.ResponseBodyHelper.badRequestBody;
import static jag.catchflight.common.controller.ResponseBodyHelper.internalServerErrorBody;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/// REST controller for handling user sign-in requests.
///
/// This class acts as an inbound adapter, receiving HTTP POST requests for user authentication.
/// It uses the [SignInUseCase] to execute the sign-in logic and the [SignInMapper]
/// to convert the API request DTO to an internal command object.
@Slf4j
@InboundAdapter
@RestController
@RequiredArgsConstructor
class SignInRestController {
    private static final String INCORRECT_CREDENTIALS = "Incorrect user and/or password.";

    private final SignInUseCase signInUseCase;
    private final SignInMapper signInMapper;
    private final HttpServletRequest servletRequest;

    /// Handles the user sign-in process.
    ///
    /// This endpoint accepts a JSON body with a user's email and password, attempts to authenticate them,
    /// and returns the appropriate HTTP response based on the outcome.
    ///
    /// @param request The request body containing the user's email and password.
    /// @return A [ResponseEntity] representing the result of the sign-in attempt.
    ///
    ///   - `201 Created`: If authentication is successful, returning the user's ID.
    ///   - `400 Bad Request`: If authentication fails due to incorrect credentials.
    ///   - `500 Internal Server Error`: If an unexpected error occurs during the process.
    @PostMapping
    ResponseEntity<?> signInUser(@Validated @RequestBody SignInRequest request) {
        log.info("Request: {}", request);
        var signInCommand = signInMapper.toCommand(request);
        var signInResult = signInUseCase.signIn(signInCommand);
        return switch (signInResult) {
            case SignInUseCase.SignInResult.Success(UserId userId) -> successBody(userId);
            case AuthenticationFailure _ -> badRequestBody(servletRequest, INCORRECT_CREDENTIALS);
            case InternalFailure(Throwable cause) -> internalServerErrorBody(servletRequest, cause);
        };
    }

    /// Represents the request body for user sign-in.
    ///
    /// @param email    The user's email address.
    /// @param password The user's password.
    record SignInRequest(String email, String password) {}

    /// The sealed interface for all possible responses from the sign-in endpoint.
    interface SignInResponse {
        record SuccessResponse(UserId userId) implements SignInResponse {}
    }

    /// Creates a `201 Created` [ResponseEntity] with the user's ID in the body.
    ///
    /// @param userId The ID of the authenticated user.
    /// @return A [ResponseEntity] for a successful sign-in.
    private static ResponseEntity<SignInResponse> successBody(UserId userId) {
        return status(HttpStatus.CREATED).body(new SignInResponse.SuccessResponse(userId));
    }

    /// Maps [SignInRequest] objects to [SignInCommand] objects.
    private static class SignInMapper {
        SignInCommand toCommand(SignInRequest request) {
            return new SignInCommand();
        }
    }
}
