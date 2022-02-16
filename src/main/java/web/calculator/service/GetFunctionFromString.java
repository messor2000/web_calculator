package web.calculator.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
public class GetFunctionFromString {
    private int pos = -1;
    private int ch;
    private String str;

//    public GetFunctionFromString(String str) {
//        this.str = str;
//    }


    void nextChar() {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
    }

    public boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    public double parse() {
        nextChar();
        double x = parseExpression();
        if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)` | number
    //        | functionName `(` expression `)` | functionName factor
    //        | factor `^` factor

    public double parseExpression() {
        double x = parseTerm();
        for (; ; ) {
            if (eat('+')) x += parseTerm(); // addition
            else if (eat('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }

    public double parseTerm() {
        double x = parseFactor();
        for (; ; ) {
            if (eat('*')) {
                x *= parseFactor(); // multiplication
            }
            else if (eat('/')) {
                x /= parseFactor(); // division
            }
            else return x;
        }
    }

    public double parseFactor() {
        if (eat('+')) return +parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
            x = parseExpression();
            if (!eat(')')) throw new RuntimeException("Missing ')'");
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(str.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') { // functions
            while (ch >= 'a' && ch <= 'z') nextChar();
            String func = str.substring(startPos, this.pos);
            if (eat('(')) {
                x = parseExpression();
                if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
            } else {
                x = parseFactor();
            }
            switch (func) {
                case "sqrt":
                    x = Math.sqrt(x);
                    break;
                case "sin":
                    x = Math.sin(Math.toRadians(x));
                    break;
                case "cos":
                    x = Math.cos(Math.toRadians(x));
                    break;
                case "tan":
                    x = Math.tan(Math.toRadians(x));
                    break;
                default:
                    throw new RuntimeException("Unknown function: " + func);
            }
        } else {
            throw new RuntimeException("Unexpected: " + (char) ch);
        }

        if (eat('^')) {
            x = Math.pow(x, parseFactor()); // exponentiation
        }

        return x;
    }
}
