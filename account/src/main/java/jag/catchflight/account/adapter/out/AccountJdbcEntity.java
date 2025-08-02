package jag.catchflight.account.adapter.out;

import jag.catchflight.sharedkernel.user.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/// A record representing an account entity for persistence in the database.
/// This entity maps to the "ACCOUNTS" table and stores user account details.
///
/// @param id          the unique identifier of the account
/// @param email       the email address associated with the account
/// @param password    the encrypted password for the account
/// @param firstName   the first name of the account holder
/// @param lastName    the last name of the account holder
/// @param accountType the type of account (e.g., REGULAR or PREMIUM)
/// @param version     the version of the account for optimistic locking
@Table(name = "ACCOUNTS")
public record AccountJdbcEntity(
        @Id UUID id,
        String email,
        String password,
        String firstName,
        String lastName,
        AccountType accountType,
        Integer version) {}