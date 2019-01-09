package io.pivotal.pal.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

import static java.lang.System.*;

/**
 * Application entry point and @Bean registration examples
 * */
@SpringBootApplication
public class PalTrackerApplication {

    /**
     * Starts the app
     */
    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }

    /**
     * Specifies desired TimeEntryRepository implementation, injected as needed by Spring
     */
    @Bean
    TimeEntryRepository timeEntryRepository() {
        if(getenv("ENV_NAME") != null && getenv("ENV_NAME").equalsIgnoreCase("test")) {
            return new InMemoryTimeEntryRepository();
        }
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return new JdbcTimeEntryRepository(dataSource);
    }

    /**
     *  Specifies desired ObjectMapper implementation, injected as needed by Spring
     */
    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(new JavaTimeModule())
                .build();
    }
}
