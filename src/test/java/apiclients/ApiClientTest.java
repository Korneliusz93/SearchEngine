package apiclients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientTest {

    @Test
    void getResponse() {
        String testPhrase = "test";
        ApiClient apiClient = new ApiClient();
        String test_result;
        try {
            test_result = apiClient.getResponse(testPhrase).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(test_result);
        Assertions.assertNotNull(test_result);
    }
        
}