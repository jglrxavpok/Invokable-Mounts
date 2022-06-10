package io.github.jglrxavpok.invokablemounts;

public class Maths {

    /**
     * <a href="https://easings.net/en#easeInOutExpo">https://easings.net/en#easeInOutExpo</a>
     * @param x
     * @return
     */
    public static float easeInOutExpo(float x) {
        return x == 0
                ? 0
                : x == 1
                ? 1
                : x < 0.5 ? (float)Math.pow(2.0f, 20.0f * x - 10.0f) / 2.0f
                : (2.0f - (float)Math.pow(2.0f, -20.0f * x + 10.0f)) / 2;
    }

}
