package entities;

import gamemath.Constants;
import gamemath.Integrator;
import gamemath.Raycasting.Boundary;
import gamemath.Raycasting.Raycast;
import gamemath.VectorD;
import graphics.Main;
import graphics.RadarBeamGraphic;
import graphics.RadarPing;
import processing.core.PApplet;

import java.util.ArrayList;

public class RadarEmitter {
    private Integrator integ;
    private double rotationRate = 0.0003;
    private double currentAngle = 0;
    private ArrayList<MassObject> massObjects;
    private RadarBeamGraphic radarBeamGraphic;
    private Raycast ray;
    
    public RadarEmitter(){
        radarBeamGraphic = new RadarBeamGraphic();
    }
    
    public void update(ArrayList<MassObject> radarObjects, VectorD radarOrigin) {
        double deltaTime = 1.0/60.0;

        currentAngle = integ.stepRotation(rotationRate,currentAngle,deltaTime);

        ray = new Raycast(radarOrigin,new VectorD(0,0).fromPolar(Constants.RADAR_DISTANNCE,currentAngle));
        radarBeamGraphic.updatePos(ray.origin(),ray.direction());



        for (MassObject radObj: radarObjects) {
            for (Boundary bound:radObj.getBoundaries()) {
                //bound.draw(this);
                RadarPing ping = ray.cast(bound);
                if(ping != null) {Main.addRadarPing(ping);}
            }
        }
    }

    public void draw(Main context){
        ray.draw(context);
    }
}
