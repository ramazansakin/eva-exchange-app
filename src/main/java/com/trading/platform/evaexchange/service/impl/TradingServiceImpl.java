package com.trading.platform.evaexchange.service.impl;

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
import com.trading.platform.evaexchange.service.TradingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TradingServiceImpl implements TradingService {

    private final PortfolioRepository portfolioRepository;
    private final ShareRepository shareRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public TradeResponseDto executeTrade(TradeRequestDto tradeRequest) {
        log.info("Executing trade: {} {} shares of {} for portfolio {}",
                tradeRequest.getTradeType(), tradeRequest.getQuantity(),
                tradeRequest.getShareSymbol(), tradeRequest.getPortfolioCode());

        // Validate and fetch entities
        Portfolio portfolio = validateAndGetPortfolio(tradeRequest.getPortfolioCode());
        Share share = validateAndGetShare(tradeRequest.getShareSymbol());

        // Execute trade based on type
        Transaction transaction = switch (tradeRequest.getTradeType()) {
            case BUY -> executeBuyTrade(portfolio, share, tradeRequest.getQuantity());
            case SELL -> executeSellTrade(portfolio, share, tradeRequest.getQuantity());
        };

        log.info("Trade executed successfully. Transaction ID: {}", transaction.getId());

        return mapToTradeResponse(transaction);
    }

    private Portfolio validateAndGetPortfolio(String portfolioCode) {
        return portfolioRepository.findByPortfolioCode(portfolioCode)
                .orElseThrow(() -> new PortfolioNotFoundException(
                        "Portfolio not found with code: " + portfolioCode));
    }

    private Share validateAndGetShare(String shareSymbol) {
        return shareRepository.findBySymbol(shareSymbol.toUpperCase())
                .orElseThrow(() -> new ShareNotRegisteredException(
                        "Share not registered with symbol: " + shareSymbol));
    }

    private Transaction executeBuyTrade(Portfolio portfolio, Share share, Integer quantity) {
        BigDecimal totalCost = share.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));

        // Check if portfolio has sufficient balance (optional business logic)
        if (portfolio.getBalance().compareTo(totalCost) < 0) {
            log.warn("Insufficient balance for buy trade. Required: {}, Available: {}",
                    totalCost, portfolio.getBalance());
            // Note: Uncomment if you want to enforce balance checking
            // throw new InsufficientFundsException("Insufficient balance for this purchase");
        }

        Transaction transaction = Transaction.builder()
                .portfolio(portfolio)
                .share(share)
                .tradeType(TradeType.BUY)
                .quantity(quantity)
                .pricePerShare(share.getCurrentPrice())
                .build();

        return transactionRepository.save(transaction);
    }

    private Transaction executeSellTrade(Portfolio portfolio, Share share, Integer quantity) {
        // Check if portfolio has sufficient shares
        Integer availableShares = transactionRepository.getNetShareQuantity(portfolio.getId(), share.getId());

        if (availableShares == null || availableShares < quantity) {
            throw new InsufficientSharesException(
                    String.format("Insufficient shares to sell. Available: %d, Requested: %d",
                            availableShares != null ? availableShares : 0, quantity));
        }

        Transaction transaction = Transaction.builder()
                .portfolio(portfolio)
                .share(share)
                .tradeType(TradeType.SELL)
                .quantity(quantity)
                .pricePerShare(share.getCurrentPrice())
                .build();

        return transactionRepository.save(transaction);
    }

    private TradeResponseDto mapToTradeResponse(Transaction transaction) {
        return TradeResponseDto.builder()
                .transactionId(transaction.getId())
                .portfolioCode(transaction.getPortfolio().getPortfolioCode())
                .shareSymbol(transaction.getShare().getSymbol())
                .tradeType(transaction.getTradeType())
                .quantity(transaction.getQuantity())
                .pricePerShare(transaction.getPricePerShare())
                .totalAmount(transaction.getTotalAmount())
                .transactionDate(transaction.getTransactionDate())
                .message("Trade executed successfully")
                .build();
    }

}
