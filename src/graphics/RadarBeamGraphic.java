package graphics;

import gamemath.VectorD;
import processing.core.PApplet;

public class RadarBeamGraphic {
    private VectorD origin;
    private VectorD dest;

    public void updatePos(VectorD newOrigin, VectorD newDest){
        origin = newOrigin;
        dest = newDest;
    }

    public void draw(PApplet context){
        context.stroke(0,255,0);
        context.line(origin.xFloat(),origin.yFloat(),dest.xFloat(),dest.yFloat());
        context.stroke(0);
    }

}
