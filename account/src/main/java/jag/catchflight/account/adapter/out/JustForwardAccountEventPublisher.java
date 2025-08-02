package jag.catchflight.account.adapter.out;

import jag.catchflight.account.port.out.AccountEventPublisher;
import jag.catchflight.common.annotations.hexagonal.OutboundAdapter;
import jag.catchflight.common.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/// Publishes account-related domain events using Spring's event publishing mechanism.
/// This class implements the [AccountEventPublisher] interface to forward events
/// to the Spring [ApplicationEventPublisher].
@Slf4j
@Component
@OutboundAdapter
@RequiredArgsConstructor
public class JustForwardAccountEventPublisher implements AccountEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    /// Publishes a domain event to the Spring application context.
    ///
    /// This method logs the event details and forwards the event to the
    /// [ApplicationEventPublisher] for further processing by registered listeners.
    ///
    /// @param event the [DomainEvent] to be published
    @Override
    public void publish(DomainEvent event) {
        log.info("Event published. Event id: {}. Event body: {}", event.eventId(), event);
        applicationEventPublisher.publishEvent(event);
    }
}
