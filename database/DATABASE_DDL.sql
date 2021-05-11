/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     10-5-2021 16:57:21                           */
/*==============================================================*/


drop table if exists IMAGE;

drop table if exists ACTIVITY;

drop table if exists `USER`;

drop table if exists POI;

drop table if exists SEGMENTINROUTE;

drop table if exists ROUTE;

drop table if exists SEGMENT;

drop table if exists COORDINATES;

/*==============================================================*/
/* Table: ACTIVITY                                              */
/*==============================================================*/
create table ACTIVITY
(
    ACTIVITYID           int not null auto_increment,
    ROUTEID              int not null,
    USERID               int not null,
    POINT                int not null,
    DURATION             bigint,
    TEMPO                int,
    DISTANCE             int,
    primary key (ACTIVITYID)
);


/*==============================================================*/
/* Table: COORDINATES                                           */
/*==============================================================*/
create table COORDINATES
(
    COORDINATESID        int not null auto_increment,
    LATITUDE             double not null,
    LONGITUDE            double not null,
    ALTITUDE             int not null,
    primary key (COORDINATESID),
    unique key AK_KEY_2 (LATITUDE, LONGITUDE, ALTITUDE)
);

/*==============================================================*/
/* Table: IMAGE                                                 */
/*==============================================================*/
create table IMAGE
(
    USERID               int not null,
    ACTIVITYID           int not null,
    FILENAME             varchar(120) not null,
    DESCRIPTION          varchar(100),
    primary key (USERID, ACTIVITYID, FILENAME)
);

/*==============================================================*/
/* Table: POI                                                   */
/*==============================================================*/
create table POI
(
    SEGMENTID            int not null,
    NAME                 varchar(100) not null,
    DESCRIPTION          varchar(100),
    primary key (SEGMENTID, NAME)
);

/*==============================================================*/
/* Table: ROUTE                                                 */
/*==============================================================*/
create table ROUTE
(
    ROUTEID              int not null auto_increment,
    DISTANCE             int not null,
    DESCRIPTION          varchar(150) not null,
    primary key (ROUTEID)
);

/*==============================================================*/
/* Table: SEGMENT                                               */
/*==============================================================*/
create table SEGMENT
(
    SEGMENTID            int not null auto_increment,
    STARTCOORD           int not null,
    ENDCOORD             int not null,
    primary key (SEGMENTID)
);

/*==============================================================*/
/* Table: SEGMENTINROUTE                                        */
/*==============================================================*/
create table SEGMENTINROUTE
(
    ROUTEID              int not null,
    SEGMENTID            int not null,
    SEQUENCENR           int not null,
    primary key (ROUTEID, SEGMENTID)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table `USER`
(
    USERID               int not null auto_increment,
    FIRSTNAME            varchar(60) not null,
    LASTNAME             varchar(60) not null,
    E_MAILADRES          varchar(254) not null,
    USERNAME             varchar(150) not null,
    TOTALSCORE           int not null default 0,
    GOOGLE_ID_HASH         varchar(65535) not null,
    PHOTOURL             varchar(2083),
    primary key (USERID),
    unique key AK_KEY_2 (E_MAILADRES),
    unique key AK_KEY_4 (GOOGLE_ID_HASH)
);

alter table ACTIVITY add constraint FK_REFERENCE_1 foreign key (USERID)
    references `USER` (USERID) on delete cascade on update cascade;

alter table ACTIVITY add constraint FK_REFERENCE_2 foreign key (ROUTEID)
    references ROUTE (ROUTEID) on delete cascade on update cascade;

alter table IMAGE add constraint FK_REFERENCE_3 foreign key (ACTIVITYID)
    references ACTIVITY (ACTIVITYID) on delete cascade on update cascade;

alter table IMAGE add constraint FK_REFERENCE_6 foreign key (USERID)
    references `USER` (USERID) on delete cascade on update cascade;

alter table POI add constraint FK_REFERENCE_8 foreign key (SEGMENTID)
    references SEGMENT (SEGMENTID) on delete cascade on update cascade;

alter table SEGMENT add constraint FK_REFERENCE_10 foreign key (ENDCOORD)
    references COORDINATES (COORDINATESID) on delete cascade on update cascade;

alter table SEGMENT add constraint FK_REFERENCE_9 foreign key (STARTCOORD)
    references COORDINATES (COORDINATESID) on delete cascade on update cascade;

alter table SEGMENTINROUTE add constraint FK_REFERENCE_4 foreign key (ROUTEID)
    references ROUTE (ROUTEID) on delete cascade on update cascade;

alter table SEGMENTINROUTE add constraint FK_REFERENCE_7 foreign key (SEGMENTID)
    references SEGMENT (SEGMENTID) on delete cascade on update cascade;

