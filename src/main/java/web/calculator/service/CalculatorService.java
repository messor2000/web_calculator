package web.calculator.service;

public interface CalculatorService {
    double integrateRectangleMethod(double a, double b, double precision, String function);

    double integrateTrapezoidalMethod(double a, double b, double precision, String function);

    double integrateSimpsonMethod(double a, double b, double precision, String function);

    double integrateParabolasMethod(double a, double b, double precision, String function);
}
