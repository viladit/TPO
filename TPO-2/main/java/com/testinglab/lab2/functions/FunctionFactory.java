package com.testinglab.lab2.functions;

public final class FunctionFactory {

    private FunctionFactory() {
    }

    public static MathFunction sin() {
        return new SinFunction(cos());
    }

    public static MathFunction cos() {
        return new CosFunction();
    }

    public static MathFunction tan() {
        MathFunction cos = cos();
        MathFunction sin = new SinFunction(cos);
        return new TanFunction(sin, cos);
    }

    public static MathFunction cot() {
        MathFunction cos = cos();
        MathFunction sin = new SinFunction(cos);
        return new CotFunction(sin, cos);
    }

    public static MathFunction sec() {
        return new SecFunction(cos());
    }

    public static MathFunction csc() {
        MathFunction cos = cos();
        return new CscFunction(new SinFunction(cos));
    }

    public static MathFunction ln() {
        return new LnFunction();
    }

    public static MathFunction log3() {
        return new LogBaseFunction(ln(), 3.0d, "log_3(x)");
    }

    public static MathFunction log2() {
        return new LogBaseFunction(ln(), 2.0d, "log_2(x)");
    }

    public static MathFunction log5() {
        return new LogBaseFunction(ln(), 5.0d, "log_5(x)");
    }

    public static MathFunction trigonometricBranch() {
        MathFunction cos = cos();
        MathFunction sin = new SinFunction(cos);
        MathFunction tan = new TanFunction(sin, cos);
        MathFunction cot = new CotFunction(sin, cos);
        MathFunction sec = new SecFunction(cos);
        MathFunction csc = new CscFunction(sin);
        return new TrigonometricSystemFunction(sin, cos, tan, cot, sec, csc);
    }

    public static MathFunction logarithmicBranch() {
        MathFunction ln = ln();
        MathFunction log2 = new LogBaseFunction(ln, 2.0d, "log_2(x)");
        MathFunction log3 = new LogBaseFunction(ln, 3.0d, "log_3(x)");
        MathFunction log5 = new LogBaseFunction(ln, 5.0d, "log_5(x)");
        return new LogarithmicSystemFunction(log2, log3, log5, ln);
    }

    public static MathFunction system() {
        return new PiecewiseSystemFunction(trigonometricBranch(), logarithmicBranch());
    }
}
