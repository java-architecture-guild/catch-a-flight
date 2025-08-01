package jag.catchflight.account.adapter.in;

import jag.catchflight.account.domain.model.Password;
import jag.catchflight.account.domain.model.UserName;
import jag.catchflight.account.port.in.CreateAccountUseCase;
import jag.catchflight.common.annotations.hexagonal.InboundAdapter;
import jag.catchflight.sharedkernel.user.Email;
import jag.catchflight.sharedkernel.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static jag.catchflight.account.port.in.CreateAccountUseCase.CreateAccountResult.*;
import static jag.catchflight.common.controller.ResponseBodyHelper.badRequestBody;
import static jag.catchflight.common.controller.ResponseBodyHelper.internalServerErrorBody;
import static org.springframework.http.ResponseEntity.status;

/// REST controller for handling account creation requests.
/// This controller processes HTTP requests to create new user accounts
/// and delegates the business logic to the [CreateAccountUseCase].
@Slf4j
@InboundAdapter
@RestController
@RequiredArgsConstructor
class CreateAccountRestController {
    private final CreateAccountUseCase createAccountUseCase;
    private final CreateAccountMapper createAccountMapper;
    private final HttpServletRequest servletRequest;

    /// Handles the user account creation process.
    ///
    /// This endpoint accepts a JSON body with user account details, attempts to create a new user account,
    /// and returns the appropriate HTTP response based on the outcome.
    ///
    /// @param request The request body containing the user account details.
    /// @return A [ResponseEntity] representing the result of the account creation attempt.
    ///
    ///     - `201 Created`: If account creation is successful, returning the user's ID.
    ///     - `400 Bad Request`: If account creation fails due to an existing account or password policy violation.
    ///     - `500 Internal Server Error`: If an unexpected error occurs during the process.
    @PostMapping
    ResponseEntity<?> createUser(@Validated @RequestBody CreateAccountRequest request) {
        log.info("Request: {}", request);
        var command = createAccountMapper.toCommand(request);
        var result = createAccountUseCase.createUser(command);
        return switch (result) {
            case Success(UserId userId) -> successBody(userId);
            case ExistingAccountFailure(String message) -> badRequestBody(servletRequest, message);
            case PasswordPolicyFailure(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerErrorBody(servletRequest, cause);
        };
    }

    /// Represents the data transfer object for creating a new user account.
    record CreateAccountRequest(
            @NotNull String email,
            @NotNull String password,
            @NotNull String firstName,
            @NotNull String lastName) {}

    /// Base interface for all possible account creation responses.
    interface CreateAccountResponse {
        record SuccessResponse(UserId userId) implements CreateAccountResponse {}
    }

    /// Creates a `201 Created` [ResponseEntity] with the user's ID in the body.
    private static ResponseEntity<CreateAccountResponse> successBody(UserId userId) {
        return status(HttpStatus.CREATED).body(new CreateAccountResponse.SuccessResponse(userId));
    }

    /// Maps between the [CreateAccountRequest] DTO and the domain-specific [CreateAccountUseCase.CreateAccountCommand].
    private static class CreateAccountMapper {
        CreateAccountUseCase.CreateAccountCommand toCommand(CreateAccountRequest request) {
            return new CreateAccountUseCase.CreateAccountCommand(
                    new Email(request.email()),
                    new Password(request.password()),
                    new UserName(request.firstName(), request.lastName()));
        }
    }
}
