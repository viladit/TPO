package com.testinglab.lab2.stubs;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.testinglab.lab2.functions.MathFunction;
import java.util.Map;

public final class Lab2Stubs {

    public static final double PI = 3.141592653589793d;
    public static final double HALF_PI = 1.5707963267948966d;
    public static final double TWO_PI = 6.283185307179586d;
    public static final double EPSILON = 1e-10;

    private static final double LOOKUP_DELTA = 1e-8;

    public static final Map<Double, Double> SIN_VALUES = Map.ofEntries(
            Map.entry(-10.583185307179586d, 0.916165936749455d),
            Map.entry(-5.692000000000000d, 0.557345551804375d),
            Map.entry(-5.666480000000000d, 0.578350532314983d),
            Map.entry(-5.636000000000000d, 0.602943280050754d),
            Map.entry(-4.300000000000000d, 0.916165936749455d),
            Map.entry(-4.000000000000000d, 0.756802495307928d),
            Map.entry(-3.886830000000000d, 0.678146261832692d),
            Map.entry(-3.830000000000000d, 0.635308047704276d),
            Map.entry(-2.530000000000000d, -0.574172148354573d),
            Map.entry(-2.500000000000000d, -0.598472144103956d),
            Map.entry(-2.443492000000000d, -0.642763825835627d),
            Map.entry(-2.170000000000000d, -0.825784993105608d),
            Map.entry(-2.000000000000000d, -0.909297426825682d),
            Map.entry(-1.300100000000000d, -0.963584930482220d),
            Map.entry(-1.280000000000000d, -0.958015860289225d),
            Map.entry(-1.236000000000000d, -0.944477251514368d),
            Map.entry(-1.000000000000000d, -0.841470984807897d),
            Map.entry(-0.846500000000000d, -0.748965867258038d),
            Map.entry(-0.740000000000000d, -0.674287911628145d)
    );

    public static final Map<Double, Double> COS_VALUES = Map.ofEntries(
            Map.entry(-10.583185307179586d, -0.400799172079975d),
            Map.entry(-5.692000000000000d, 0.830280636823404d),
            Map.entry(-5.666480000000000d, 0.815788368249374d),
            Map.entry(-5.636000000000000d, 0.797784056647936d),
            Map.entry(-4.300000000000000d, -0.400799172079975d),
            Map.entry(-4.000000000000000d, -0.653643620863612d),
            Map.entry(-3.886830000000000d, -0.734926967502449d),
            Map.entry(-3.830000000000000d, -0.772258819646744d),
            Map.entry(-2.530000000000000d, -0.818734599277382d),
            Map.entry(-2.500000000000000d, -0.801143615546934d),
            Map.entry(-2.443492000000000d, -0.766064399510347d),
            Map.entry(-2.170000000000000d, -0.563985057569410d),
            Map.entry(-2.000000000000000d, -0.416146836547142d),
            Map.entry(-1.300100000000000d, 0.267402471468712d),
            Map.entry(-1.280000000000000d, 0.286715209631956d),
            Map.entry(-1.236000000000000d, 0.328576811981409d),
            Map.entry(-1.000000000000000d, 0.540302305868140d),
            Map.entry(-0.846500000000000d, 0.662608579541810d),
            Map.entry(-0.740000000000000d, 0.738468558729588d),
            Map.entry(5.870796326794897d, 0.916165936749455d)
    );

    public static final Map<Double, Double> TAN_VALUES = Map.ofEntries(
            Map.entry(-10.583185307179586d, -2.285847877366980d),
            Map.entry(-5.692000000000000d, 0.671273696008063d),
            Map.entry(-5.666480000000000d, 0.708946774463190d),
            Map.entry(-5.636000000000000d, 0.755772536473281d),
            Map.entry(-4.300000000000000d, -2.285847877366980d),
            Map.entry(-4.000000000000000d, -1.157821282349578d),
            Map.entry(-3.886830000000000d, -0.922739662333091d),
            Map.entry(-3.830000000000000d, -0.822662081081685d),
            Map.entry(-2.530000000000000d, 0.701292151157822d),
            Map.entry(-2.500000000000000d, 0.747022297238660d),
            Map.entry(-2.443492000000000d, 0.839046725375136d),
            Map.entry(-2.170000000000000d, 1.464196581137220d),
            Map.entry(-2.000000000000000d, 2.185039863261519d),
            Map.entry(-1.300100000000000d, -3.603500465757535d),
            Map.entry(-1.280000000000000d, -3.341349981115374d),
            Map.entry(-1.236000000000000d, -2.874448887062082d),
            Map.entry(-1.000000000000000d, -1.557407724654902d),
            Map.entry(-0.846500000000000d, -1.130329262829563d),
            Map.entry(-0.740000000000000d, -0.913089533274301d)
    );

    public static final Map<Double, Double> COT_VALUES = Map.ofEntries(
            Map.entry(-10.583185307179586d, -0.437474431217129d),
            Map.entry(-5.692000000000000d, 1.489705325781137d),
            Map.entry(-5.666480000000000d, 1.410543126819631d),
            Map.entry(-5.636000000000000d, 1.323149428882897d),
            Map.entry(-4.300000000000000d, -0.437474431217129d),
            Map.entry(-4.000000000000000d, -0.863691154450617d),
            Map.entry(-3.886830000000000d, -1.083729290959013d),
            Map.entry(-3.830000000000000d, -1.215565932837382d),
            Map.entry(-2.530000000000000d, 1.425939244220851d),
            Map.entry(-2.500000000000000d, 1.338648128304151d),
            Map.entry(-2.443492000000000d, 1.191828738206328d),
            Map.entry(-2.170000000000000d, 0.682968402523734d),
            Map.entry(-2.000000000000000d, 0.457657554360286d),
            Map.entry(-1.300100000000000d, -0.277507942486079d),
            Map.entry(-1.280000000000000d, -0.299280232735809d),
            Map.entry(-1.236000000000000d, -0.347892775029331d),
            Map.entry(-1.000000000000000d, -0.642092615934331d),
            Map.entry(-0.846500000000000d, -0.884697966233920d),
            Map.entry(-0.740000000000000d, -1.095182852895096d)
    );

    public static final Map<Double, Double> SEC_VALUES = Map.ofEntries(
            Map.entry(-10.583185307179586d, -2.495015133914648d),
            Map.entry(-5.692000000000000d, 1.204412045336780d),
            Map.entry(-5.666480000000000d, 1.225808112643150d),
            Map.entry(-5.636000000000000d, 1.253472028761415d),
            Map.entry(-4.300000000000000d, -2.495015133914648d),
            Map.entry(-4.000000000000000d, -1.529885656466397d),
            Map.entry(-3.886830000000000d, -1.360679420158396d),
            Map.entry(-3.830000000000000d, -1.294902660299085d),
            Map.entry(-2.530000000000000d, -1.221397020331868d),
            Map.entry(-2.500000000000000d, -1.248215651468818d),
            Map.entry(-2.443492000000000d, -1.305373282767324d),
            Map.entry(-2.170000000000000d, -1.773096621229064d),
            Map.entry(-2.000000000000000d, -2.402997961722381d),
            Map.entry(-1.300100000000000d, 3.739681217258333d),
            Map.entry(-1.280000000000000d, 3.487781486317586d),
            Map.entry(-1.236000000000000d, 3.043428396452337d),
            Map.entry(-1.000000000000000d, 1.850815717680925d),
            Map.entry(-0.846500000000000d, 1.509186616164092d),
            Map.entry(-0.740000000000000d, 1.354153793250634d)
    );

    public static final Map<Double, Double> CSC_VALUES = Map.ofEntries(
            Map.entry(-10.583185307179586d, 1.091505326587438d),
            Map.entry(-5.692000000000000d, 1.794219038373154d),
            Map.entry(-5.666480000000000d, 1.729055208088539d),
            Map.entry(-5.636000000000000d, 1.658530798976352d),
            Map.entry(-4.300000000000000d, 1.091505326587438d),
            Map.entry(-4.000000000000000d, 1.321348708810902d),
            Map.entry(-3.886830000000000d, 1.474608143230779d),
            Map.entry(-3.830000000000000d, 1.574039560200065d),
            Map.entry(-2.530000000000000d, -1.741637944065623d),
            Map.entry(-2.500000000000000d, -1.670921545558680d),
            Map.entry(-2.443492000000000d, -1.555781392488832d),
            Map.entry(-2.170000000000000d, -1.210968966921044d),
            Map.entry(-2.000000000000000d, -1.099750170294616d),
            Map.entry(-1.300100000000000d, -1.037791240155195d),
            Map.entry(-1.280000000000000d, -1.043824054956773d),
            Map.entry(-1.236000000000000d, -1.058786750444871d),
            Map.entry(-1.000000000000000d, -1.188395105778121d),
            Map.entry(-0.846500000000000d, -1.335174329987824d),
            Map.entry(-0.740000000000000d, -1.483046014550945d)
    );

    public static final Map<Double, Double> LN_VALUES = Map.ofEntries(
            Map.entry(0.000090000000000d, -9.315700887634009d),
            Map.entry(0.006000000000000d, -5.115995809754082d),
            Map.entry(0.073000000000000d, -2.617295837833746d),
            Map.entry(1.000000000000000d, 0.000000000000000d),
            Map.entry(2.000000000000000d, 0.693147180559945d),
            Map.entry(3.000000000000000d, 1.098612288668110d),
            Map.entry(5.000000000000000d, 1.609437912434100d)
    );

    public static final Map<Double, Double> LOG2_VALUES = Map.ofEntries(
            Map.entry(0.000090000000000d, -13.439715472994500d),
            Map.entry(0.006000000000000d, -7.380821783940932d),
            Map.entry(0.073000000000000d, -3.775959725782070d),
            Map.entry(1.000000000000000d, 0.000000000000000d),
            Map.entry(2.000000000000000d, 1.000000000000000d)
    );

    public static final Map<Double, Double> LOG3_VALUES = Map.ofEntries(
            Map.entry(0.000090000000000d, -8.479516371446923d),
            Map.entry(0.006000000000000d, -4.656780069296697d),
            Map.entry(0.073000000000000d, -2.382365339283429d),
            Map.entry(1.000000000000000d, 0.000000000000000d),
            Map.entry(2.000000000000000d, 0.630929753571457d)
    );

    public static final Map<Double, Double> LOG5_VALUES = Map.ofEntries(
            Map.entry(0.000090000000000d, -5.788170401394995d),
            Map.entry(0.006000000000000d, -3.178746921660801d),
            Map.entry(0.073000000000000d, -1.626217338123575d),
            Map.entry(1.000000000000000d, 0.000000000000000d),
            Map.entry(2.000000000000000d, 0.430676558073393d)
    );

    public static final Map<Double, Double> TRIGONOMETRIC_BRANCH_VALUES = Map.ofEntries(
            Map.entry(-5.666480000000000d, 5.208490000000000d),
            Map.entry(-4.300000000000000d, 79786.313976312800000d),
            Map.entry(-3.886830000000000d, 16.088100000000000d),
            Map.entry(-2.443490000000000d, 0.000000000000000d),
            Map.entry(-1.300100000000000d, 0.000000000000000d),
            Map.entry(-1.236000000000000d, 40.274870000000000d),
            Map.entry(-0.846500000000000d, 8.478400000000000d)
    );

    public static final Map<Double, Double> LOGARITHMIC_BRANCH_VALUES = Map.ofEntries(
            Map.entry(0.029500000000000d, 5.083140000000000d),
            Map.entry(0.500000000000000d, 1.000000000000000d),
            Map.entry(2.000000000000000d, -1.000000000000000d),
            Map.entry(8.000000000000000d, -3.000000000000000d),
            Map.entry(10.000000000000000d, -3.321930000000000d),
            Map.entry(12.010000000000000d, -3.586160000000000d)
    );

    private Lab2Stubs() {
    }

    public static MathFunction sin() {
        return stub("sin", SIN_VALUES);
    }

    public static MathFunction cos() {
        return stub("cos", COS_VALUES);
    }

    public static MathFunction tan() {
        return stub("tan", TAN_VALUES);
    }

    public static MathFunction cot() {
        return stub("cot", COT_VALUES);
    }

    public static MathFunction sec() {
        return stub("sec", SEC_VALUES);
    }

    public static MathFunction csc() {
        return stub("csc", CSC_VALUES);
    }

    public static MathFunction ln() {
        return stub("ln", LN_VALUES);
    }

    public static MathFunction log2() {
        return stub("log2", LOG2_VALUES);
    }

    public static MathFunction log3() {
        return stub("log3", LOG3_VALUES);
    }

    public static MathFunction log5() {
        return stub("log5", LOG5_VALUES);
    }

    public static MathFunction trigonometricBranch() {
        return stub("trigonometric branch", TRIGONOMETRIC_BRANCH_VALUES);
    }

    public static MathFunction logarithmicBranch() {
        return stub("logarithmic branch", LOGARITHMIC_BRANCH_VALUES);
    }

    private static MathFunction stub(String name, Map<Double, Double> table) {
        MathFunction function = mock(MathFunction.class);
        when(function.calculate(anyDouble(), anyDouble())).thenAnswer(invocation -> {
            double x = invocation.getArgument(0);
            return table.entrySet().stream()
                    .filter(entry -> distance(entry.getKey(), x) <= LOOKUP_DELTA)
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElseThrow(() -> new IllegalArgumentException("No tabulated " + name + " value for x=" + x));
        });
        return function;
    }

    private static double distance(double left, double right) {
        return left >= right ? left - right : right - left;
    }
}
