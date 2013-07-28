# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table competition (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_competition primary key (id))
;

create table team (
  id                        bigint not null,
  team_name                 varchar(255),
  project                   varchar(255),
  competition_id            bigint,
  constraint pk_team primary key (id))
;

create table vote (
  id                        bigint not null,
  team_id                   bigint not null,
  vote                      integer,
  date                      timestamp,
  constraint pk_vote primary key (id))
;

create sequence competition_seq;

create sequence team_seq;

create sequence vote_seq;

alter table team add constraint fk_team_competition_1 foreign key (competition_id) references competition (id) on delete restrict on update restrict;
create index ix_team_competition_1 on team (competition_id);
alter table vote add constraint fk_vote_team_2 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_vote_team_2 on vote (team_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists competition;

drop table if exists team;

drop table if exists vote;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists competition_seq;

drop sequence if exists team_seq;

drop sequence if exists vote_seq;

