package web.calculator.service;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.springframework.stereotype.Service;

@Service
public class WebCalculatorService implements CalculatorService {

    private static final double PRECISION = 1000;
    private static final String FX = "f(x) = ";

    @Override
    public double integrateRectangleMethod(double a, double b, String function) {
        double step = (b - a) / (PRECISION - 1);
        double area = 0;

        for (int i = 0; i < (b - a) / step; i++) {
            Function f = new Function(FX + function);
            Expression e = new Expression("f(" + (a + i * step) + ")", f);
            area += step * e.calculate();
        }

        return area;
    }

    @Override
    public double integrateTrapezoidalMethod(double a, double b, String function) {
        double step = (b - a) / (PRECISION - 1);
        double area = 0;
        Function f = new Function(FX + function);

        for (int i = 0; i < (b - a) / step; i++) {
            Expression e = new Expression("f(" + (a + i * step) + ")", f);
            Expression e1 = new Expression("f(" + (a + (i + 1) * step) + ")", f);
            area += step * (0.5 * (e.calculate() + e1.calculate()));
        }

        return area;
    }

    @Override
    public double integrateSimpsonMethod(double a, double b, String function) {
        return integrateSimpsonAndParabolasMethod(a, b, function);
    }

    @Override
    public double integrateParabolasMethod(double a, double b, String function) {
        return integrateSimpsonAndParabolasMethod(a, b, function);
    }

    private double integrateSimpsonAndParabolasMethod(double a, double b, String function) {
        double step = (b - a) / (PRECISION - 1);
        Function f = new Function(FX + function);
        Expression ea = new Expression("f(" + a + ")", f);
        Expression eb = new Expression("f(" + b + ")", f);

        double sum = 1.0 / 3.0 * (ea.calculate() + eb.calculate());

        for (int i = 1; i < PRECISION - 1; i += 2) {
            double x = a + step * i;
            Expression ex = new Expression("f(" + x + ")", f);
            sum += 4.0 / 3.0 * ex.calculate();
        }

        for (int i = 2; i < PRECISION - 1; i += 2) {
            double x = a + step * i;
            Expression ex = new Expression("f(" + x + ")", f);
            sum += 2.0 / 3.0 * ex.calculate();
        }

        return sum * step;
    }

}
