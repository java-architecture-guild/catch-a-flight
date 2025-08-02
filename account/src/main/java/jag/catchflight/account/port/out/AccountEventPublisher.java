package jag.catchflight.account.port.out;

import jag.catchflight.common.annotations.hexagonal.OutboundAdapter;
import jag.catchflight.common.annotations.hexagonal.OutboundPort;
import jag.catchflight.common.events.DomainEvent;

@OutboundPort
public interface AccountEventPublisher {
    void publish(DomainEvent event);
}
