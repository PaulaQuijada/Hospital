CREATE TABLE appointments (
date_schedule DATE not null,
time_schedule TIME not null,
employee_num INT,
dni CHAR(9) not null,
appointment_num varchar(36) primary key
);
