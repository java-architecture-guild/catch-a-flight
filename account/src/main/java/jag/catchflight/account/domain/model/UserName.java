// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainValueObject;

import java.util.Objects;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@DomainValueObject
public record UserName(String firstName, String lastName) {
    public UserName {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
    }
}

