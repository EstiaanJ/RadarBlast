package graphics;

import gamemath.VectorD;
import processing.core.PApplet;

public class RadarPing{
    public static final int MAX_AGE = 300;
    public VectorD pos;
    double brightness;
    public int age = 0;

    public RadarPing(VectorD posIn, double brightnessIn){
        pos = posIn;
        brightness = brightnessIn;
    }

    public void draw(Main context){
        age++;
        //int fillLevel = Math.round(context.map((float)brightness,0,1,0,255));
        context.noStroke();
        context.fill(255);
        context.circle(pos.xFloat(),pos.yFloat(),10 * (1/context.scalingFactor));
    }
}
