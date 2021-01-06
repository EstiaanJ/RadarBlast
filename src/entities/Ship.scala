package entities

import gamemath.Raycasting.Boundary
import graphics.ShipGraphic
import gamemath.{Integrator, VectorD}
import processing.core.PApplet

import java.util

class Ship(val width: Double, val height: Double, val MOI: Double, val mass: Double, val graphic: ShipGraphic) {
  private val integ = Integrator
  private var torque = 0.0
  private var netForce = VectorD(0,0)
  private var pos = VectorD(0,0)
  private var velocity = VectorD(0,0)
  private var acceleration = VectorD(0,0)
  private var angularAcceleration = 0.0
  private var angularVelocity = 0.0
  private var rotation = 0.0
  private val radarEmitter = new RadarEmitter()

  def setPos(newPos: VectorD): Unit ={
    pos = VectorD(newPos.x,newPos.y)
  }

  def moveTo(mx: Float, my: Float): Unit ={
    impulse(new VectorD(mx,my) - pos)
  }

  def impulse(vector: VectorD): Unit ={
    velocity = velocity + VectorD(vector.x,vector.y)
  }

  def rotationImpulse(rot: Double): Unit ={
    rotation = rotation + rot
  }

  def getBoundaries(): util.ArrayList[Boundary] = {
    val a = rotatePoint((VectorD(pos.x+0.5*width,pos.y-0.5*height)),pos.x,pos.y)
    val b = rotatePoint((VectorD(pos.x+0.5*width,pos.y+0.5*height)),pos.x,pos.y)
    val c = rotatePoint((VectorD(pos.x-0.5*width,pos.y+0.5*height)),pos.x,pos.y)
    val d = rotatePoint((VectorD(pos.x-0.5*width,pos.y-0.5*height)),pos.x,pos.y)

    val boundAB = new Boundary(a,b)
    val boundBC = new Boundary(b,c)
    val boundCD = new Boundary(c,d)
    val boundDA = new Boundary(d,a)

    var bList: util.ArrayList[Boundary] = new util.ArrayList()
    bList.add(boundAB)
    bList.add(boundBC)
    bList.add(boundCD)
    bList.add(boundDA)

    return bList
  }

  def rotatePoint(point: VectorD, centerX: Double, centerY: Double): VectorD ={
    val tempX = point.x - centerX
    val tempY = point.y - centerY

    val rotatedX: Double = tempX * Math.cos(this.rotation) - tempY * Math.sin(this.rotation)
    val rotatedY: Double = tempX * Math.sin(this.rotation) - tempY * Math.cos(this.rotation)

    VectorD(rotatedX + centerX, rotatedY + centerY)
  }

  def tick(radarObjectList: util.ArrayList[Ship],context: PApplet): Unit ={
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
