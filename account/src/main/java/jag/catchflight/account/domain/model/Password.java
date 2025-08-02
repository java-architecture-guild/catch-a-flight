package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainValueObject;

import java.util.Objects;

@DomainValueObject
public record Password(String value) {
    public Password {
        Objects.requireNonNull(value);
    }
}