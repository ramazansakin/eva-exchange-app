package com.trading.platform.evaexchange.model.entity;


import com.trading.platform.evaexchange.model.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id", nullable = false)
    private Share share;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType tradeType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerShare;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime transactionDate;

    @PrePersist
    private void calculateTotalAmount() {
        if (pricePerShare != null && quantity != null) {
            totalAmount = pricePerShare.multiply(BigDecimal.valueOf(quantity));
        }
    }

}
