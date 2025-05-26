package com.trading.platform.evaexchange.service;


import com.trading.platform.evaexchange.model.dto.TradeRequestDto;
import com.trading.platform.evaexchange.model.dto.TradeResponseDto;

public interface TradingService {

    TradeResponseDto executeTrade(TradeRequestDto tradeRequest);

}
