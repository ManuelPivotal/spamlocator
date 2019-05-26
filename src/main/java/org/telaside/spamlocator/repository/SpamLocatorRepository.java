package org.telaside.spamlocator.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

public interface SpamLocatorRepository extends CrudRepository<SpamLocatorMessage, String> {

	@Query("select distinct hhf.ip from HostHop hhf where hhf.ip is not null and hhf.ip not in (select igl.ip from IPGeoLocation igl)")
	Set<String> findUnsetIpGeolocation();
}
