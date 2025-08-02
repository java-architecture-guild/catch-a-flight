package jag.catchflight.account.domain.events;

import jag.catchflight.common.events.DomainEvent;

import java.util.UUID;

public record AccountCreationFailed() implements DomainEvent {
    @Override
    public UUID eventId() {
        return null;
    }
}
