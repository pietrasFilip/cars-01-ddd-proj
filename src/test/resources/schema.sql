use test_cars_01;

create table if not exists components (
    id integer primary key auto_increment,
    name varchar(50) not null
);

create table if not exists cars (
    id integer primary key auto_increment,
    model varchar(50) not null,
    price decimal,
    color tinyint check (color between 0 and 4),
    mileage integer
);

create table if not exists cars_components (
    car_id integer not null,
    component_id integer not null,
    primary key (car_id, component_id),
    foreign key (car_id) references cars(id) on delete cascade on update cascade,
    foreign key (component_id) references components(id) on delete cascade on update cascade
);