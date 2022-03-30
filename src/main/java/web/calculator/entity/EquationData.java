package web.calculator.entity;



import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EquationData {

    @NotNull
    private String function;

    @NotNull
    private double a;

    @NotNull
    private double b;
}
