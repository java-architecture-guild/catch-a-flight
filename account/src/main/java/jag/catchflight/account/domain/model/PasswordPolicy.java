package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainPolicy;

import java.util.List;

@DomainPolicy
interface PasswordPolicy {
    List<PasswordPolicy> passwordPolicies = List.of();
}
