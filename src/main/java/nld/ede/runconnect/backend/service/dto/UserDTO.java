package nld.ede.runconnect.backend.service.dto;

public class UserDTO {

    private int userId;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String username;
    private int totalScore;
    private String afbeeldingUrl;
    private String googleId;

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getAfbeeldingUrl() {
        return afbeeldingUrl;
    }

    public void setAfbeeldingUrl(String afbeeldingUrl) {
        this.afbeeldingUrl = afbeeldingUrl;
    }
}
