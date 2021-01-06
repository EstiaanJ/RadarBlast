package graphics;

import gamemath.VectorD;
import processing.core.PApplet;

public class RadarPing{
    public static final int MAX_AGE = 300;

    VectorD pos;
    double brightness;
    public int age = 0;

    public RadarPing(VectorD posIn, double brightnessIn){
        pos = posIn;
        brightness = brightnessIn;
    }

    public void draw(PApplet context){
        age++;
        //int fillLevel = Math.round(context.map((float)brightness,0,1,0,255));
        context.stroke(255);
        context.point(pos.xFloat(),pos.yFloat());
    }
}
