package com.trading.platform.evaexchange.repository;

import com.trading.platform.evaexchange.model.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    Optional<Share> findBySymbol(String symbol);

}
