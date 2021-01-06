package graphics;

import gamemath.Raycasting.Boundary;
import gamemath.Raycasting.Raycast;
import entities.RadarObject;
import entities.Ship;
import gamemath.VectorD;
import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    static ArrayList<Ship> radarObjects = new ArrayList<Ship>();
    static ArrayList<RadarPing> radarPings = new ArrayList<RadarPing>();

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
        radarObjects.add(player);
    }

    public void draw(){
        background(0);

        text("X: " + mouseX,mouseX,mouseY - 12);
        text("Y: " + mouseY, mouseX,mouseY);

        player.tick(radarObjects,this);
        player.draw(this);




        for(int i = 0; i < radarPings.size(); i++){
            RadarPing ping = radarPings.get(i);
            ping.draw(this);
            if(ping.age > RadarPing.MAX_AGE) { radarPings.remove(i);}
        }

    }

    public void mouseReleased(){
        player.moveTo(mouseX,mouseY);
    }


    public static void addRadarPing(RadarPing ping){
        radarPings.add(ping);
    }
}
