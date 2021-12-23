drop table balance;
drop table market;
drop table pending;
drop table orderinfo;
drop table aorder;

create table aorder (id bigint not null, account varchar(255), open_date datetime(6), type varchar(255), primary key (id)) engine=InnoDB;
create table balance (comment varchar(255), profit decimal(19,2), id bigint not null, primary key (id)) engine=InnoDB;
create table market (close_date datetime(6), close_price decimal(19,2), commission decimal(19,2), open_price decimal(19,2), profit decimal(19,2), stop_loss decimal(19,2), swap decimal(19,2), take_profit decimal(19,2), tax decimal(19,2), ticker varchar(255), volume decimal(19,2), id bigint not null, infoid bigint, primary key (id)) engine=InnoDB;
create table orderinfo (id bigint not null auto_increment, strategy varchar(255), primary key (id)) engine=InnoDB;
create table pending (close_price decimal(19,2), close_time datetime(6), comment varchar(255), open_price decimal(19,2), stop_loss decimal(19,2), take_profit decimal(19,2), ticker varchar(255), volume decimal(19,2), id bigint not null, primary key (id)) engine=InnoDB;

alter table balance add constraint FKp3pse83bwi296f2981s5q2fj2 foreign key (id) references aorder (id);
alter table market add constraint FKjc26fm0mspqyqgyrg3spyy2v7 foreign key (infoid) references orderinfo (id);
alter table market add constraint FKmi3sntd04i6g9ul7e8av41u9k foreign key (id) references aorder (id);
alter table pending add constraint FKlujy6n9h33c8ocpsatd9rpf3 foreign key (id) references aorder (id);
