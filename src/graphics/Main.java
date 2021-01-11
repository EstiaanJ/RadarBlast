package graphics;

import gamemath.Raycasting.Boundary;
import gamemath.Raycasting.Raycast;
import entities.RadarObject;
import entities.Ship;
import gamemath.VectorD;
import processing.core.PApplet;
import processing.event.MouseEvent;


import java.util.ArrayList;

public class Main extends PApplet {
    static ArrayList<Ship> radarObjects = new ArrayList<Ship>();
    static ArrayList<RadarPing> radarPings = new ArrayList<RadarPing>();
    public float scalingFactor = 1;

    Ship player;

    Ship testO = new Ship(10,100,3,100,new ShipGraphic());


    public static void main(String[] args) {
        PApplet.main(new String[]{"graphics.Main"});

    }

    public void settings(){
        size(1000,1000);
    }

    public void setup(){
        player = new Ship(100,10,3,100,new ShipGraphic());
        player.setPos(new VectorD(100,100));
        testO.setPos(new VectorD(700,600));
        radarObjects.add(testO);
    }

    public void draw(){
        background(0);

        text("Sc :" + scalingFactor, mouseX, mouseY - 24);
        text("X: " + mx(),mouseX,mouseY - 12);
        text("Y: " + my(),mouseX,mouseY);

        pushMatrix();
        scale(scalingFactor);
        player.tick(radarObjects,this);
        player.draw(this);

        for(int i = 0; i < radarPings.size(); i++){
            RadarPing ping = radarPings.get(i);
            ping.draw(this);
            if(ping.age > RadarPing.MAX_AGE) { radarPings.remove(i);}
        }
        popMatrix();

    }

    public void mouseReleased(){
        player.moveTo(mouseX,mouseY);
    }

    public void mouseWheel(MouseEvent event){

        if(scalingFactor > 0.1){
            scalingFactor += (0.1* event.getCount());
        } else if (scalingFactor > 0.01 && scalingFactor <= 0.1) {
            scalingFactor += (0.01 * event.getCount());
        } else {
            if(event.getCount() > 0) {
                scalingFactor += 0.01f;
            } else {
                scalingFactor = 0.01f;
            }

        }
    }


    public static void addRadarPing(RadarPing ping){
        radarPings.add(ping);
    }

    public float mx(){
        return mouseX / scalingFactor;
    }

    public float my(){
        return mouseY / scalingFactor;
    }
}
