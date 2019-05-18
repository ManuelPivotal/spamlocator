package org.telaside.spamlocator.repository;

import org.springframework.data.repository.CrudRepository;
import org.telaside.spamlocator.domain.IPGeoLocation;

public interface IPGeoLocationRepository extends CrudRepository<IPGeoLocation, String> {

	IPGeoLocation getByIp(String ip);
}
