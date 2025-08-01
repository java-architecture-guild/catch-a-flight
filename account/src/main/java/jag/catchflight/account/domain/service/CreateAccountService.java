// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.domain.service;

import jag.catchflight.account.domain.model.AccountFactory;
import jag.catchflight.account.port.in.CreateAccountUseCase;
import jag.catchflight.account.port.out.AccountEventPublisher;
import jag.catchflight.account.port.out.CreateAccountRepository;
import jag.catchflight.common.annotations.domain.DomainService;
import lombok.RequiredArgsConstructor;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@DomainService
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {
    private final AccountFactory accountFactory;
    private final CreateAccountRepository createAccountRepository;
    private final AccountEventPublisher accountEventPublisher;

    @Override
    public CreateAccountResult createUser(CreateAccountCommand command) {
        throw new UnsupportedOperationException();
    }
}
