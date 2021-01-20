package entities

import graphics.{Main, ShipGraphic}
import gamemath.{Integrator, VectorD}
import processing.core.PApplet

import java.util

class Ship(override val width: Double, override  val length: Double, override  val MOI: Double, override  val mass: Double, val graphic: ShipGraphic, val playerShip: Boolean) extends
  MassObject (width: Double,length: Double, MOI: Double, mass: Double){

  private val radarEmitter = new RadarEmitter()
  private var enginePower = 0.0;
  private var turnRate = 0.0

  def moveToDep(mx: Float, my: Float): Unit ={
    impulse(new VectorD(mx,my) - pos)
  }

  def rotationImpulse(rot: Double): Unit ={
    rotation = rotation + rot
  }

  def tick(massObjects: util.ArrayList[MassObject]): Unit ={
    val deltaTime = 1.0/60.0
    val engineForce = VectorD(0,0).fromPolar(enginePower,this.rotation)
    //val friction = ((0.25 * (length + mass + MOI)) + width) * 5

    this.netForce = engineForce
    this.torque = turnRate
    super.tick();

    if(playerShip) radarEmitter.update(massObjects,this.pos);
  }

  def enginePowerAdd(force: Double): Unit = {
    if(enginePower > Main.MAX_POWER) {
      if(force < 0){
        enginePower = enginePower + force
      }
    } else if (enginePower < -Main.MAX_POWER) {
      if(force > 0){
        enginePower = enginePower + force
      }
    } else {
      enginePower = enginePower + force
    }


  }

  def getEnginePower(): Double ={
    this.enginePower
  }

  def zeroEnginePower(): Unit ={
    enginePower = 0;
  }

  def turnRateAdd(added: Double): Unit ={
    if(turnRate > 0.2){
      if(added < 0){
        turnRate += added
      }
    } else if( turnRate < -0.2) {
      if(added > 0){
        turnRate += added
      }
    } else {
      turnRate += added
    }
  }

  def getTurnRate(): Double ={
    this.turnRate
  }

  def zeroTurn(): Unit ={
    turnRate = 0
  }

  def draw(context: Main): Unit ={
    graphic.updatePos(this.pos,this.rotation)
    graphic.draw(context)
    radarEmitter.draw(context)
    //radarEmitter.draw(context)
  }
}
