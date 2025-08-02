package jag.catchflight.account.adapter.out;

import jag.catchflight.account.domain.model.*;
import jag.catchflight.account.port.out.CreateAccountRepository;
import jag.catchflight.account.port.out.FindAccountRepository;
import jag.catchflight.account.port.out.FindCurrentAccountRepository;
import jag.catchflight.account.port.out.UpdateAccountRepository;
import jag.catchflight.common.annotations.hexagonal.OutboundAdapter;
import jag.catchflight.sharedkernel.user.Email;
import jag.catchflight.sharedkernel.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/// A service class responsible for managing account-related operations, acting as a bridge between the
/// domain model and the persistence layer. It implements multiple repository interfaces for creating,
/// finding, and updating accounts, and serves as an outbound adapter for interacting with the JDBC repository.
@Service
@OutboundAdapter
@RequiredArgsConstructor
class AccountRepository
        implements CreateAccountRepository,
        FindCurrentAccountRepository,
        FindAccountRepository,
        UpdateAccountRepository {
    private final AccountJdbcRepository accountJdbcRepository;
    private final AccountJdbcEntityMapper accountJdbcEntityMapper;

    /// Loads an account by its [UserId].
    ///
    /// @param userId the [UserId] of the account to retrieve
    /// @return an [Optional] containing the [Account] if found, or an empty [Optional] if not found
    /// @throws NullPointerException if the userId parameter is null
    @Override
    public Optional<Account> load(UserId userId) {
        return accountJdbcRepository.findById(userId.value()).map(accountJdbcEntityMapper::toDomain);
    }

    /// Finds a [CurrentAccount] by its email address.
    ///
    /// @param email the [Email] to search for
    /// @return a [CurrentAccount] representing either an existing account (Regular or Premium) or a [NonExistingAccount]
    /// @throws NullPointerException if the email parameter is null
    @Override
    public CurrentAccount findByEmail(Email email) {
        return accountJdbcRepository
                .findByEmail(email.value())
                .map(this::existingUser)
                .orElseGet(this::nonExistingUser);
    }

    /// Creates a new account and persists it to the database.
    ///
    /// @param account the [Account] to create
    /// @return the created [Account] with updated persistence details
    /// @throws NullPointerException if the account parameter is null
    @Override
    public Account create(Account account) {
        var accountJdbcEntity = accountJdbcEntityMapper.toJdbcEntity(account);
        var createdJdbcEntity = accountJdbcRepository.save(accountJdbcEntity);
        return accountJdbcEntityMapper.toDomain(createdJdbcEntity);
    }

    /// Updates an existing account by saving it to the database.
    ///
    /// @param account the [Account] to update
    /// @throws NullPointerException if the account parameter is null
    @Override
    public void save(Account account) {
        accountJdbcRepository.save(accountJdbcEntityMapper.toJdbcEntity(account));
    }

    /// Converts an [AccountJdbcEntity] to a [CurrentAccount], determining the account type (Regular or Premium).
    ///
    /// @param accountJdbcEntity the JDBC entity to convert
    /// @return a [CurrentAccount] representing a [RegularAccount] or [PremiumAccount]
    /// @throws NullPointerException if the accountJdbcEntity parameter is null
    private CurrentAccount existingUser(AccountJdbcEntity accountJdbcEntity) {
        return switch (accountJdbcEntity.accountType()) {
            case REGULAR -> new RegularAccount(new UserId(accountJdbcEntity.id()));
            case PREMIUM -> new PremiumAccount(new UserId(accountJdbcEntity.id()));
        };
    }

    /// Creates a [NonExistingAccount] for cases where no account is found.
    ///
    /// @return a [NonExistingAccount] instance
    private NonExistingAccount nonExistingUser() {
        return new NonExistingAccount();
    }
}

