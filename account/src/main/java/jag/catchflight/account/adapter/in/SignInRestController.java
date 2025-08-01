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

import static jag.catchflight.account.adapter.in.SignInRestController.SignInResponse.SuccessResponse;
import static jag.catchflight.account.port.in.SignInUseCase.SignInCommand;
import static jag.catchflight.account.port.in.SignInUseCase.SignInResult.*;
import static jag.catchflight.common.controller.ResponseBodyHelper.badRequestBody;
import static jag.catchflight.common.controller.ResponseBodyHelper.internalServerErrorBody;

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
    private final SignInUseCase signInUseCase;
    private final SignInMapper signInMapper;
    private final HttpServletRequest servletRequest;

    /// Handles HTTP POST requests to authenticate and sign in a user.
    ///
    /// This endpoint processes the sign-in request, validates the provided credentials,
    /// and delegates the authentication logic to the [SignInUseCase]. Based on
    /// the result, it returns an appropriate HTTP response.
    ///
    /// @param request the [SignInRequest] containing the user's sign-in credentials
    ///                                                             (e.g., username and password). Must not be null and must be valid.
    /// @return a [ResponseEntity] containing the response body and HTTP status code:
    ///
    ///     - Success: Returns a success response with the authenticated [UserId].
    ///     - Authentication Failure: Returns a 400 Bad Request response with an error message.
    ///     - Internal Failure: Returns a 500 Internal Server Error response with error details.
    @PostMapping
    ResponseEntity<?> signInUser(@Validated @RequestBody SignInRequest request) {
        log.info("Request: {}", request);
        var signInCommand = signInMapper.toCommand(request);
        var signInResult = signInUseCase.signIn(signInCommand);
        return switch (signInResult) {
            case Success(UserId userId) -> successBody(userId);
            case AuthenticationFailure _ -> badRequestBody(servletRequest, "Incorrect user and/or password.");
            case InternalFailure(Throwable cause) -> internalServerErrorBody(servletRequest, cause);
        };
    }

    /// Represents the request body for user sign-in.
    record SignInRequest(String email, String password) {}

    /// Creates a `201 Created` [ResponseEntity] with the user's ID in the body.
    static ResponseEntity<SignInResponse> successBody(UserId userId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse(userId));
    }

    /// Sucessful sign-in operation response.
    interface SignInResponse {
        record SuccessResponse(UserId userId) implements SignInResponse {}
    }

    /// Maps [SignInRequest] objects to [SignInCommand] objects.
    private static class SignInMapper {
        SignInCommand toCommand(SignInRequest request) {
            return new SignInCommand();
        }
    }
}
