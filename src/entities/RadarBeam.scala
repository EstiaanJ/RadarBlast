package entities

import gamemath.{Ray, VectorD}
import graphics.RadarPing

import java.util

class RadarBeam(override val origin: VectorD, override val angle: Double,override val radius: Double) extends Ray(origin,angle,radius) {

  def checkForReflection(radarObject: RadarObject): RadarPing={
    if(this.intersectionCircle(radarObject.pos) < radarObject.radius){
      createRadarPing(radarObject.pos,0.1,radarObject.radarOpaccity)
    } else {
      null
    }
  }

  def createRadarPing(pingPos: VectorD, pingDistance: Double, pingOpacity: Double): RadarPing ={
    new RadarPing(pingPos,pingOpacity/pingDistance)
  }


}
