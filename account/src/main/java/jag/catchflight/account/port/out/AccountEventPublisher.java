// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.port.out;

import jag.catchflight.common.annotations.hexagonal.OutboundAdapter;
import jag.catchflight.common.annotations.hexagonal.OutboundPort;
import jag.catchflight.common.events.DomainEvent;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
public interface AccountEventPublisher {
    void publish(DomainEvent event);
}
