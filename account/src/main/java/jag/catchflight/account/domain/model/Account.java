package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainAggregate;
import jag.catchflight.common.persistence.Version;
import jag.catchflight.sharedkernel.user.AccountType;
import jag.catchflight.sharedkernel.user.Email;
import jag.catchflight.sharedkernel.user.UserId;
import lombok.Builder;
import lombok.Getter;

import lombok.Builder;
import lombok.Getter;

/// Represents an account aggregate in the domain model, encapsulating user-related data and operations.
@Getter
@Builder
@DomainAggregate
public class Account {
    private UserId userId;
    private Email email;
    private Password password;
    private UserName userName;
    private AccountType accountType;
    private Version version;

    /// Upgrades the user's account type to PREMIUM if the current account type is REGULAR.
    ///
    /// @return an [UpgradeUserResult] indicating the result of the upgrade attempt
    public UpgradeUserResult upgradeUser() {
        return switch (accountType) {
            case PREMIUM -> new UpgradeUserResult.AlreadyUpdatedFailure(userId, "Premium user can't be upgraded");
            case REGULAR -> {
                accountType = AccountType.PREMIUM;
                yield new UpgradeUserResult.Success();
            }
        };
    }

    /// Sealed interface defining the possible results of an account upgrade operation.
    public sealed interface UpgradeUserResult {
        record Success() implements UpgradeUserResult {}
        record AlreadyUpdatedFailure(UserId userId, String message) implements UpgradeUserResult {}
    }
}
