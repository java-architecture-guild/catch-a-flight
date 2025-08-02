package jag.catchflight.account.adapter.out;

import jag.catchflight.account.domain.model.Account;
import jag.catchflight.account.domain.model.Password;
import jag.catchflight.account.domain.model.UserName;
import jag.catchflight.common.persistence.Version;
import jag.catchflight.sharedkernel.user.Email;
import jag.catchflight.sharedkernel.user.UserId;

/// A mapper class that converts between domain [Account] objects and
/// JDBC entity [AccountJdbcEntity] objects.
class AccountJdbcEntityMapper {

    /// Converts a domain [Account] object to a JDBC entity [AccountJdbcEntity].
    ///
    /// @param account the domain Account object to convert
    /// @return a new [AccountJdbcEntity] with values mapped from the Account
    /// @throws NullPointerException if the account parameter is null
    AccountJdbcEntity toJdbcEntity(Account account) {
        var userId = account.getUserId();
        var userName = account.getUserName();
        var email = account.getEmail();
        var password = account.getPassword();
        var userType = account.getAccountType();
        var version = account.getVersion();

        return new AccountJdbcEntity(
                userId != null ? userId.value() : null,
                email.value(),
                password.value(),
                userName.firstName(),
                userName.lastName(),
                userType,
                version.value());
    }

    /// Converts a JDBC entity [AccountJdbcEntity] to a domain [Account] object.
    ///
    /// @param entity the JDBC entity to convert
    /// @return a new [Account] object built with values from the JDBC entity
    /// @throws NullPointerException if the entity parameter is null
    Account toDomain(AccountJdbcEntity entity) {
        var userId = new UserId(entity.id());
        var userName = new UserName(entity.firstName(), entity.lastName());
        var email = new Email(entity.email());
        var password = new Password(entity.password());
        var userType = entity.accountType();
        var version = new Version(entity.version());

        return Account.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .password(password)
                .accountType(userType)
                .version(version)
                .build();
    }
}

