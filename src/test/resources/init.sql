create table employee
(
    id            bigint generated always as identity
        constraint employee_pkey
            primary key,
    first_name    varchar(100) not null,
    last_name     varchar(100) not null,
    department_id bigint       not null,
    job_title     varchar(100) not null,
    gender        varchar      not null,
    date_of_birth date         not null
);