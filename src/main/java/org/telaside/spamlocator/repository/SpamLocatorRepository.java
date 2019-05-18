package org.telaside.spamlocator.repository;

import org.springframework.data.repository.CrudRepository;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

public interface SpamLocatorRepository extends CrudRepository<SpamLocatorMessage, Long> {

}
