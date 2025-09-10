

create sequence menu_item_sequence_id start with (select max(id) + 1 from menu_item);

create sequence event_store_sequence_id start with (select max(id) + 1 from event_store);

CREATE TABLE event_store(
                            id int AUTO_INCREMENT PRIMARY KEY,
                            occurred_on DATE NOT NULL,
                            event_body VARCHAR(65000) NOT NULL,
                            event_type VARCHAR(50) NOT NULL
);