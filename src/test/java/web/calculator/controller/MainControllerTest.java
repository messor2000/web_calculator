package web.calculator.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web.calculator.service.WebCalculatorService;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebCalculatorService calculatorService;

    @Before
    public void setUp() {
        MainController controller = new MainController(calculatorService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Test go to main page by path /")
    public void goToMainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test should show correct answer if function is correct")
    public void calculateFunctionWithCorrectInputsTest() throws Exception {
        String rectangleResult = calculatorService.integrateRectangleMethod("0" ,"2", "x^2");
        String trapezoidalResult = calculatorService.integrateTrapezoidalMethod("0" ,"2", "x^2");
        String simpsonRuleResult = calculatorService.integrateSimpsonMethod("0" ,"2", "x^2");
        String parabolasResult = calculatorService.integrateParabolasMethod("0" ,"2", "x^2");
        String splineResult = calculatorService.integrateSplineMethod("0" ,"2", "x^2");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/integrate")
                .param("function", "x^2")
                .param("numFrom", "0")
                .param("numTo", "2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.flash().attribute("rectangleResult", rectangleResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("trapezoidalResult", trapezoidalResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("parabolasResult", parabolasResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("splineResult", splineResult))
                .andExpect(redirectedUrl("/"));

        String epsilon = "0.01d";
        assertEquals(simpsonRuleResult, simpsonRuleResult, epsilon);
    }

    @Test
    @DisplayName("Test should show correct answer if trigonometrical function is correct")
    public void calculateTrigonometricalFunctionWithCorrectInputsTest() throws Exception {
        String rectangleResult = calculatorService.integrateRectangleMethod("0" ,"2", "cos(x^2)");
        String trapezoidalResult = calculatorService.integrateTrapezoidalMethod("0" ,"2", "cos(x^2)");
        String simpsonRuleResult = calculatorService.integrateSimpsonMethod("0" ,"2", "cos(x^2)");
        String parabolasResult = calculatorService.integrateParabolasMethod("0" ,"2", "cos(x^2)");
        String splineResult = calculatorService.integrateSplineMethod("0" ,"2", "cos(x^2)");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/integrate")
                .param("function", "cos(x^2)")
                .param("numFrom", "0.0")
                .param("numTo", "2.0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.flash().attribute("rectangleResult", rectangleResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("trapezoidalResult", trapezoidalResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("parabolasResult", parabolasResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("splineResult", splineResult))
                .andExpect(redirectedUrl("/"));

        String epsilon = "0.01d";
        assertEquals(simpsonRuleResult, simpsonRuleResult, epsilon);
    }

    @Test
    @DisplayName("Test should show NaN when incorrect result")
    public void showNaNWhenPutIncorrectFunction() throws Exception {
        String rectangleResult = calculatorService.integrateRectangleMethod("0" ,"2", "x2");
        String trapezoidalResult = calculatorService.integrateTrapezoidalMethod("0" ,"2", "x2");
        String simpsonRuleResult = calculatorService.integrateSimpsonMethod("0" ,"2", "x2");
        String parabolasResult = calculatorService.integrateParabolasMethod("0" ,"2", "x2");
        String splineResult = calculatorService.integrateSplineMethod("0" ,"2", "x2");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/integrate")
                .param("function", "x2")
                .param("numFrom", "0.0")
                .param("numTo", "2.0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.flash().attribute("rectangleResult", rectangleResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("trapezoidalResult", trapezoidalResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("simpsonRuleResult", simpsonRuleResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("parabolasResult", parabolasResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("splineResult", splineResult))
                .andExpect(redirectedUrl("/"));
    }
}
