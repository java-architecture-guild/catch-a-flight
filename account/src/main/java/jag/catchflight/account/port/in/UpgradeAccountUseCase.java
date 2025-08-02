package jag.catchflight.account.port.in;

import jag.catchflight.common.annotations.hexagonal.InboundPort;
import jag.catchflight.sharedkernel.user.UserId;

import java.util.Objects;

@InboundPort
public interface UpgradeAccountUseCase {
    UpgradeUserResult upgradeUser(UpgradeUserCommand command);

    record UpgradeUserCommand(UserId userId) {
        public UpgradeUserCommand {
            Objects.requireNonNull(userId);
        }
    }

    public sealed interface UpgradeUserResult {
        record Success() implements UpgradeUserResult {}
        record UserNotFoundFailure(String message) implements UpgradeUserResult {}
        record UserAlreadyUpgradedFailure(String message) implements UpgradeUserResult {}
        record InternalFailure(Throwable cause) implements UpgradeUserResult {}
    }
}
