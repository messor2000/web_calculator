package web.calculator.service;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class WebCalculatorService implements CalculatorService {

    private static final int PRECISION = 1000;
    private static final String FX = "f(x) = ";
    private static final String ERROR_MSG = "Error within limits";
    private static final String ERROR_BOARD_MSG = "Top board must be higher";

    @Override
    public String integrateRectangleMethod(String a, String b, String function) {

        if (Double.parseDouble(b) > Double.parseDouble(a)) {
            return ERROR_BOARD_MSG;
        } else if (isNumeric(a) && isNumeric(b)) {
            double convertedA = Double.parseDouble(a);
            double convertedB = Double.parseDouble(b);
            double step = (convertedB - convertedA) / (PRECISION - 1);
            double area = 0;

            for (int i = 0; i < (convertedB - convertedA) / step; i++) {
                Function f = new Function(FX + function);
                Expression e = new Expression("f(" + (convertedA + i * step) + ")", f);
                area += step * e.calculate();
            }

            return Double.toString(area);
        }

        return ERROR_MSG;
    }

    @Override
    public String integrateTrapezoidalMethod(String a, String b, String function) {

        if (Double.parseDouble(b) > Double.parseDouble(a)) {
            return ERROR_BOARD_MSG;
        } else if (isNumeric(a) && isNumeric(b)) {
            double convertedA = Double.parseDouble(a);
            double convertedB = Double.parseDouble(b);
            double step = (convertedB - convertedA) / (PRECISION - 1);
            double area = 0;
            Function f = new Function(FX + function);

            for (int i = 0; i < (convertedB - convertedA) / step; i++) {
                Expression e = new Expression("f(" + (convertedA + i * step) + ")", f);
                Expression e1 = new Expression("f(" + (convertedA + (i + 1) * step) + ")", f);
                area += step * (0.5 * (e.calculate() + e1.calculate()));
            }
            return Double.toString(area);
        }
        return ERROR_MSG;
    }

    @Override
    public String integrateSplineMethod(String a, String b, String function) {
        if (Double.parseDouble(b) > Double.parseDouble(a)) {
            return ERROR_BOARD_MSG;
        } else if (isNumeric(a) && isNumeric(b)) {
            double convertedA = Double.parseDouble(a);
            double convertedB = Double.parseDouble(b);
            int n = PRECISION - 1;
            double tau = (convertedB - convertedA) / n;
            double[] f = new double[(PRECISION + 1)];
            double[] alpha = new double[n];
            double[] beta = new double[n];
            double[] c = new double[n];
            double tmp = 0;

            Function stringFunction = new Function(FX + function);

            for (int i = 0; i <= PRECISION; i++) {
                double x = convertedA + tau * i;
                Expression e = new Expression("f(" + x + ")", stringFunction);
                f[i] = e.calculate();
            }

            alpha[0] = -1 / 4;
            beta[0] = f[2] - 2 * f[1] + f[0];

            for (int i = 1; i < n; i++) {
                alpha[i] = -1 / (alpha[i - 1] + 4);
                beta[i] = (f[i + 2] - 2 * f[i + 1] + f[i] - beta[i - 1]) / (alpha[i - 1] + 4);
            }

            c[n - 1] = (f[PRECISION] - 2 * f[PRECISION - 1] + f[PRECISION - 2] - beta[n - 1]) / (4 + alpha[n - 1]);

            for (int i = n - 2; i >= 0; i--) {
                c[i] = alpha[i + 1] * c[i + 1] + beta[i + 1];
            }

            for (int i = 0; i < n; i++) {
                c[i] = c[i] * 3 / (tau * tau);
            }

            double result = (5 * f[0] + 13 * f[1] + 13 * f[PRECISION - 1] + 5 * f[PRECISION]) / 12;
            for (int i = 2; i < PRECISION - 1; i++) {
                tmp = tmp + f[i];
            }
            result = (result + tmp) * tau - (c[0] + c[n - 1]) * tau * tau * tau / 36;

            return Double.toString(result);
        }
        return ERROR_MSG;
    }

    @Override
    public String integrateSimpsonMethod(String a, String b, String function) {
        return integrateSimpsonAndParabolasMethod(a, b, function);
    }

    @Override
    public String integrateParabolasMethod(String a, String b, String function) {
        return integrateSimpsonAndParabolasMethod(a, b, function);
    }

    private String integrateSimpsonAndParabolasMethod(String a, String b, String function) {
        if (Double.parseDouble(b) > Double.parseDouble(a)) {
            return ERROR_BOARD_MSG;
        } else if (isNumeric(a) && isNumeric(b)) {
            double convertedA = Double.parseDouble(a);
            double convertedB = Double.parseDouble(b);
            double step = (convertedB - convertedA) / (PRECISION - 1);
            Function f = new Function(FX + function);
            Expression ea = new Expression("f(" + a + ")", f);
            Expression eb = new Expression("f(" + b + ")", f);

            double sum = 1.0 / 3.0 * (ea.calculate() + eb.calculate());

            for (int i = 1; i < PRECISION - 1; i += 2) {
                double x = convertedA + step * i;
                Expression ex = new Expression("f(" + x + ")", f);
                sum += 4.0 / 3.0 * ex.calculate();
            }

            for (int i = 2; i < PRECISION - 1; i += 2) {
                double x = convertedA + step * i;
                Expression ex = new Expression("f(" + x + ")", f);
                sum += 2.0 / 3.0 * ex.calculate();
            }

            return Double.toString(sum * step);
        }
        return ERROR_MSG;
    }

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
