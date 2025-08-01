// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.domain.model;

import jag.catchflight.account.port.out.FindCurrentAccountRepository;
import jag.catchflight.common.annotations.domain.DomainAggregate;
import jag.catchflight.common.annotations.domain.DomainFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@DomainFactory
@RequiredArgsConstructor
public class AccountFactory {
    private final FindCurrentAccountRepository findCurrentAccountRepository;
    private final List<PasswordPolicy> passwordPolicies = PasswordPolicy.passwordPolicies;
}
