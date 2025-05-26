package com.trading.platform.evaexchange.repository;

import com.trading.platform.evaexchange.model.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByPortfolioCode(String portfolioCode);

}
