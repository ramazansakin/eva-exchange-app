package com.trading.platform.evaexchange.model.dto;

import com.trading.platform.evaexchange.model.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeResponseDto {

    private Long transactionId;
    private String portfolioCode;
    private String shareSymbol;
    private TradeType tradeType;
    private Integer quantity;
    private BigDecimal pricePerShare;
    private BigDecimal totalAmount;
    private LocalDateTime transactionDate;
    private String message;

}
