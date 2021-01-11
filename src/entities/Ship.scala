package entities

import graphics.{Main, ShipGraphic}
import gamemath.{Integrator, VectorD}
import processing.core.PApplet

import java.util

class Ship(override val width: Double,override  val height: Double,override  val MOI: Double,override  val mass: Double, val graphic: ShipGraphic) extends
  RadarObject (width: Double,height: Double, MOI: Double, mass: Double){
  private val integ = Integrator
  private var torque = 0.0
  private var netForce = VectorD(0,0)
  private val radarEmitter = new RadarEmitter()

  def moveTo(mx: Float, my: Float): Unit ={
    impulse(new VectorD(mx,my) - pos)
  }

  def rotationImpulse(rot: Double): Unit ={
    rotation = rotation + rot
  }

  def tick(radarObjectList: util.ArrayList[Ship],context: Main): Unit ={
    val deltaTime = 1.0/60.0

    acceleration = integ.stepAcceleration(netForce,mass)
    velocity = integ.stepVelocity(acceleration,velocity,deltaTime)
    pos = integ.stepPosition(velocity,pos,deltaTime);

    angularAcceleration = integ.stepAngularAcceleration(torque,MOI * mass)
    angularVelocity = integ.stepAngluarVelocity(angularAcceleration,angularVelocity,deltaTime)
    rotation = integ.stepRotation(angularVelocity,rotation,deltaTime)

    radarEmitter.update(radarObjectList,this.pos,context);
  }

  def draw(context: PApplet): Unit ={
    graphic.updatePos(this.pos,this.rotation)
    graphic.draw(context)
    //radarEmitter.draw(context)
  }
}
