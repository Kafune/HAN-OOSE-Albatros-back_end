DROP PROCEDURE if exists spr_InsertSegements ;
DELIMITER $$
CREATE PROCEDURE spr_InsertSegements (IN route_name varchar(200),
                                      IN route_distance INT,
                                      IN sequence_Nr varchar(10),
                                      IN start_latitude double,
                                      IN start_longitude double,
                                      IN start_Altitude  int,
                                      IN end_latitude double,
                                      IN end_longitude double,
                                      IN end_Altitude  int,
                                      IN poi_Name VARCHAR(100),
                                      IN poi_Description VARCHAR(100))
BEGIN
    DECLARE route_id int DEFAULT 0;
    DECLARE startId INT DEFAULT 0;
    DECLARE endId int DEFAULT 0;
    DECLARE segmentId2 int DEFAULT 0;

    SELECT ROUTEID INTO route_id FROM route WHERE DISTANCE = route_distance AND NAME = route_name;


    IF NOT EXISTS(SELECT LATITUDE,LONGITUDE, ALTITUDE
                        FROM coordinates
                        WHERE LATITUDE = start_latitude
                            AND LONGITUDE = start_longitude
                            AND ALTITUDE= start_Altitude)
        THEN

        INSERT INTO coordinates (LATITUDE,LONGITUDE, ALTITUDE) VALUES (start_latitude, start_longitude , start_Altitude);
    END IF;

    SELECT COORDINATESID
    INTO startId
    FROM coordinates
    WHERE LATITUDE = start_latitude
      AND LONGITUDE =  start_longitude
      AND ALTITUDE = start_Altitude;

    IF NOT EXISTS(SELECT LATITUDE,LONGITUDE, ALTITUDE
                  FROM coordinates
                  WHERE LATITUDE = end_latitude
                    AND LONGITUDE = end_longitude
                    AND ALTITUDE= end_Altitude)
    THEN

        INSERT INTO coordinates(LATITUDE,LONGITUDE, ALTITUDE) VALUES (end_latitude,end_longitude, end_Altitude);

    END IF;

    SELECT COORDINATESID
    INTO endId
    FROM coordinates
    WHERE LATITUDE = end_latitude
      AND LONGITUDE= end_longitude
      AND ALTITUDE = end_Altitude;


    INSERT INTO segment (STARTCOORD, ENDCOORD) VALUES (startId, endId);
    SELECT SEGMENTID INTO segmentId2 FROM segment WHERE STARTCOORD = startId AND ENDCOORD = endId;


    INSERT INTO segmentinroute (ROUTEID, SEGMENTID, SEQUENCENR) VALUES (route_Id, segmentId2, sequence_Nr);


    IF poi_Name != '-1' THEN
        INSERT INTO poi (SEGMENTID, NAME, DESCRIPTION) VALUES (segmentId2, poi_Name, poi_Description);
    END IF;

END$$

DELIMITER ;