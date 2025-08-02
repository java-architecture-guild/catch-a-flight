package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainValueObject;

@DomainValueObject
public sealed interface CurrentAccount permits RegularAccount, PremiumAccount, NonExistingAccount {}
