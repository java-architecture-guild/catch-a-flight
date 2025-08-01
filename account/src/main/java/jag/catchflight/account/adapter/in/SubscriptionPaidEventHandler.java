// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.JAVA-ARCHITECTURE-GUILD - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package jag.catchflight.account.adapter.in;

import jag.catchflight.account.port.in.UpgradeAccountUseCase;
import jag.catchflight.common.annotations.hexagonal.InboundAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@InboundAdapter
@RequiredArgsConstructor
class SubscriptionPaidEventHandler {
    private final UpgradeAccountUseCase upgradeAccountUseCase;
}
