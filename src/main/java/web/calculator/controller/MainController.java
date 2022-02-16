package web.calculator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.calculator.service.CalculatorService;
import web.calculator.service.GetFunctionFromString;
import web.calculator.service.WebCalculatorService;

@Slf4j
@Controller
public class MainController {

    private final CalculatorService calculatorService;
    private final GetFunctionFromString getFunctionFromString;

    public MainController(WebCalculatorService calculatorService, GetFunctionFromString getFunctionFromString) {
        this.calculatorService = calculatorService;
        this.getFunctionFromString = getFunctionFromString;
    }

    @GetMapping(value = "/")
    public String getMainPage() {
        return "index";
    }

    @PostMapping("/integrate")
    public String calculateIntegral(@RequestParam("function") String function, @RequestParam("numFrom") double numFrom,
                                    @RequestParam("numTo") double numTo, @RequestParam("precision") int precision,
                                    RedirectAttributes redirectAttributes) {

        getFunctionFromString.setStr(function);


        double rectangleResult = calculatorService.integrateRectangleMethod(numFrom, numTo, precision, function);
        double trapezoidalResult = calculatorService.integrateTrapezoidalMethod(numFrom, numTo, precision, x -> Double.parseDouble(function));
        double simpsonRuleResult = calculatorService.integrateTrapezoidalMethod(numFrom, numTo, precision, x -> Double.parseDouble(function));
        double parabolasResult = calculatorService.integrateParabolasMethod(numFrom, numTo, precision, x -> Double.parseDouble(function));

        log.info(String.valueOf(rectangleResult));
        log.info(String.valueOf(trapezoidalResult));
        log.info(String.valueOf(simpsonRuleResult));
        log.info(String.valueOf(parabolasResult));

        redirectAttributes.addFlashAttribute("function", function);
        redirectAttributes.addFlashAttribute("rectangleResult", rectangleResult);
        redirectAttributes.addFlashAttribute("trapezoidalResult", trapezoidalResult);
        redirectAttributes.addFlashAttribute("simpsonRuleResult", simpsonRuleResult);
        redirectAttributes.addFlashAttribute("parabolasResult", parabolasResult);

        return "redirect:/";
    }


}
