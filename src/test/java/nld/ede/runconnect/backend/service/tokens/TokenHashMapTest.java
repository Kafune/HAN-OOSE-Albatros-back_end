package nld.ede.runconnect.backend.service.tokens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenHashMapTest {

    TokenHashMap tokenHashMap;

    @BeforeEach
    void setUp() {
        TokenHashMap.killInstance();
        tokenHashMap = TokenHashMap.getInstance();
    }

    @Test
    void getTokenHappy() {
        tokenHashMap.addToken("Exists");
        assertNotEquals(null, tokenHashMap.getToken("Exists"));

    }

    /**
     * expects that there is no user in the map.
     */
    @Test
    void getTokenUserdoesntExist() {
        assertEquals(null, tokenHashMap.getToken("DoesntExcist"));
    }

    /**
     * Tests the timeout.
     */
    @Test
    void getTokenTimeOutTest() {
        tokenHashMap.addToken("Exists");
        tokenHashMap.setInterval(1000);
        //wait two seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(null, tokenHashMap.getToken("Exists"));
    }


}