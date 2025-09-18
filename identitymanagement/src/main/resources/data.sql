insert into role (type) values ('USER');
insert into role (type) values ('MANAGER');
insert into role (type) values ('ADMIN');


insert into app_user (id, username, password, first_name, surname, email, role_id)
values ('0000',
        'user',
        '$2a$12$NRxLx0wmM96WNoUb99tdPeNxKhrmAEBPIu7jD2zKGjXorQrJYnxmm',
        'first1',
        'surname1',
        'user@email.com',
        1);

insert into app_user (id, username, password, first_name, surname, email, role_id)
values ('0001',
        'manager',
        '$2a$12$tKqVf27s7Gr/HjzUmIEYtuUnwQeSL1mkCtcuNbk6ItX7GjBdo9ck2',
        'first2',
        'surname2',
        'manager@email.com',
        2);


insert into app_user (id, username, password, first_name, surname, email, role_id)
values ('0002',
        'admin',
        '$2a$12$llP7q0XajD1Pt6Pgw25Ps.EVMzngGJmZYTQj78Hxei8vPgzzKU3w6',
        'first3',
        'surname3',
        'admin@email.com',
        3);

//Password = user123
//Password = manager123
//Password = admin123
//https://bcrypt-generator.com/