package jag.catchflight.account.domain.model;

public class PasswordPolicyException extends RuntimeException {
  public PasswordPolicyException(String message) {
    super(message);
  }
}
