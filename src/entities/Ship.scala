package entities

import graphics.{Main, ShipGraphic}
import gamemath.{Integrator, VectorD}
import processing.core.PApplet

import java.util

class Ship(override val width: Double, override  val length: Double, override  val MOI: Double, override  val mass: Double, val graphic: ShipGraphic) extends
  MassObject (width: Double,length: Double, MOI: Double, mass: Double){
  private val integ = Integrator
  private var torque = 0.0
  private var netForce = VectorD(0,0)
  private val radarEmitter = new RadarEmitter()
  private var enginePower = 0.0;
  private var turnRate = 0.0

  def moveToDep(mx: Float, my: Float): Unit ={
    impulse(new VectorD(mx,my) - pos)
  }

  def rotationImpulse(rot: Double): Unit ={
    rotation = rotation + rot
  }

  def tick(radarObjectList: util.ArrayList[Ship],context: Main): Unit ={
    val deltaTime = 1.0/60.0
    val engineForce = new VectorD(0,0).fromPolar(enginePower,this.rotation)
    //val friction = ((0.25 * (length + mass + MOI)) + width) * 5

    netForce = engineForce
    acceleration = integ.stepAcceleration(netForce,mass)
    velocity = integ.stepVelocity(acceleration,velocity,deltaTime).scale(0.99)
    pos = integ.stepPosition(velocity,pos,deltaTime);

    angularAcceleration = integ.stepAngularAcceleration(torque,MOI * mass)
    angularVelocity = integ.stepAngluarVelocity(angularAcceleration,angularVelocity,deltaTime)
    rotation = integ.stepRotation(angularVelocity,rotation,deltaTime)

    radarEmitter.update(radarObjectList,this.pos,context);
    //netForce = new VectorD(0,0)
  }

  def engineForceAdd(force: Double): Unit = {
    enginePower = enginePower + force
  }

  def getEnginePower(): Double ={
    this.enginePower
  }


  def draw(context: PApplet): Unit ={
    graphic.updatePos(this.pos,this.rotation)
    graphic.draw(context)
    //radarEmitter.draw(context)
  }
}
