package com.lab3;
import com.lab3.service.HomeAutomationWebservice;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Server_TeamB",
                version = "0.0",
                description = "Server_TeamB API"
        )
)
public class Application {

    public static void main(String[] args) {
        HomeAutomationWebservice.getInstance();
        Micronaut.run(Application.class);
    }

}