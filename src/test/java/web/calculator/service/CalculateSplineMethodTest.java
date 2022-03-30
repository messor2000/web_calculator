package web.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CalculateSplineMethodTest {

    @Autowired
    private WebCalculatorService calculatorService;

    @Test
    @DisplayName("Test that spline method should get correct result")
    public void shouldReturnCorrectAnswerWithCorrectFunction() {
        String methodResult = calculatorService.integrateSplineMethod("0", "2", "x^2");

        String expectedResult = "2.67";

        assertEquals(expectedResult, methodResult.substring(0, 4));
    }

    @Test
    @DisplayName("Test that spline method should get correct result")
    public void shouldReturnCorrectAnswerWithTrigonometry() {
        String methodResult = calculatorService.integrateSplineMethod("0", "2", "sin(x+2)");

        String expectedResult = "0.23";

        assertEquals(expectedResult, methodResult.substring(0, 4));
    }

    @Test
    @DisplayName("Test that spline method should get Nan when function is invalid")
    public void shouldReturnNaNWithIncorrectInput() {
        String methodResult = calculatorService.integrateSplineMethod("0", "2", "x2");

        String expectedResult = "NaN";

        assertEquals(expectedResult, methodResult);
    }

    @Test
    @DisplayName("Test that spline method should get error when boarder incorrect")
    public void shouldReturnErrorWithIncorrectBoarderInput() {
        String methodResult = calculatorService.integrateSplineMethod("fgj", "fdg", "x2");

        String expectedResult = "Error within limits";

        assertEquals(expectedResult, methodResult);
    }
}
