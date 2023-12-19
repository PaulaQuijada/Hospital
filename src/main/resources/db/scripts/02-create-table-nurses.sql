CREATE TABLE nurses (
    name varchar(50) not null,
    dni char(9) unique not null,
    address varchar(100) not null,
    employee_num varchar(36) primary key,
    start_schedule TIME,
    end_schedule TIME
);
