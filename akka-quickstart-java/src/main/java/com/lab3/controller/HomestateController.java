package com.lab3.controller;

import com.lab3.ImuttableHomeState;
import com.lab3.TempratureUnits;
import com.lab3.dtos.*;
import com.lab3.refrigerator.ConsumeMessage;
import com.lab3.refrigerator.Product;
import com.lab3.refrigerator.Receipt;
import com.lab3.refrigerator.StoredProductsMessage;
import com.lab3.service.HomeAutomationWebservice;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Controller("/")
public class HomestateController {

    @Get(uri="/state", produces = MediaType.APPLICATION_JSON)
    @Operation(summary = "Returns a the Homestate",
            description = "Returns a the Homestate"
    )
    @ApiResponse(responseCode = "200", description = "Returns a the Homestate",
            content = @Content(mediaType = "application/json",
                    schema =  @Schema(implementation = ImuttableHomeState.class))
    )
    @Tag(name = "HomeState")
    public ImuttableHomeState getHomeState() {
        return HomeAutomationWebservice.getInstance().getStateOfHome();
    }

    @Post(uri="/blinds/open")
    @Operation(summary = "openBlinds",
            description = "openBlinds"
    )
    @Tag(name = "Blinds")
    public void openBlinds() {
        HomeAutomationWebservice.getInstance().OpenBlinds();
    }

    @Post(uri="/blinds/close")
    @Operation(summary = "closeBlinds",
            description = "closeBlinds"
    )
    @Tag(name = "Blinds")
    public void closeBlinds() {
        HomeAutomationWebservice.getInstance().CloseBlinds();
    }


    @Post(uri="/mediastation/on")
    @Operation(summary = "Turn on Mediastation",
            description = "Turn on Mediastation"
    )
    @Tag(name = "Mediastation")
    public void turnOnMediastation() {
        HomeAutomationWebservice.getInstance().StartMediaStation();
    }

    @Post(uri="/mediastation/off")
    @Operation(summary = "Turn off Mediastation",
            description = "Turn off Mediastation"
    )
    @Tag(name = "Mediastation")
    public void turnOffMediastation() {
        HomeAutomationWebservice.getInstance().StopMediaStation();
    }


    @Post(uri="/Temperature/Celsius")
    @Operation(summary = "set Temperature in Celsius",
            description = "set Temperature in Celsius",
            requestBody = @RequestBody(description = "Temperature in Celsius",content =@Content(mediaType = "application/json",
            schema = @Schema(implementation = TempPostMessage.class)))
    )
    @Tag(name = "Temperature")
    public void setTemperatureCelsius(@RequestBody @Body TempPostMessage value) {
        HomeAutomationWebservice.getInstance().setTemperature(TempratureUnits.CELSIUS,value.getTemp());
    }

    @Post(uri="/Temperature/Kelvin")
    @Operation(summary = "set Temperature in Kelvin",
            description = "set Temperature in Kelvin",
            requestBody = @RequestBody(description = "Temperature in Kelvin",content =@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TempPostMessage.class)))
    )
    @Tag(name = "Temperature")
    public void setTemperatureKelvin(@RequestBody @Body TempPostMessage value) {
        HomeAutomationWebservice.getInstance().setTemperature(TempratureUnits.KELVIN, value.getTemp());
    }

    @Post(uri="/Temperature/Fahrenheit")
    @Operation(summary = "set Temperature in Fahrenheit",
            description = "set Temperature in Fahrenheit",
            requestBody = @RequestBody(description = "Temperature in Fahrenheit",content =@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TempPostMessage.class)))
    )
    @Tag(name = "Temperature")
    public void setTemperatureFahrenheit(@RequestBody @Body TempPostMessage value) {
        HomeAutomationWebservice.getInstance().setTemperature(TempratureUnits.KELVIN, value.getTemp());
    }


    @Get(uri="/Temperature/Fahrenheit")
    @Operation(summary = "set Temperature in Fahrenheit",
            description = "set Temperature in Fahrenheit"
    )
    @ApiResponse(responseCode = "200", description = "Temperature in Fahrenheit",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))
    )
    @Tag(name = "Temperature")
    public Double getTemperatureFahrenheit() {
        return HomeAutomationWebservice.getInstance().getTemperature(TempratureUnits.FAHRENHEIT);
    }

    @Get(uri="/Temperature/Celsius")
    @Operation(summary = "set Temperature in Celsius",
            description = "set Temperature in Celsius"
    )
    @ApiResponse(responseCode = "200", description = "Temperature in Celsius",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))
    )
    @Tag(name = "Temperature")
    public Double getTemperatureCelsius() {
        return HomeAutomationWebservice.getInstance().getTemperature(TempratureUnits.CELSIUS);
    }


    @Get(uri="/Temperature/Kelvin")
    @Operation(summary = "set Temperature in Kelvin",
            description = "set Temperature in Kelvin"
    )
    @ApiResponse(responseCode = "200", description = "Temperature in Kelvin",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))
    )
    @Tag(name = "Temperature")
    public Double getTemperatureKelvin() {
        return HomeAutomationWebservice.getInstance().getTemperature(TempratureUnits.KELVIN);
    }

    @Get(uri="/fridgeInventory")
    @Operation(summary = "Get the Inventory of the Fridge",
            description = "Get the Inventory of the Fridge"
    )
    @ApiResponse(responseCode = "200", description = "Inventory of the Fridge",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
    )
    @Tag(name = "Fridge")
    public List<ProductDto> getFridgeInventory() {
        return HomeAutomationWebservice.getInstance().getFridgeInventory();
    }

    @Get(uri="/fridgeOrderHistory")
    @Operation(summary = "Get the Orderhistory of the Fridge",
            description = "Get the Orderhistory of the Fridge"
    )
    @ApiResponse(responseCode = "200", description = "Orderhistory of the Fridge",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReceiptDto.class)))
    )
    @Tag(name = "Fridge")
    public List<ReceiptDto> getFridgeOrderHistory() {
        return HomeAutomationWebservice.getInstance().getFridgeOrderReceipts();
    }

    @Post(uri="/Consume")
    @Operation(summary = "Consume Product out of the Fridge",
            description = "Consume Product out of the Fridge",
            requestBody = @RequestBody(description = "Product which you want to consume ",content =@Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductDto.class)))
    )
    @ApiResponse(responseCode = "200", description = "Product consumed out of the Fridge or see Errormessage at /lastConsumeMessage or in the server Logs ",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConsumeMessageDto.class))
    )
    @Tag(name = "Fridge")
    public ConsumeMessageDto consumeProduct(@RequestBody @Body ProductDto product) {
        return HomeAutomationWebservice.getInstance().consumeProduct(product);
    }

    @Post(uri="/order/{amount}")
    @Operation(summary = "order Product out of the Fridge",
            description = "order Product out of the Fridge",
            requestBody = @RequestBody(description = "Product which you want to order ",content =@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDto.class)))
    )
    @Tag(name = "Fridge")
    public void orderProduct(@RequestBody @Body ProductDto product,@Parameter(description = "The amount of Products", required = true)  String amount) {
        var amountvalue = tryParseInt(amount);

        if(amountvalue >0) {
            HomeAutomationWebservice.getInstance().orderProduct(product, amountvalue);
        }
    }

    @Get(uri="/lastConsumeMessage")
    @Operation(summary = "Consume Product out of the Fridge",
            description = "Consume Product out of the Fridge"

    )
    @ApiResponse(responseCode = "200", description = "Product consumed out of the Fridge or see Errormessage at ",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ConsumeMessageDto.class))
    )
    @Tag(name = "Fridge")
    public ConsumeMessageDto getLastConsumeMessage() {
        return HomeAutomationWebservice.getInstance().getLastConsumeMessage();
    }

    @Get(uri="/lastOrderMessage")
    @Operation(summary = "OrderMessage",
            description = "COrderMessage out of the Fridge"

    )
    @ApiResponse(responseCode = "200", description = "LastOrderMessage",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderMessageDto.class))
    )
    @Tag(name = "Fridge")
    public OrderMessageDto getLastOrderMessage() {
        return HomeAutomationWebservice.getInstance().getLastOrderMessage();
    }

    public Integer tryParseInt(String someText) {
        try {
            return Integer.parseInt(someText);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    @Schema
    public static class TempPostMessage{
        private double temp;

        @Schema
        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

}
