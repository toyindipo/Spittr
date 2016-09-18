drop table if exists spittle;
drop table if exists spitter;

create table spitter (
  id identity,
  username varchar(25) not null,
  password varchar(25) not null,
  lastName varchar(100) not null,
  firstName varchar(100) not null,
  email varchar(50) not null
);

create table if not exists Spittle (
  id integer identity primary key,
  spitter integer not null,
  message varchar(2000) not null,
  postedTime datetime not null,
  foreign key (spitter) references spitter(id)
);
