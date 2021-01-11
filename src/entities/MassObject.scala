package entities

import gamemath.Raycasting.Boundary
import gamemath.VectorD

import java.util

class MassObject(val width: Double, val length: Double, val MOI: Double, val mass: Double){
  protected var pos = VectorD(0,0)
  protected var velocity = VectorD(0,0)
  protected var acceleration = VectorD(0,0)
  protected var angularAcceleration = 0.0
  protected var angularVelocity = 0.0
  protected var rotation = 0.0
  private var health = 8000.0
  var alive = true


  def setPos(newPos: VectorD): Unit ={
    pos = VectorD(newPos.x,newPos.y)
  }

  def impulse(vector: VectorD): Unit ={
    velocity = velocity + VectorD(vector.x,vector.y)
  }

  def rotatePoint(point: VectorD, centerX: Double, centerY: Double): VectorD ={
    val tempX = point.x - centerX
    val tempY = point.y - centerY

    val rotatedX: Double = tempX * Math.cos(this.rotation) - tempY * Math.sin(this.rotation)
    val rotatedY: Double = tempX * Math.sin(this.rotation) - tempY * Math.cos(this.rotation)

    VectorD(rotatedX + centerX, rotatedY + centerY)
  }

  def getBoundaries(): util.ArrayList[Boundary] = {
    val a = rotatePoint((VectorD(pos.x+0.5*width,pos.y-0.5*length)),pos.x,pos.y)
    val b = rotatePoint((VectorD(pos.x+0.5*width,pos.y+0.5*length)),pos.x,pos.y)
    val c = rotatePoint((VectorD(pos.x-0.5*width,pos.y+0.5*length)),pos.x,pos.y)
    val d = rotatePoint((VectorD(pos.x-0.5*width,pos.y-0.5*length)),pos.x,pos.y)

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

  def getPos(): VectorD = {
    this.pos.copy()
  }

  def doDamage(damage: Double): Unit ={
    health = health - damage
    if(health < 0){
      alive = false
    }
  }

}
