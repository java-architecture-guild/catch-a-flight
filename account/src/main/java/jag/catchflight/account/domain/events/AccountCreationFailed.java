package jag.catchflight.account.domain.events;

import jag.catchflight.common.events.DomainEvent;

import java.util.UUID;

public record AccountCreationFailed(UUID eventId) implements DomainEvent {
    @Override
    public UUID eventId() {
        return eventId;
    }
}
