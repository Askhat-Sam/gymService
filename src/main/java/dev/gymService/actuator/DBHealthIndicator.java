package dev.gymService.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBHealthIndicator implements HealthIndicator {
    private JdbcTemplate jdbcTemplate;
    public DBHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Health health() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Health.up().withDetail("ExternalDB", "Healthy!").build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("ExternalDB", "NOT Healthy!")
                    .withException(e)
                    .build();
        }
    }
}
