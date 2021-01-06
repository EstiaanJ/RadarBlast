package gamemath.Raycasting

import gamemath.VectorD
import graphics.RadarPing
import processing.core.PApplet

class Raycast(val origin: VectorD, val direction: VectorD) {

  def draw(context: PApplet): Unit ={
    context.stroke(0,255,0);
    context.line(this.origin.xFloat(),this.origin.yFloat(),direction.xFloat(),direction.yFloat())
  }

  def cast(boundary: Boundary): RadarPing = {
    var ping: RadarPing = null;

    val x1 = boundary.pointA.x
    val y1 = boundary.pointA.y
    val x2 = boundary.pointB.x
    val y2 = boundary.pointB.y

    val x3 = this.origin.x
    val y3 = this.origin.y
    val x4 = this.direction.x
    val y4 = this.direction.y

    val denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)

    if(denom == 0){
      ping = null
    } else {
      val t = ((x1-x3) * (y3 - y4) - (y1 - y3) * (x3 - x4) )/denom
      val u = -((x1-x2) * (y1 - y3) - (y1 - y2) * (x1 - x3) )/denom

      if(t > 0 && t < 1 && u > 0) {
        val colPoint = new VectorD(x1 + t *(x2 - x1),y1 + t *(y2 - y1))
        ping = createRadarPing(colPoint,1,1)
      } else {
        ping = null
      }
    }
    return ping
  }

  def createRadarPing(pingPos: VectorD, pingDistance: Double, pingOpacity: Double): RadarPing ={
    new RadarPing(pingPos,pingOpacity/pingDistance)
  }

}
