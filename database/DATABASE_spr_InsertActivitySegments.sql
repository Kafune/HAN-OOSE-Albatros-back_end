DROP PROCEDURE if exists spr_InsertActivitySegments ;
        DELIMITER $$
CREATE PROCEDURE spr_InsertActivitySegments (IN userId int,
                                      IN point INT,
                                      IN duration bigint,
                                      IN distance double,
                                      IN sequence_nr int,
                                      IN start_latitude double,
                                      IN start_longitude double,
                                      IN start_Altitude  int,
                                      IN end_latitude double,
                                      IN end_longitude double,
                                      IN end_Altitude  int)
BEGIN
    DECLARE activity_Id int DEFAULT 0;
    DECLARE startId INT DEFAULT 0;
    DECLARE endId int DEFAULT 0;
    DECLARE segmentId2 int DEFAULT 0;

    SELECT ACTIVITYID
        INTO activity_Id
        FROM activity a
        WHERE a.USERID = userId
            AND a.POINT = point
            AND a.DURATION = duration
            AND a.DISTANCE = distance;


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


INSERT INTO activityinsegment (SEGMENTID, ACTIVITYID, SEQUENCENR) VALUES (segmentId2, activity_Id, sequence_nr);


        END$$

        DELIMITER ;
