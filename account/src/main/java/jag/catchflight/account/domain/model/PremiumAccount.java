package jag.catchflight.account.domain.model;

import jag.catchflight.common.annotations.domain.DomainValueObject;
import jag.catchflight.sharedkernel.user.UserId;

@DomainValueObject
public record PremiumAccount(UserId userId) implements CurrentAccount {}
