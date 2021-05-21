/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     19-5-2021 14:40:54                           */
/*==============================================================*/


DROP TABLE IF EXISTS IMAGE;
DROP TABLE IF EXISTS ACTIVITYINSEGMENT;
DROP TABLE IF EXISTS ACTIVITY;
DROP TABLE IF EXISTS `USER`;
DROP TABLE IF EXISTS POI;
DROP TABLE IF EXISTS SEGMENTINROUTE;
DROP TABLE IF EXISTS ROUTE;
DROP TABLE IF EXISTS SEGMENT;
DROP TABLE IF EXISTS COORDINATES;

/*==============================================================*/
/* Table: ACTIVITY                                              */
/*==============================================================*/
create table ACTIVITY
(
    ACTIVITYID           int not null auto_increment,
    USERID               int not null,
    POINT                int not null,
    DURATION             bigint not null,
    DISTANCE             int not null,
    ROUTEID              int,
    primary key (ACTIVITYID)
);

/*==============================================================*/
/* Table: ACTIVITYINSEGMENT                                     */
/*==============================================================*/
create table ACTIVITYINSEGMENT
(
    SEGMENTID            int not null,
    ACTIVITYID           int not null,
    SEQUENCENR           int not null,
    primary key (SEGMENTID, ACTIVITYID)
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
    NAME                 varchar(150) not null,
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
    IMAGE_URL             varchar(2083),
    primary key (USERID),
    unique key AK_AK_EMAILADRES_USERNAME (E_MAILADRES, USERNAME)
);

alter table ACTIVITY add constraint FK_FK_ACTIVITY_ROUTE foreign key (ROUTEID)
    references ROUTE (ROUTEID) on delete cascade on update cascade;

alter table ACTIVITY add constraint FK_FK_ACTIVITY_USER foreign key (USERID)
    references `USER` (USERID) on delete cascade on update cascade;

alter table ACTIVITYINSEGMENT add constraint FK_FK_ACTIVITYINSEGMENT_ACTIVITY foreign key (ACTIVITYID)
    references ACTIVITY (ACTIVITYID) on delete cascade on update cascade;

alter table ACTIVITYINSEGMENT add constraint FK_FK_ACTIVITYINSEGMENT_SEGMENT foreign key (SEGMENTID)
    references SEGMENT (SEGMENTID) on delete cascade on update cascade;

alter table IMAGE add constraint FK_FK_IMAGE_ACTIVITY foreign key (ACTIVITYID)
    references ACTIVITY (ACTIVITYID) on delete cascade on update cascade;

alter table IMAGE add constraint FK_FK_IMAGE_USER foreign key (USERID)
    references `USER` (USERID) on delete cascade on update cascade;

alter table POI add constraint FK_FK_POI_SEGMENT foreign key (SEGMENTID)
    references SEGMENT (SEGMENTID) on delete cascade on update cascade;

alter table SEGMENT add constraint FK_FK_SEGMENT_COORDINATES_ENDCOORD foreign key (ENDCOORD)
    references COORDINATES (COORDINATESID) on delete cascade on update cascade;

alter table SEGMENT add constraint FK_FK_SEGMENT_COORDINATES_STARTCOORD foreign key (STARTCOORD)
    references COORDINATES (COORDINATESID) on delete cascade on update cascade;

alter table SEGMENTINROUTE add constraint FK_FK_SEGMENTINROUTE_ROUTE foreign key (ROUTEID)
    references ROUTE (ROUTEID) on delete cascade on update cascade;

alter table SEGMENTINROUTE add constraint FK_FK_SEGMENTINROUTE_SEGMENT foreign key (SEGMENTID)
    references SEGMENT (SEGMENTID) on delete cascade on update cascade;

