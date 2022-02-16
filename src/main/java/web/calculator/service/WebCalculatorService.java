package web.calculator.service;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;

@Service
public class WebCalculatorService implements CalculatorService {

    @Override
    public double integrateRectangleMethod(double a, double b, double precision, String function) {
        double step = (b - a) / (precision - 1);
        double area = 0;

        for (int i = 0; i < (b - a) / step; i++) {
            org.mariuszgromada.math.mxparser.Function f = new org.mariuszgromada.math.mxparser.Function("f(x) = " + function);
            Expression e = new Expression("f(" + (a + i * step) + ")", f);
            area += step * e.calculate();
        }

        return area;
    }

    @Override
    public double integrateTrapezoidalMethod(double a, double b, double precision, Function function) {
        double step = (b - a) / (precision - 1);
        double area = 0;

        for (int i = 0; i < (b - a) / step; i++) {
            area += step * (0.5 * (function.func(a + i * step) + function.func(a + (i + 1) * step)));
        }

        return area;
    }

    @Override
    public double integrateSimpsonMethod(double a, double b, double precision, Function function) {
        return integrateSimpsonAndParabolasMethod(a, b, precision, function);
    }

    @Override
    public double integrateParabolasMethod(double a, double b, double precision, Function function) {
        return integrateSimpsonAndParabolasMethod(a, b, precision, function);
    }

    private double integrateSimpsonAndParabolasMethod(double a, double b, double precision, Function function) {
        double step = (b - a) / (precision - 1);

        double sum = 1.0 / 3.0 * (function.func(a) + function.func(b));

        for (int i = 1; i < precision - 1; i += 2) {
            double x = a + step * i;
            sum += 4.0 / 3.0 * function.func(x);
        }

        for (int i = 2; i < precision - 1; i += 2) {
            double x = a + step * i;
            sum += 2.0 / 3.0 * function.func(x);
        }

        return sum * step;
    }
}
