create table blog (id integer not null auto_increment, content varchar(255), title varchar(255), primary key (id)) engine=InnoDB;
create table connection (id integer not null auto_increment, connection_id varchar(255), type varchar(255), primary key (id)) engine=InnoDB;
create table mapping (id integer not null auto_increment, client varchar(255), executor varchar(255), primary key (id)) engine=InnoDB;
