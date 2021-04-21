package nld.ede.runconnect.backend.domain;

public class Coordinate {
    private double longitude; //Chose for double because a float is Sufficient for storing 6 to 7 decimal digits. While a coordinate is max 13chars (https://stackoverflow.com/questions/15965166/what-are-the-lengths-of-location-coordinates-latitude-and-longitude#answer-61632275)
    private double latitude;
    private float altitude;
}
