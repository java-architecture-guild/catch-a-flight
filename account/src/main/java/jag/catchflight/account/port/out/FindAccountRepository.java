package jag.catchflight.account.port.out;

import jag.catchflight.account.domain.model.Account;
import jag.catchflight.common.annotations.domain.DomainRepository;
import jag.catchflight.common.annotations.hexagonal.OutboundPort;
import jag.catchflight.sharedkernel.user.UserId;

import java.util.Optional;

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface FindAccountRepository {
    Optional<Account> load(UserId userId);
}
