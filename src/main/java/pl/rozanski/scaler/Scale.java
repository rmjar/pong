package pl.rozanski.scaler;

public class Scale {

    public static float scale(float val, float x, float y, float X, float Y) throws IllegalArgumentException {
        if (y - x == 0) {
            throw new IllegalArgumentException("Division by 0");
        }
        return (val - y) * (Y - X) / (y - x) + Y;
    }


}
