package nld.ede.runconnect.backend.service.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    public int userId;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String username;
    public int totalScore;
    public String imageUrl;
    // This is the admin field. The name has been changed due to security reasons on the front-end.
    public boolean a61646d696e;
    public String token;
    public List<ActivityDTO> activities = new ArrayList<>();
}
