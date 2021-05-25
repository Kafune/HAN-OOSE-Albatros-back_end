package nld.ede.runconnect.backend.service.helpers;

import nld.ede.runconnect.backend.domain.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GoogleIdVerifier {


    public static final int STATUS_OK = 200;

    public  boolean verifyGoogleId(User userToLogin) {
        Response response = getResponse(userToLogin.getGoogleId());

        return response.getStatus() == STATUS_OK
                && ((UserInfo) response.getEntity()).email.equals(userToLogin.getEmailAddress());
    }

    public  Response getResponse(String id) {

        Client client = ClientBuilder.newClient();

        String query = "id_token=" + id;
        WebTarget webTarget = client.target("https://oauth2.googleapis.com/tokeninfo?" + query);

        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = builder.get();
        response.readEntity(UserInfo.class);
        return response;
    }
}
