INSERT INTO staff_member (id, fullname_firstname, fullname_surname, email_address, department_name)
VALUES ('S001', 'Alice', 'Johnson', 'alice.johnson@softco.com', 'Engineering'),
       ('S002', 'Ben', 'Taylor', 'ben.taylor@softco.com', 'Product'),
       ('S003', 'Clara', 'Nguyen', 'clara.nguyen@softco.com', 'Design');

INSERT INTO leave_entitlement (remaining_days, valid_from, valid_to, staff_member_id)
VALUES (15, '2025-01-01', '2025-12-31', 'S001'),
       (10, '2025-01-01', '2025-12-31', 'S001'),
       (20, '2025-01-01', '2025-12-31', 'S002'),
       (18, '2025-01-01', '2025-12-31', 'S003');

INSERT INTO leave_request (id, staff_member_id, fullname_firstname, fullname_surname, requested_on,
                           leave_status, description_of_status)
VALUES ('LR001', 'S001', 'Alice', 'Johnson',  '2025-08-20', 0, 'Awaiting manager approval'),
       ('LR002', 'S002', 'Ben', 'Taylor',  '2025-08-22', 1, 'Leave approved'),
       ('LR003', 'S003', 'Clara', 'Nguyen',  '2025-08-25', 2, 'Leave request rejected');

INSERT INTO leave_day (duration_days, start_date, end_date, leave_request_id)
VALUES
    (3, '2025-09-01', '2025-09-03', 'LR001'),
    (5, '2025-09-10', '2025-09-14', 'LR002'),
    (2, '2025-09-20', '2025-09-21', 'LR003');

