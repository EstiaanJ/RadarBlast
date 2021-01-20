package graphics;

import entities.Dumbfire;
import entities.MassObject;
import entities.Ship;
import gamemath.VectorD;
import processing.core.PApplet;
import processing.event.MouseEvent;


import java.util.ArrayList;

public class Main extends PApplet {
    public static final double MAX_TURN_RATE = 0.3;
    public static final int MAX_POWER = 8000;
    public static final int viewWidth = 1000;
    public static final int viewHeight = 1000;

    static ArrayList<MassObject> massbjects = new ArrayList<MassObject>();
    static ArrayList<RadarPing> radarPings = new ArrayList<RadarPing>();
    ArrayList<Dumbfire> missiles = new ArrayList<Dumbfire>();
    public float scalingFactor = 1;

    Ship player;

    Ship testO = new Ship(10,100,3,100,new ShipGraphic(),false);
    Ship test1 = new Ship(10,100,3,100,new ShipGraphic(),false);


    public static void main(String[] args) {
        PApplet.main(new String[]{"graphics.Main"});

    }

    public void settings(){
        size(viewWidth,viewHeight);
    }

    public void setup(){
        player = new Ship(14,100,3000,3000,new ShipGraphic(),true);
        player.setPos(new VectorD(0,0));
        //player.impulse(new VectorD(100,0));
        testO.setPos(new VectorD(1000,600));
        testO.impulse(new VectorD(1,100));
        test1.setPos(new VectorD(700,190));
        massbjects.add(testO);
        massbjects.add(test1);
        //radarObjects.add(player);
    }

    public void draw(){
        background(0);

        text("Engine Power: " + player.getEnginePower(), 10,100);
        text("Ship Speed " + round((float)player.velocity().r()),10,112);
        player.tick(massbjects);
        testO.tick(massbjects);

        text("Sc :" + scalingFactor, mouseX, mouseY - 24);
        text("absolute X: " + absoluteX(),mouseX,mouseY - 12);
        text("absolute Y: " + absoluteY(),mouseX,mouseY);


        scale(scalingFactor);


        translate(-player.pos().xFloat()+((viewWidth/2)*(1/scalingFactor)),-player.pos().yFloat()+((viewHeight/2)*(1/scalingFactor)));
        //rotate((float)player.rotation());
        player.draw(this);

        for(int i = 0; i < radarPings.size(); i++){
            RadarPing ping = radarPings.get(i);
            ping.draw(this);
            if(ping.age > RadarPing.MAX_AGE) { radarPings.remove(i);}
        }

        for(int i = 0; i < missiles.size(); i++){
            Dumbfire missile = missiles.get(i);
            missile.tick();
            for (MassObject mass: massbjects) {
                if(mass != missile){missile.checkFuseRadius(mass,this); }
            }
            if(!missile.alive()){
                missiles.remove(i);
            }
        }

        for(int i = 0; i < massbjects.size(); i++){
            MassObject massObj = massbjects.get(i);
            if(!massObj.alive()){
                massbjects.remove(i);
            }
        }

    }

    public void mouseReleased(){
        //Ship sip = new Ship(10,100,1000,100,new ShipGraphic(),false);
        Dumbfire s = new Dumbfire(3,16,1,0.8);
        s.setPos(player.getPos());
        VectorD directVec = new VectorD(absoluteX(),absoluteY());
        double direction = player.getPos().bearingTo(directVec);
        VectorD velVec = new VectorD(0,0).fromPolar(1000,direction);
        s.impulse(velVec);
        massbjects.add(s);
        missiles.add(s);
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
        player.enginePowerAdd(200);
    }

    public void playerSlower(){
        player.enginePowerAdd(-200);
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

    private float xCorrection(){
        return -player.pos().xFloat()+((viewWidth/2)*(1/scalingFactor));
    }

    private float yCorrection(){
        return -player.pos().yFloat()+((viewHeight/2)*(1/scalingFactor));
    }

    public float absoluteX(){
        return ((mouseX/scalingFactor - xCorrection()));
    }

    public float absoluteY(){
        return ((mouseY/scalingFactor - yCorrection()));
    }
}
