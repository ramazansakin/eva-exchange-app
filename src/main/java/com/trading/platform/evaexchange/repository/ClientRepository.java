package com.trading.platform.evaexchange.repository;

import com.trading.platform.evaexchange.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientCode(String clientCode);

}
