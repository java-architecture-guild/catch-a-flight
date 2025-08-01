// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.domain.service;

import jag.catchflight.account.port.in.CreateAccountUseCase;
import jag.catchflight.account.port.in.SignInUseCase;
import jag.catchflight.account.port.out.AccountEventPublisher;
import jag.catchflight.common.annotations.domain.DomainService;
import lombok.RequiredArgsConstructor;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@DomainService
@RequiredArgsConstructor
public class SignInService implements SignInUseCase {
    private final AccountEventPublisher accountEventPublisher;

    @Override
    public SignInResult signIn(SignInCommand signInCommand) {
        throw new UnsupportedOperationException();
    }
}
