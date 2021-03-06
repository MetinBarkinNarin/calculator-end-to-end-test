package org.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.config.CalculatorUIProperties;
import org.example.model.CalculateType;
import org.example.selenium.ISeleniumDriverService;
import org.example.testcontainer.CalculatorUIContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration
public final class CalculatorAngryStepDefinitions {

    @Autowired
    private ISeleniumDriverService seleniumDriverService;

    @Autowired
    private CalculatorUIProperties calculatorUIProperties;

    @Autowired
    private CalculatorUIContainerService calculatorUIContainerService;

    @Given("Either of any value is not given")
    public void givenNumbers() {
        this.seleniumDriverService.getUrlInDriver(this.calculatorUIContainerService.getUrl());

        this.seleniumDriverService.setElementValueByElementId(
                this.calculatorUIProperties.getFirstValueElementId(),
                "0"
        );// only first value is given
    }

    @When("Necati wants to perform {} operation")
    public void calculateNumbers(CalculateType calculateType) {
        switch (calculateType) {
            case ADDITION:
                this.seleniumDriverService.clickButtonByElementId(this.calculatorUIProperties.getAdditionButtonElementId());
                break;
            case SUBTRACTION:
                this.seleniumDriverService.clickButtonByElementId(this.calculatorUIProperties.getSubtractionButtonElementId());
                break;
            default:
                throw new UnsupportedOperationException("Calculate Type is unsupported operation: " + calculateType.name());
        }
    }

    @Then("Necati should see a warning notification")
    public void checkingResultNumber() {
        this.seleniumDriverService.waitUntilElementValueIsFill(this.calculatorUIProperties.getResultMessageElementId());

//        assertEquals(this.seleniumDriverService.getElementValueByElementId("resultValue"), resultValue);
        assertEquals(this.seleniumDriverService.getElementValueByElementId(
                this.calculatorUIProperties.getResultMessageElementId()),
                "FAIL"
        );
    }
}
