package graphics;

import entities.Ship;
import gamemath.VectorD;
import processing.core.PApplet;
import processing.event.MouseEvent;


import java.util.ArrayList;

public class Main extends PApplet {
    public static final double MAX_TURN_RATE = 0.3;
    public static final int MAX_POWER = 4000;

    static ArrayList<Ship> radarObjects = new ArrayList<Ship>();
    static ArrayList<RadarPing> radarPings = new ArrayList<RadarPing>();
    public float scalingFactor = 1;

    Ship player;

    Ship testO = new Ship(10,100,3,100,new ShipGraphic());
    Ship test1 = new Ship(10,100,3,100,new ShipGraphic());


    public static void main(String[] args) {
        PApplet.main(new String[]{"graphics.Main"});

    }

    public void settings(){
        size(1000,1000);
    }

    public void setup(){
        player = new Ship(14,100,3000,3000,new ShipGraphic());
        player.setPos(new VectorD(0,0));
        //player.impulse(new VectorD(100,0));
        testO.setPos(new VectorD(7000,6000));
        test1.setPos(new VectorD(7000,19000));
        radarObjects.add(testO);
        radarObjects.add(test1);
        //radarObjects.add(player);
    }

    public void draw(){
        background(0);

        text("Engine Power: " + player.getEnginePower(), 10,100);
        text("Ship Speed " + round((float)player.velocity().r()),10,112);

        //text("Sc :" + scalingFactor, mouseX, mouseY - 24);
        text("X: " + mx(),mouseX,mouseY - 12);
        text("Y: " + my(),mouseX,mouseY);

        pushMatrix();

        //scale(scalingFactor);
        translate(-player.pos().xFloat()+500,-player.pos().yFloat()+500);
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
        Ship s = new Ship(10,100,1000,100,new ShipGraphic());
        s.setPos(new VectorD(mx(),my()));
        radarObjects.add(s);
        //player.moveTo(mouseX,mouseY);
    }

    public void keyPressed(){
        switch(key){
            case'w':
                playerFaster();
                break;
            case 's':
                playerSlower();
                break;
            case 'a':
                playerTurnLeftMore();
                break;
            case 'd':
                playerTurnRightMore();
                break;
            case 'q':
                playerResetTurn();
                break;
            case 'e':
                playerZeroEngine();
                break;
        }
    }

    public void playerFaster(){
        player.enginePowerAdd(1000);
    }

    public void playerSlower(){
        player.enginePowerAdd(-1000);
    }

    public void playerTurnLeftMore(){
        player.turnRateAdd(0.04);
    }

    public void playerTurnRightMore(){
        player.turnRateAdd(-0.04);
    }

    public void playerResetTurn(){
        player.zeroTurn();
    }

    public void playerZeroEngine(){
        player.zeroEnginePower();
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
