-- Insert 10 users
INSERT INTO users (user_id, username, full_name, password, email, role) VALUES
                                                                            ('11111111-1111-1111-1111-111111111111', 'admin', 'Admin One', '$2a$10$iG4YlqftCe.AxmrklawXmedv/KbbcrY/AycM5bNU/bYXCIMLsu1/e', 'admin1@bank.com', 'ADMIN'),
                                                                            ('22222222-2222-2222-2222-222222222222', 'user1', 'User One', '$2a$10$somehashedpassword', 'user1@bank.com', 'USER'),
                                                                            ('33333333-3333-3333-3333-333333333333', 'user2', 'User Two', '$2a$10$somehashedpassword', 'user2@bank.com', 'USER'),
                                                                            ('44444444-4444-4444-4444-444444444444', 'user3', 'User Three', '$2a$10$somehashedpassword', 'user3@bank.com', 'USER'),
                                                                            ('55555555-5555-5555-5555-555555555555', 'user4', 'User Four', '$2a$10$somehashedpassword', 'user4@bank.com', 'USER'),
                                                                            ('66666666-6666-6666-6666-666666666666', 'user5', 'User Five', '$2a$10$somehashedpassword', 'user5@bank.com', 'USER'),
                                                                            ('77777777-7777-7777-7777-777777777777', 'user6', 'User Six', '$2a$10$somehashedpassword', 'user6@bank.com', 'USER'),
                                                                            ('88888888-8888-8888-8888-888888888888', 'user7', 'User Seven', '$2a$10$somehashedpassword', 'user7@bank.com', 'USER'),
                                                                            ('99999999-9999-9999-9999-999999999999', 'user8', 'User Eight', '$2a$10$somehashedpassword', 'user8@bank.com', 'USER'),
                                                                            ('00000000-0000-0000-0000-000000000000', 'user9', 'User Nine', '$2a$10$somehashedpassword', 'user9@bank.com', 'USER');

-- Insert 10 customers
INSERT INTO customers (customer_id, nic_passport, full_name, dob, address, mobile_no, email) VALUES
                                                                                                 ('c1111111-1111-1111-1111-111111111111', '123456789V', 'John Smith', '1980-05-15', '123 Main St, Colombo', '+94112345678', 'john@email.com'),
                                                                                                 ('c2222222-2222-2222-2222-222222222222', '234567890V', 'Jane Doe', '1985-08-21', '456 Oak Ave, Kandy', '+94122345678', 'jane@email.com'),
                                                                                                 ('c3333333-3333-3333-3333-333333333333', '345678901V', 'Robert Johnson', '1975-03-10', '789 Pine Rd, Galle', '+94132345678', 'robert@email.com'),
                                                                                                 ('c4444444-4444-4444-4444-444444444444', '456789012V', 'Sarah Williams', '1990-11-30', '321 Elm St, Negombo', '+94142345678', 'sarah@email.com'),
                                                                                                 ('c5555555-5555-5555-5555-555555555555', '567890123V', 'Michael Brown', '1982-07-22', '654 Maple Dr, Jaffna', '+94152345678', 'michael@email.com'),
                                                                                                 ('c6666666-6666-6666-6666-666666666666', '678901234V', 'Emily Davis', '1995-04-18', '987 Cedar Ln, Matara', '+94162345678', 'emily@email.com'),
                                                                                                 ('c7777777-7777-7777-7777-777777777777', '789012345V', 'David Wilson', '1978-09-05', '159 Birch St, Anuradhapura', '+94172345678', 'david@email.com'),
                                                                                                 ('c8888888-8888-8888-8888-888888888888', '890123456V', 'Lisa Taylor', '1988-12-12', '753 Spruce Ave, Ratnapura', '+94182345678', 'lisa@email.com'),
                                                                                                 ('c9999999-9999-9999-9999-999999999999', '901234567V', 'James Anderson', '1970-02-28', '246 Willow Rd, Kurunegala', '+94192345678', 'james@email.com'),
                                                                                                 ('c0000000-0000-0000-0000-000000000000', '012345678V', 'Mary Thomas', '1992-06-08', '864 Palm Blvd, Badulla', '+94102345678', 'mary@email.com');

-- Insert 10 saving accounts
INSERT INTO saving_accounts (account_number, customer_id, opening_date, account_type, balance) VALUES
                                                                                                   ('SAV001', 'c1111111-1111-1111-1111-111111111111', '2020-01-15 09:30:00', 'SAVING', 5000.00),
                                                                                                   ('SAV002', 'c2222222-2222-2222-2222-222222222222', '2020-02-20 10:15:00', 'PREMIUM', 15000.00),
                                                                                                   ('SAV003', 'c3333333-3333-3333-3333-333333333333', '2020-03-10 11:00:00', 'CHILDREN', 2000.00),
                                                                                                   ('SAV004', 'c4444444-4444-4444-4444-444444444444', '2020-04-05 14:30:00', 'WOMEN', 8000.00),
                                                                                                   ('SAV005', 'c5555555-5555-5555-5555-555555555555', '2020-05-12 13:45:00', 'SENIOR_CITIZEN', 12000.00),
                                                                                                   ('SAV006', 'c6666666-6666-6666-6666-666666666666', '2020-06-18 15:20:00', 'SAVING', 3000.00),
                                                                                                   ('SAV007', 'c7777777-7777-7777-7777-777777777777', '2020-07-22 16:10:00', 'PREMIUM', 25000.00),
                                                                                                   ('SAV008', 'c8888888-8888-8888-8888-888888888888', '2020-08-30 10:50:00', 'WOMEN', 7000.00),
                                                                                                   ('SAV009', 'c9999999-9999-9999-9999-999999999999', '2020-09-14 09:15:00', 'SENIOR_CITIZEN', 18000.00),
                                                                                                   ('SAV010', 'c0000000-0000-0000-0000-000000000000', '2020-10-25 11:30:00', 'SAVING', 4000.00);

-- Insert 10 transactions for each account (100 total, but here's 10 sample ones)
INSERT INTO transactions (account_number, amount, new_balance, transaction_type, description, reference_number) VALUES
                                                                                                                    ('SAV001', 1000.00, 6000.00, 'DEPOSIT', 'Initial deposit', 'DEP001'),
                                                                                                                    ('SAV001', -500.00, 5500.00, 'WITHDRAWAL', 'ATM withdrawal', 'WDR001'),
                                                                                                                    ('SAV002', 5000.00, 20000.00, 'DEPOSIT', 'Salary deposit', 'DEP002'),
                                                                                                                    ('SAV003', 100.00, 2100.00, 'INTEREST', 'Monthly interest', 'INT001'),
                                                                                                                    ('SAV004', -1000.00, 7000.00, 'WITHDRAWAL', 'Utility payment', 'WDR002'),
                                                                                                                    ('SAV005', 3000.00, 15000.00, 'DEPOSIT', 'Gift from relative', 'DEP003'),
                                                                                                                    ('SAV006', -200.00, 2800.00, 'FEE', 'Account maintenance fee', 'FEE001'),
                                                                                                                    ('SAV007', 5000.00, 30000.00, 'TRANSFER', 'From checking account', 'TRN001'),
                                                                                                                    ('SAV008', -500.00, 6500.00, 'WITHDRAWAL', 'Shopping', 'WDR003'),
                                                                                                                    ('SAV009', 2000.00, 20000.00, 'DEPOSIT', 'Investment return', 'DEP004');

-- Insert 10 audit logs
INSERT INTO audit_logs (actor_user_id, action_type, entity_type, entity_id, description, ip_address) VALUES
                                                                                                         ('11111111-1111-1111-1111-111111111111', 'LOGIN', 'USER', '11111111-1111-1111-1111-111111111111', 'Admin user logged in', '192.168.1.100'),
                                                                                                         ('22222222-2222-2222-2222-222222222222', 'CREATE', 'CUSTOMER', 'c1111111-1111-1111-1111-111111111111', 'Created new customer John Smith', '192.168.1.101'),
                                                                                                         ('11111111-1111-1111-1111-111111111111', 'UPDATE', 'ACCOUNT', 'SAV001', 'Updated account details', '192.168.1.100'),
                                                                                                         ('33333333-3333-3333-3333-333333333333', 'DELETE', 'USER', '00000000-0000-0000-0000-000000000000', 'Deleted inactive user', '192.168.1.102'),
                                                                                                         ('22222222-2222-2222-2222-222222222222', 'CREATE', 'TRANSACTION', NULL, 'Created deposit transaction', '192.168.1.101'),
                                                                                                         ('44444444-4444-4444-4444-444444444444', 'LOGOUT', 'USER', '44444444-4444-4444-4444-444444444444', 'User logged out', '192.168.1.103'),
                                                                                                         ('11111111-1111-1111-1111-111111111111', 'UPDATE', 'CUSTOMER', 'c2222222-2222-2222-2222-222222222222', 'Updated customer address', '192.168.1.100'),
                                                                                                         ('55555555-5555-5555-5555-555555555555', 'CREATE', 'ACCOUNT', 'SAV005', 'Created new savings account', '192.168.1.104'),
                                                                                                         ('33333333-3333-3333-3333-333333333333', 'LOGIN', 'USER', '33333333-3333-3333-3333-333333333333', 'User logged in', '192.168.1.102'),
                                                                                                         ('22222222-2222-2222-2222-222222222222', 'UPDATE', 'TRANSACTION', NULL, 'Corrected transaction amount', '192.168.1.101');