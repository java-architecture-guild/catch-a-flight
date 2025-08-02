package jag.catchflight.account.port.out;

import jag.catchflight.account.domain.model.Account;
import jag.catchflight.common.annotations.domain.DomainRepository;
import jag.catchflight.common.annotations.hexagonal.OutboundPort;

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface UpdateAccountRepository {
    void save(Account account);
}
