package web.calculator.service;

public interface CalculatorService {
    String integrateRectangleMethod(String a, String b, String function);

    String integrateTrapezoidalMethod(String a, String b, String function);

    String integrateSimpsonMethod(String a, String b, String function);

    String integrateParabolasMethod(String a, String b, String function);

    String integrateSplineMethod(String a, String b, String function);
}
