package dev.gymService.utills;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenHelper {
    @Autowired
    private ApplicationContext applicationContext;

    public String extractJwtToken(String responseBody) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract JWT token from response", e);
        }
    }

    public void saveJwtTokenToProperties(String jwtToken) {
        // Dynamically set the JWT token in the Spring Environment
        ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
        environment.getSystemProperties().put("test.jwt.token", jwtToken);

        System.out.println("jwtToken saved: " + jwtToken);
    }
}
