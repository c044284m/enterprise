CREATE TABLE staff_member (
    id VARCHAR PRIMARY KEY,
    fullname_firstname VARCHAR NOT NULL,
    fullname_surname VARCHAR NOT NULL,
    email_address VARCHAR NOT NULL,
    department_name VARCHAR NOT NULL
);

CREATE TABLE leave_entitlement (
   id INT AUTO_INCREMENT PRIMARY KEY,
   remaining_days INT NOT NULL,
   valid_from DATE NOT NULL,
   valid_to DATE NOT NULL,
   staff_member_id VARCHAR NOT NULL,
   FOREIGN KEY (staff_member_id) REFERENCES staff_member(id)
);

CREATE TABLE leave_request (
    id VARCHAR PRIMARY KEY,
    staff_member_id VARCHAR NOT NULL,
    fullname_firstname VARCHAR NOT NULL,
    fullname_surname VARCHAR NOT NULL,
    requested_on DATE NOT NULL,
    leave_status INT NOT NULL,
    description_of_status VARCHAR NOT NULL,
    FOREIGN KEY (staff_member_id) REFERENCES staff_member(id)
);

CREATE TABLE leave_day (
   id INT AUTO_INCREMENT PRIMARY KEY,
   duration_days INT NOT NULL,
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   leave_request_id VARCHAR NOT NULL,
   FOREIGN KEY (leave_request_id) REFERENCES leave_request(id)
);