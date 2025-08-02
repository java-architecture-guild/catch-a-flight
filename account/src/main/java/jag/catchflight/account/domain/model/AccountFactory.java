package jag.catchflight.account.domain.model;

import jag.catchflight.account.port.out.FindCurrentAccountRepository;
import jag.catchflight.common.annotations.domain.DomainAggregate;
import jag.catchflight.common.annotations.domain.DomainFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainFactory
@RequiredArgsConstructor
public class AccountFactory {
    private final FindCurrentAccountRepository findCurrentAccountRepository;
    private final List<PasswordPolicy> passwordPolicies = PasswordPolicy.passwordPolicies;
}
