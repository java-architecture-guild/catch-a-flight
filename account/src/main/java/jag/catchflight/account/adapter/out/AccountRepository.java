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

    @Override
    public Optional<Account> load(UserId userId) {
        return accountJdbcRepository.findById(userId.value()).map(accountJdbcEntityMapper::toDomain);
    }

    @Override
    public CurrentAccount findByEmail(Email email) {
        return accountJdbcRepository
                .findByEmail(email.value())
                .map(this::existingUser)
                .orElseGet(this::nonExistingUser);
    }

    @Override
    public Account create(Account account) {
        var accountJdbcEntity = accountJdbcEntityMapper.toJdbcEntity(account);
        var createdJdbcEntity = accountJdbcRepository.save(accountJdbcEntity);
        return accountJdbcEntityMapper.toDomain(createdJdbcEntity);
    }

    @Override
    public void save(Account account) {
        accountJdbcRepository.save(accountJdbcEntityMapper.toJdbcEntity(account));
    }

    private CurrentAccount existingUser(AccountJdbcEntity accountJdbcEntity) {
        return switch (accountJdbcEntity.accountType()) {
            case REGULAR -> new RegularAccount(new UserId(accountJdbcEntity.id()));
            case PREMIUM -> new PremiumAccount(new UserId(accountJdbcEntity.id()));
        };
    }

    private NonExistingAccount nonExistingUser() {
        return new NonExistingAccount();
    }
}

