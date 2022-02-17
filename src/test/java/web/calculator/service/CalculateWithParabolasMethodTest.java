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
public class CalculateWithParabolasMethodTest {

    @Autowired
    private WebCalculatorService calculatorService;

    @Test
    @DisplayName("Test that parabolas method should get correct result")
    public void shouldReturnCorrectAnswerWithCorrectFunction() {
        double methodResult = calculatorService.integrateParabolasMethod(0, 2, "x^2");

        String result = String.format("%.2f", methodResult);
        String expectedResult = "2,66";

        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test that parabolas method should get correct result")
    public void shouldReturnCorrectAnswerWithTrigonometry() {
        double methodResult = calculatorService.integrateParabolasMethod(0, 2, "sin(x+2)");

        String result = String.format("%.2f", methodResult);
        String expectedResult = "0,24";

        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test that parabolas method should get correct result")
    public void shouldReturnNaNWithIncorrectInput() {
        double methodResult = calculatorService.integrateParabolasMethod(0, 2, "x2");

        String result = String.format("%.2f", methodResult);
        String expectedResult = "NaN";

        assertEquals(expectedResult, result);
    }
}
