package jag.catchflight.account.port.out;

import jag.catchflight.common.annotations.domain.DomainRepository;
import jag.catchflight.common.annotations.hexagonal.OutboundPort;

@OutboundPort
@DomainRepository
public interface CreateAccountRepository {}
