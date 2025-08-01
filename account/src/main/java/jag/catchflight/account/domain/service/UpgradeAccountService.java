// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.domain.service;

import jag.catchflight.account.port.in.UpgradeAccountUseCase;
import jag.catchflight.account.port.out.AccountEventPublisher;
import jag.catchflight.account.port.out.FindAccountRepository;
import jag.catchflight.account.port.out.UpdateAccountRepository;
import jag.catchflight.common.annotations.domain.DomainService;
import lombok.RequiredArgsConstructor;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@DomainService
@RequiredArgsConstructor
public class UpgradeAccountService implements UpgradeAccountUseCase {
    private final FindAccountRepository findAccountRepository;
    private final UpdateAccountRepository updateAccountRepository;
    private final AccountEventPublisher accountEventPublisher;

    @Override
    public UpgradeUserResult upgradeUser(UpgradeUserCommand command) {
        throw new UnsupportedOperationException();
    }
}
