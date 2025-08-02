package jag.catchflight.account.domain.events;

import jag.catchflight.common.events.DomainEvent;

import java.util.UUID;

/// A record representing a domain event for a successful account deletion.
///
/// @param eventId the unique identifier for this event
public record AccountDeleted(UUID eventId) implements DomainEvent {

    /// Returns the unique identifier for this event.
    ///
    /// @return the event's UUID
    @Override
    public UUID eventId() {
        return eventId;
    }
}
