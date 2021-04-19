/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     16-4-2021 14:06:58                           */
/*==============================================================*/


drop table if exists ACTIVITY;

drop table if exists COORDINATES;

drop table if exists IMAGE;

drop table if exists POI;

drop table if exists ROUTE;

drop table if exists SEGMENT;

drop table if exists SEGMENTINROUTE;

drop table if exists `USER`;

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
   LATITUDE             numeric(12, 9) not null,
   LONGITUDE            numeric(12, 9) not null,
   ALTITUDE             int not null,
   primary key (COORDINATESID)
);

/*==============================================================*/
/* Table: IMAGE                                                 */
/*==============================================================*/
create table IMAGE
(
   USERID               int not null,
   ACTIVITYID           int,
   FILENAME             varchar(120) not null,
   DESCRIPTION          varchar(100)
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
   AFSTAND              int not null,
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
   PREVIOUSSEGMENT      int,
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
   E_MAILADRES          varchar(100) not null,
   USERNAME             varchar(20) not null,
   PASSWORD             varchar(130) not null,
   BIRTHDATE            date not null,
   TOTALSCORE           int not null default 0,
   primary key (USERID),
   unique key AK_KEY_2 (E_MAILADRES),
   unique key AK_KEY_3 (USERNAME)
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

