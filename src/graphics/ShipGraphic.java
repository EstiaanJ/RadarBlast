package graphics;

import gamemath.VectorD;
import processing.core.PApplet;

public class ShipGraphic {
    private VectorD pos = new VectorD(0,0);
    private float angle = 0.0f;

    public void updatePos(VectorD newPos, double newAngle){
        pos = new VectorD(newPos.x(), newPos.y());
        angle = (float)newAngle;
    }

    public void draw(PApplet context){
        context.rectMode(context.CENTER);
        context.noStroke();
        context.fill(255);
        context.pushMatrix();
            context.rotate(angle);
            context.translate(pos.xFloat(),pos.yFloat());

            context.rect(0,0,10,100);
        context.popMatrix();
    }
}
