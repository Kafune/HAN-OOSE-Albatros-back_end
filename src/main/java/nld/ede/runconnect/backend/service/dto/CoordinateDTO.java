package nld.ede.runconnect.backend.service.dto;

public class CoordinateDTO {
    //Chose for double because a float is Sufficient for storing 6 to 7 decimal digits. While a coordinate is max 13chars (https://stackoverflow.com/questions/15965166/what-are-the-lengths-of-location-coordinates-latitude-and-longitude#answer-61632275)
    public double longitude;
    public double latitude;
    public float altitude;

}
