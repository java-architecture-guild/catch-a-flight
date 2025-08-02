package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainValueObject;

@DomainValueObject
public record NonExistingAccount() implements CurrentAccount {}
