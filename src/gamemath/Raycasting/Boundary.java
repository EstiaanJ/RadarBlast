package gamemath.Raycasting;

import gamemath.VectorD;
import processing.core.PApplet;

public class Boundary {
    public VectorD pointA;
    public VectorD pointB;

    public Boundary(VectorD pointA, VectorD pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public void draw(PApplet context){
        context.stroke(255);
        context.line(pointA.xFloat(),pointA.yFloat(),pointB.xFloat(),pointB.yFloat());
    }
}
