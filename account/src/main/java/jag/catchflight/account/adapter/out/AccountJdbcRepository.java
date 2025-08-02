package jag.catchflight.account.adapter.out;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/// A Spring Data repository interface for performing CRUD operations on [AccountJdbcEntity] entities,
/// using a [UUID] as the primary key.
@Repository
public interface AccountJdbcRepository extends CrudRepository<AccountJdbcEntity, UUID> {
    /// Retrieves an [AccountJdbcEntity] by its email address.
    ///
    /// @param email the email address to search for
    /// @return an [Optional] containing the [AccountJdbcEntity] if found, or an empty [Optional] if no entity matches the email
    /// @throws NullPointerException if the email parameter is null
    Optional<AccountJdbcEntity> findByEmail(String email);
}
