package web.calculator.service;

public interface CalculatorService {
    double integrateRectangleMethod(double a, double b, String function);

    double integrateTrapezoidalMethod(double a, double b, String function);

    double integrateSimpsonMethod(double a, double b, String function);

    double integrateParabolasMethod(double a, double b, String function);

    double integrateSplineMethod(double a, double b, String function);
}
