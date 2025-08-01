// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.common.policy;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Domain policy output sealed types.
 */
public sealed interface DomainPolicyOutput {
    record Allowance() implements DomainPolicyOutput {}
    record Rejection(String reason) implements DomainPolicyOutput {}
}
