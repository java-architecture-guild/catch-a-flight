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

/// REST controller for creating new user accounts.
/// This class handles HTTP POST requests to the account creation endpoint.
/// It uses a [CreateAccountUseCase] to orchestrate the creation of an account
/// and maps the request and response objects using a [CreateAccountMapper].
@Slf4j
@InboundAdapter
@RestController
@RequiredArgsConstructor
class CreateAccountRestController {
    private final CreateAccountUseCase createAccountUseCase;
    private final CreateAccountMapper createAccountMapper;
    private final HttpServletRequest servletRequest;

    /// Handles the creation of a new user account.
    @PostMapping
    ResponseEntity<?> createUser(@Validated @RequestBody CreateAccountRequest request) {
        log.info("Request: {}", request);
        var createUserCommand = createAccountMapper.toCommand(request);
        var createUserResult = createAccountUseCase.createUser(createUserCommand);
        return switch (createUserResult) {
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

    /// Maps between the [CreateAccountRequest] DTO and
    /// the domain-specific [CreateAccountUseCase.CreateAccountCommand].
    private static class CreateAccountMapper {
        CreateAccountUseCase.CreateAccountCommand toCommand(CreateAccountRequest request) {
            return new CreateAccountUseCase.CreateAccountCommand(
                    new Email(request.email()),
                    new Password(request.password()),
                    new UserName(request.firstName(), request.lastName()));
        }
    }
}
