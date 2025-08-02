package jag.catchflight.account.adapter.in;

import jag.catchflight.account.port.in.UpgradeAccountUseCase;
import jag.catchflight.common.annotations.hexagonal.InboundAdapter;
import jag.catchflight.sharedkernel.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static jag.catchflight.account.port.in.UpgradeAccountUseCase.UpgradeUserCommand;
import static jag.catchflight.account.port.in.UpgradeAccountUseCase.UpgradeUserResult.*;

/// REST controller for handling account upgrade requests.
/// This controller processes HTTP requests to upgrade a user account and returns appropriate responses based on the outcome.
@Slf4j
@RestController
@InboundAdapter
@RequiredArgsConstructor
class UpgradeAccountRestController {

    private final UpgradeAccountUseCase upgradeAccountUseCase;
    private final UpgradeUserMapper upgradeUserMapper;
    private final HttpServletRequest servletRequest;

    /// Handles HTTP POST requests to upgrade a user account.
    ///
    /// @param accountId the ID of the account to be upgraded
    /// @return a [ResponseEntity] containing the result of the upgrade operation
    ///         - HTTP 201 Created for successful upgrades
    ///         - HTTP 400 Bad Request for user not found or already upgraded failures
    ///         - HTTP 500 Internal Server Error for unexpected errors
    @PostMapping
    ResponseEntity<?> upgrade(@PathVariable("accountId") String accountId) {
        log.info("Request, userId: {}", accountId);
        var upgradeUserCommand = upgradeUserMapper.toCommand(accountId);
        var upgradeUserResult = upgradeAccountUseCase.upgradeUser(upgradeUserCommand);
        return switch (upgradeUserResult) {
            case Success() -> successBody();
            case UserNotFoundFailure(String message) -> badRequestBody(servletRequest, message);
            case UserAlreadyUpgradedFailure(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerErrorBody(servletRequest, cause);
        };
    }

    /// Creates a successful response for a completed account upgrade.
    private static ResponseEntity<UpgradeUserResponse> successBody() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /// Builds a bad request response for client-side errors (e.g., user not found or already upgraded).
    private ResponseEntity<?> badRequestBody(HttpServletRequest request, String message) {
        return ResponseEntity.badRequest().body(message);
    }

    /// Builds an internal server error response for unexpected server-side errors.
    private ResponseEntity<?> internalServerErrorBody(HttpServletRequest request, Throwable cause) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cause.getMessage());
    }

    /// Interface defining the structure of upgrade user responses.
    private interface UpgradeUserResponse {
        record SuccessResponse() implements UpgradeUserResponse {}
    }

    /// Mapper class to convert request data into a command for upgrading a user account.
    private static class UpgradeUserMapper {
        UpgradeUserCommand toCommand(String userId) {
            return new UpgradeUserCommand(new UserId(UUID.fromString(userId)));
        }
    }
}
