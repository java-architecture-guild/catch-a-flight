package jag.catchflight.account.domain.events;

import jag.catchflight.common.events.DomainEvent;

import java.util.UUID;

/// A record representing a domain event indicating that an account has been upgraded.
///
/// @param eventId the unique identifier for this event
public record AccountUpgraded(UUID eventId) implements DomainEvent {
    /// Returns the unique identifier for this event.
    ///
    /// @return the event's UUID
    @Override
    public UUID eventId() {
        return eventId;
    }
}
