// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainPolicy;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@DomainPolicy
interface PasswordPolicy {
    List<PasswordPolicy> passwordPolicies = List.of();
}
