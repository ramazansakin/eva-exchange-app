package com.trading.platform.evaexchange.repository;

import com.trading.platform.evaexchange.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COALESCE(SUM(CASE WHEN t.tradeType = 'BUY' THEN t.quantity ELSE -t.quantity END), 0) " +
            "FROM Transaction t WHERE t.portfolio.id = :portfolioId AND t.share.id = :shareId")
    Integer getNetShareQuantity(@Param("portfolioId") Long portfolioId, @Param("shareId") Long shareId);

}
