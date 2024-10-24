INSERT INTO users (id, user_id, name, amount, created_at)
VALUES
    (1, '550e8400-e29b-41d4-a716-446655440000', 'Test User 1', 5000, NOW()),
    (2, '550e8400-e29b-41d4-a716-446655440001', 'Test User 2', 10000, NOW());

INSERT INTO concerts (id, title, created_at)
VALUES
    (1, 'Concert A', NOW()),
    (2, 'Concert B', NOW());

INSERT INTO concert_schedules (id, concert_id, open_at, start_at, end_at, total_seat, available_seat, total_seat_status, created_at)
VALUES
    (1, 1, '2024-10-25', '2024-10-25 18:00:00', '2024-10-25 20:00:00', 50, 50, 'AVAILABLE', NOW()),
    (2, 2, '2024-11-01', '2024-11-01 19:00:00', '2024-11-01 21:00:00', 50, 25, 'AVAILABLE', NOW());

INSERT INTO seats (id, concert_schedule_id, amount, position, seat_status, created_at, updated_at)
VALUES
    (1, 1, 1000, 1, 'AVAILABLE', NOW(), NOW()),
    (2, 1, 1000, 2, 'TEMPORARY', NOW(), NOW());

INSERT INTO payments (id, user_id, reservation_id, price, status, created_at)
VALUES
    (1, '550e8400-e29b-41d4-a716-446655440000', 1, 1000, 'COMPLETED', NOW()),
    (2, '550e8400-e29b-41d4-a716-446655440001', 2, 2000, 'COMPLETED', NOW());