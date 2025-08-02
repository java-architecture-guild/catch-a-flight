package jag.catchflight.account.port.in;

import jag.catchflight.common.annotations.hexagonal.InboundPort;
import jag.catchflight.sharedkernel.user.UserId;

@InboundPort
public interface SignInUseCase {
    SignInResult signIn(SignInCommand signInCommand);

    record SignInCommand() {}

    sealed interface SignInResult {
        record Success(UserId userId) implements SignInResult {}
        record AuthenticationFailure() implements SignInResult {}
        record InternalFailure(Throwable cause) implements SignInResult {}
    }
}
