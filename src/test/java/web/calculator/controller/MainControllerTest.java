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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        MainController controller = new MainController(calculatorService); // 1
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); // 2
    }

    @Test
    @DisplayName("Test go to main page by path /")
    public void goToMainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test go to main page by path /calculate")
    public void calculateFunctionWithCorrectInputsTest() throws Exception {
        double rectangleResult = calculatorService.integrateRectangleMethod(0 ,2, "x^2");
        double trapezoidalResult = calculatorService.integrateTrapezoidalMethod(0 ,2, "x^2");
        double simpsonRuleResult = calculatorService.integrateSimpsonMethod(0 ,2, "x^2");
        double parabolasResult = calculatorService.integrateParabolasMethod(0 ,2, "x^2");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                .param("function", "x^2")
                .param("numFrom", "0.0")
                .param("numTo", "0.2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.flash().attribute("rectangleResult", rectangleResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("trapezoidalResult", trapezoidalResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("simpsonRuleResult", simpsonRuleResult))
                .andExpect(MockMvcResultMatchers.flash().attribute("parabolasResult", parabolasResult))
                .andExpect(status().isOk());
    }
}
