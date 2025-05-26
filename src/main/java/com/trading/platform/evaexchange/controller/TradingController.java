package com.trading.platform.evaexchange.controller;

import com.trading.platform.evaexchange.model.dto.TradeRequestDto;
import com.trading.platform.evaexchange.model.dto.TradeResponseDto;
import com.trading.platform.evaexchange.service.TradingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trading")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Trading", description = "Trading operations for buying and selling shares")
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/buy")
    @Operation(summary = "Buy shares", description = "Execute a buy trade for the specified shares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trade executed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or business rule violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TradeResponseDto> buyShares(@Valid @RequestBody TradeRequestDto tradeRequest) {
        log.info("Received buy request: {}", tradeRequest);

        TradeResponseDto response = tradingService.executeTrade(tradeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sell")
    @Operation(summary = "Sell shares", description = "Execute a sell trade for the specified shares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trade executed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or business rule violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TradeResponseDto> sellShares(@Valid @RequestBody TradeRequestDto tradeRequest) {
        log.info("Received sell request: {}", tradeRequest);

        TradeResponseDto response = tradingService.executeTrade(tradeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
