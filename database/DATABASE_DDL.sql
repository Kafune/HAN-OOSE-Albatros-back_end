/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     16-4-2021 10:54:19                           */
/*==============================================================*/


drop table if exists ACTIVITEIT;

drop table if exists AFBEELDING;

drop table if exists BEZIENSWAARDIGHEID;

drop table if exists GEBRUIKER;

drop table if exists ROUTE;

drop table if exists SEGMENTINROUTE;

/*==============================================================*/
/* Table: ACTIVITEIT                                            */
/*==============================================================*/
create table ACTIVITEIT
(
   ACTIVITEITID         int not null,
   ROUTEID              int not null,
   GEBRUIKERID          int not null,
   PUNTENWAARDE         int not null,
   DURATIE              bigint,
   TEMPO                int,
   AFGELEGDE_AFSTAND    int,
   primary key (ACTIVITEITID)
);


/*==============================================================*/
/* Table: AFBEELDING                                            */
/*==============================================================*/
create table AFBEELDING
(
   GEBRUIKERID          int not null,
   ACTIVITEITID         int,
   BESTANDSNAAM         varchar(120) not null,
   BESCHRIJVING         varchar(100),
   unique key AK_KEY_1 (GEBRUIKERID)
);
 

/*==============================================================*/
/* Table: BEZIENSWAARDIGHEID                                    */
/*==============================================================*/
create table BEZIENSWAARDIGHEID
(
   SEGMENTID            int not null,
   NAAM                 varchar(100) not null,
   ROUTEID              int,
   BESCHRIJVING         varchar(100),
   primary key (SEGMENTID, NAAM)
);
 

/*==============================================================*/
/* Table: GEBRUIKER                                             */
/*==============================================================*/
create table GEBRUIKER
(
   GEBRUIKERID          int not null,
   VOORNAAM             varchar(60) not null,
   ACHTERNAAM           varchar(60) not null,
   GEBRUIKERSNAAM       varchar(20) not null,
   WACHTWOORD           varchar(130) not null,
   GEBOORTEDATUM        date not null,
   TOTAALSCORE          int not null,
   primary key (GEBRUIKERID)
);
 

/*==============================================================*/
/* Table: ROUTE                                                 */
/*==============================================================*/
create table ROUTE
(
   ROUTEID              int not null,
   HOOGTEVERSCHIL       int not null,
   STARTCOORD           numeric(12,9) not null,
   EINDCOORD            numeric(12,9) not null,
   AFSTAND              int not null,
   primary key (ROUTEID)
);
 

/*==============================================================*/
/* Table: SEGMENTINROUTE                                        */
/*==============================================================*/
create table SEGMENTINROUTE
(
   ROUTEID              int not null,
   SEGMENTID            int not null,
   STARTCOORD           point not null,
   ENDCOORD             point not null,
   VOORGAANDSEGMENT     int,
   primary key (ROUTEID, SEGMENTID)
);
 

alter table ACTIVITEIT add constraint FK_REFERENCE_1 foreign key (GEBRUIKERID)
      references GEBRUIKER (GEBRUIKERID);

alter table ACTIVITEIT add constraint FK_REFERENCE_2 foreign key (ROUTEID)
      references ROUTE (ROUTEID);

alter table AFBEELDING add constraint FK_REFERENCE_3 foreign key (ACTIVITEITID)
      references ACTIVITEIT (ACTIVITEITID);

alter table AFBEELDING add constraint FK_REFERENCE_6 foreign key (GEBRUIKERID)
      references GEBRUIKER (GEBRUIKERID);

alter table BEZIENSWAARDIGHEID add constraint FK_REFERENCE_7 foreign key (ROUTEID, SEGMENTID)
      references SEGMENTINROUTE (ROUTEID, SEGMENTID);

alter table SEGMENTINROUTE add constraint FK_REFERENCE_4 foreign key (ROUTEID)
      references ROUTE (ROUTEID);

