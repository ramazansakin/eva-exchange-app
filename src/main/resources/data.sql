-- Insert sample clients
INSERT INTO clients (client_code, name, email, created_at, updated_at)
VALUES ('CLI001', 'John Doe', 'john.doe@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CLI002', 'Jane Smith', 'jane.smith@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CLI003', 'Bob Johnson', 'bob.johnson@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CLI004', 'Alice Brown', 'alice.brown@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CLI005', 'Charlie Wilson', 'charlie.wilson@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample shares
INSERT INTO shares (symbol, company_name, current_price, created_at, updated_at)
VALUES ('AAP', 'Apple Inc.', 150.25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('GOO', 'Google LLC', 2800.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('MIC', 'Microsoft Corp', 330.75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('TSL', 'Tesla Inc.', 850.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('AMZ', 'Amazon.com Inc.', 3400.25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample portfolios
INSERT INTO portfolios (portfolio_code, portfolio_name, client_id, balance, created_at, updated_at)
VALUES ('PF001', 'John Doe Portfolio', 1, 50000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('PF002', 'Jane Smith Portfolio', 2, 75000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('PF003', 'Bob Johnson Portfolio', 3, 60000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('PF004', 'Alice Brown Portfolio', 4, 80000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('PF005', 'Charlie Wilson Portfolio', 5, 45000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample buy transactions
INSERT INTO transactions (portfolio_id, share_id, trade_type, quantity, price_per_share, total_amount, transaction_date)
VALUES
-- John Doe's transactions
(1, 1, 'BUY', 10, 150.25, 1502.50, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(1, 2, 'BUY', 5, 2800.50, 14002.50, CURRENT_TIMESTAMP - INTERVAL '4 days'),
(1, 1, 'SELL', 3, 150.25, 450.75, CURRENT_TIMESTAMP - INTERVAL '3 days'),

-- Jane Smith's transactions
(2, 3, 'BUY', 20, 330.75, 6615.00, CURRENT_TIMESTAMP - INTERVAL '6 days'),
(2, 4, 'BUY', 8, 850.00, 6800.00, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(2, 3, 'SELL', 5, 330.75, 1653.75, CURRENT_TIMESTAMP - INTERVAL '2 days'),

-- Bob Johnson's transactions
(3, 1, 'BUY', 15, 150.25, 2253.75, CURRENT_TIMESTAMP - INTERVAL '7 days'),
(3, 5, 'BUY', 3, 3400.25, 10200.75, CURRENT_TIMESTAMP - INTERVAL '4 days'),

-- Alice Brown's transactions
(4, 2, 'BUY', 6, 2800.50, 16803.00, CURRENT_TIMESTAMP - INTERVAL '8 days'),
(4, 4, 'BUY', 12, 850.00, 10200.00, CURRENT_TIMESTAMP - INTERVAL '3 days'),
(4, 4, 'SELL', 2, 850.00, 1700.00, CURRENT_TIMESTAMP - INTERVAL '1 day'),

-- Charlie Wilson's transactions
(5, 3, 'BUY', 25, 330.75, 8268.75, CURRENT_TIMESTAMP - INTERVAL '9 days'),
(5, 1, 'BUY', 12, 150.25, 1803.00, CURRENT_TIMESTAMP - INTERVAL '6 days'),
(5, 3, 'SELL', 8, 330.75, 2646.00, CURRENT_TIMESTAMP - INTERVAL '2 days');
