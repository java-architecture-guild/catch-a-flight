// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.adapter.in;

import jag.catchflight.account.domain.events.AccountSubscriptionPaid;
import jag.catchflight.account.port.in.SignInUseCase;
import jag.catchflight.account.port.in.UpgradeAccountUseCase;
import jag.catchflight.common.annotations.hexagonal.InboundAdapter;
import jag.catchflight.sharedkernel.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static jag.catchflight.account.port.in.UpgradeAccountUseCase.UpgradeUserCommand;
import static jag.catchflight.account.port.in.UpgradeAccountUseCase.UpgradeUserResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/// Event handler for processing account subscription paid events.
/// This service listens for [AccountSubscriptionPaid] events and triggers the appropriate account upgrade logic.
@Slf4j
@Service
@InboundAdapter
@RequiredArgsConstructor
class SubscriptionPaidEventHandler {
    private final UpgradeAccountUseCase upgradeAccountUseCase;
    private final UpgradeUserMapper upgradeUserMapper;

    /// Handles the [AccountSubscriptionPaid] event by processing the subscription payment and initiating
    /// the account upgrade.
    ///
    /// @param accountSubscriptionPaid the event containing details of the paid subscription
    @EventListener
    void handle(AccountSubscriptionPaid event) {
        log.info("Event received: {}", event);
        var command = upgradeUserMapper.toCommand(event);
        var result = upgradeAccountUseCase.upgradeUser(command);
        switch (result) {
            case Success() -> log.info("Account upgraded.");
            case UserAlreadyUpgradedFailure(String message) -> log.info("Account upgraded failed: {}", message);
            case UserNotFoundFailure(String message) -> log.info("Account upgraded failed: {}", message);
            case InternalFailure(Throwable cause) -> log.error("Internal failure: {}", cause);
        }
    }

    /// Maps [AccountSubscriptionPaid] objects to [UpgradeUserCommand] objects.
    private static class UpgradeUserMapper {
        UpgradeUserCommand toCommand(AccountSubscriptionPaid event) {
            return new UpgradeUserCommand(new UserId(event.eventId()));
        }
    }
}
