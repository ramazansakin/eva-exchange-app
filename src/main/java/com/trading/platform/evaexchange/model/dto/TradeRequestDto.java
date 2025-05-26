package com.trading.platform.evaexchange.model.dto;

import com.trading.platform.evaexchange.model.enums.TradeType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequestDto {

    @NotBlank(message = "Portfolio code is required")
    private String portfolioCode;

    @NotBlank(message = "Share symbol is required")
    @Size(min = 3, max = 3, message = "Share symbol must be exactly 3 characters")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Share symbol must be 3 uppercase letters")
    private String shareSymbol;

    @NotNull(message = "Trade type is required")
    private TradeType tradeType;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

}
