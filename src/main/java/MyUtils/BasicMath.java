package MyUtils;

public class BasicMath {
    public static double lerp(float a, float b, float frac){
        return (a * (1.0 - frac)) + (b * frac);
    }
}
