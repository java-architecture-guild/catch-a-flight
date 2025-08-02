package jag.catchflight.account.domain.events;

import jag.catchflight.common.events.DomainEvent;

import java.util.UUID;

/// A record representing a domain event for when an account is created.
/// This event implements the [DomainEvent] interface and contains
/// the unique identifier of the event.
///
/// @param eventId the unique identifier of the account creation event
public record AccountCreated(UUID eventId) implements DomainEvent {
    /// Returns the unique identifier of the account creation event
    ///
    /// @return the [UUID] representing the event's unique identifier
    @Override
    public UUID eventId() {
        return eventId;
    }
}
