package nld.ede.runconnect.backend.service.tokens;

import java.util.HashMap;

public class TokenHashMap {
    private static TokenHashMap _instance = null;
    HashMap<String, String> userToken = new HashMap<String, String>(); //email, token
    private long interval = 2592000000L; // 30days

    /**
     * Adds a token to a user. Except if the token already exists
     * @param email
     * @return String Token
     */
    public synchronized String addToken (String email){
        if(getToken(email) == null){
            userToken.put(email, String.valueOf(System.currentTimeMillis()));
        }
        return getToken(email);
    }

    /**
     * Gets the token of a user and checks if it is older than 30 days. If so it removes the token.
     * @param email
     * @return String Token
     */
    public String getToken(String email){
        if(userToken.get(email) == null){
            return null;
        }else if(Long.parseLong(userToken.get(email)) < System.currentTimeMillis() - interval ){ //(Token < currentTime - 30days)
            userToken.remove(email);
        }
        return userToken.get(email);
    }

    /**
     * Checks if the token is being used.
     * true if it exists
     * @return boolean
     */
    public boolean doesExist(String token) {
        return userToken.containsValue(token);
    }

    /**
     * gets the email connected to a token returns Null if no email
     * @param token
     * @return
     */
    public String getEmail(String token){
        for (String i : userToken.keySet()) {
            if(userToken.get(i).equals(token)){
                return i;
            }
        }
        return null;
    }

    /**
     * Sets interval. USE ONLY FOR TESTS!
     * @param interval
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /*Singleton methods:*/

    /**
     * Private constructor replaces the default public constructor
     */
    private TokenHashMap() {
    }

    /**
     * Creates a instance of TokenHashMap
     * synchronized creator to avoid multithreading issues
     * this is one more check to avoid instantiation of more than one object
     */
    private synchronized static void createInstance() {
        if (_instance == null) {
            _instance = new TokenHashMap();
        }
    }

    /**
     * makes a instance of TokenHashMap if it not exists and returns it
     *
     * @return TokenHashMap
     */
    public static TokenHashMap getInstance() {
        if (_instance == null) {
            createInstance();
        }
        return _instance;
    }

    /**
     * Removes the singleton instance. Mainly used for testing.
     * DO NOT USE IT FOR ANYTHING ELSE!
     */
    public static void killInstance() {
        if (_instance != null) {
            _instance = null;
        }
    }


}
