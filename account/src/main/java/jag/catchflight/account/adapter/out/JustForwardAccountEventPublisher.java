package jag.catchflight.account.adapter.out;

import jag.catchflight.account.port.out.AccountEventPublisher;
import jag.catchflight.common.annotations.hexagonal.OutboundAdapter;
import jag.catchflight.common.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@OutboundAdapter
@RequiredArgsConstructor
public class JustForwardAccountEventPublisher implements AccountEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        log.info("Event published. Event id: {}. Event body: {}", event.eventId(), event);
    }
}
