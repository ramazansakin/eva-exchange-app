package com.trading.platform.evaexchange.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.trading.platform.evaexchange.model.dto.TradeRequestDto;
import com.trading.platform.evaexchange.model.dto.TradeResponseDto;
import com.trading.platform.evaexchange.model.entity.Portfolio;
import com.trading.platform.evaexchange.model.entity.Share;
import com.trading.platform.evaexchange.model.entity.Transaction;
import com.trading.platform.evaexchange.model.enums.TradeType;
import com.trading.platform.evaexchange.model.exception.InsufficientSharesException;
import com.trading.platform.evaexchange.model.exception.PortfolioNotFoundException;
import com.trading.platform.evaexchange.model.exception.ShareNotRegisteredException;
import com.trading.platform.evaexchange.repository.PortfolioRepository;
import com.trading.platform.evaexchange.repository.ShareRepository;
import com.trading.platform.evaexchange.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TradingServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private ShareRepository shareRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TradingServiceImpl tradingService;

    private Portfolio testPortfolio;
    private Share testShare;
    private TradeRequestDto buyRequest;
    private TradeRequestDto sellRequest;

    @BeforeEach
    void setUp() {
        testPortfolio = Portfolio.builder()
                .id(1L)
                .portfolioCode("PF001")
                .portfolioName("Test Portfolio")
                .balance(BigDecimal.valueOf(50000))
                .build();

        testShare = Share.builder()
                .id(1L)
                .symbol("AAP")
                .companyName("Apple Inc.")
                .currentPrice(BigDecimal.valueOf(150.25))
                .build();

        buyRequest = TradeRequestDto.builder()
                .portfolioCode("PF001")
                .shareSymbol("AAP")
                .tradeType(TradeType.BUY)
                .quantity(10)
                .build();

        sellRequest = TradeRequestDto.builder()
                .portfolioCode("PF001")
                .shareSymbol("AAP")
                .tradeType(TradeType.SELL)
                .quantity(5)
                .build();
    }

    @Test
    void executeTrade_BuySuccess() {
        // Given
        Transaction savedTransaction = Transaction.builder()
                .id(1L)
                .portfolio(testPortfolio)
                .share(testShare)
                .tradeType(TradeType.BUY)
                .quantity(10)
                .pricePerShare(BigDecimal.valueOf(150.25))
                .totalAmount(BigDecimal.valueOf(1502.50))
                .transactionDate(LocalDateTime.now())
                .build();

        when(portfolioRepository.findByPortfolioCode("PF001"))
                .thenReturn(Optional.of(testPortfolio));
        when(shareRepository.findBySymbol("AAP"))
                .thenReturn(Optional.of(testShare));
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);

        // When
        TradeResponseDto result = tradingService.executeTrade(buyRequest);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        assertEquals("PF001", result.getPortfolioCode());
        assertEquals("AAP", result.getShareSymbol());
        assertEquals(TradeType.BUY, result.getTradeType());
        assertEquals(10, result.getQuantity());
        assertEquals(BigDecimal.valueOf(150.25), result.getPricePerShare());
        assertEquals(BigDecimal.valueOf(1502.50), result.getTotalAmount());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void executeTrade_SellSuccess() {
        // Given
        Transaction savedTransaction = Transaction.builder()
                .id(2L)
                .portfolio(testPortfolio)
                .share(testShare)
                .tradeType(TradeType.SELL)
                .quantity(5)
                .pricePerShare(BigDecimal.valueOf(150.25))
                .totalAmount(BigDecimal.valueOf(751.25))
                .transactionDate(LocalDateTime.now())
                .build();

        when(portfolioRepository.findByPortfolioCode("PF001"))
                .thenReturn(Optional.of(testPortfolio));
        when(shareRepository.findBySymbol("AAP"))
                .thenReturn(Optional.of(testShare));
        when(transactionRepository.getNetShareQuantity(anyLong(), anyLong()))
                .thenReturn(10); // Available shares
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);

        // When
        TradeResponseDto result = tradingService.executeTrade(sellRequest);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getTransactionId());
        assertEquals(TradeType.SELL, result.getTradeType());
        assertEquals(5, result.getQuantity());

        verify(transactionRepository, times(1)).getNetShareQuantity(anyLong(), anyLong());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void executeTrade_PortfolioNotFound() {
        // Given
        when(portfolioRepository.findByPortfolioCode("PF001"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(PortfolioNotFoundException.class,
                () -> tradingService.executeTrade(buyRequest));

        verify(shareRepository, never()).findBySymbol(anyString());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void executeTrade_ShareNotRegistered() {
        // Given
        when(portfolioRepository.findByPortfolioCode("PF001"))
                .thenReturn(Optional.of(testPortfolio));
        when(shareRepository.findBySymbol("AAP"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ShareNotRegisteredException.class,
                () -> tradingService.executeTrade(buyRequest));

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void executeTrade_InsufficientShares() {
        // Given
        when(portfolioRepository.findByPortfolioCode("PF001"))
                .thenReturn(Optional.of(testPortfolio));
        when(shareRepository.findBySymbol("AAP"))
                .thenReturn(Optional.of(testShare));
        when(transactionRepository.getNetShareQuantity(anyLong(), anyLong()))
                .thenReturn(3); // Insufficient shares (less than requested 5)

        // When & Then
        assertThrows(InsufficientSharesException.class,
                () -> tradingService.executeTrade(sellRequest));

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

}