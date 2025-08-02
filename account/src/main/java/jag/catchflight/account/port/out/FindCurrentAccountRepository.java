package jag.catchflight.account.port.out;

import jag.catchflight.account.domain.model.CurrentAccount;
import jag.catchflight.common.annotations.domain.DomainRepository;
import jag.catchflight.common.annotations.hexagonal.OutboundPort;
import jag.catchflight.sharedkernel.user.Email;

@OutboundPort
@DomainRepository
public interface FindCurrentAccountRepository {
    CurrentAccount findByEmail(Email email);
}
