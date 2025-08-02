package jag.catchflight.account.port.in;

import jag.catchflight.account.domain.model.Password;
import jag.catchflight.account.domain.model.UserName;
import jag.catchflight.common.annotations.hexagonal.InboundPort;
import jag.catchflight.sharedkernel.user.Email;
import jag.catchflight.sharedkernel.user.UserId;

import java.util.Objects;

@InboundPort
public interface CreateAccountUseCase {
    CreateAccountResult createUser(CreateAccountCommand command);

    record CreateAccountCommand(Email email, Password password, UserName userName) {
        public CreateAccountCommand {
            Objects.requireNonNull(email);
            Objects.requireNonNull(password);
            Objects.requireNonNull(userName);
        }
    }

    sealed interface CreateAccountResult {
        record Success(UserId userId) implements CreateAccountResult {}
        record ExistingAccountFailure(String message) implements CreateAccountResult {}
        record PasswordPolicyFailure(String message) implements CreateAccountResult {}
        record InternalFailure(Throwable cause) implements CreateAccountResult {}
    }
}
