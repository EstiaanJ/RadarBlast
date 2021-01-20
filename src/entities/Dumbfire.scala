package entities

import graphics.Main

class Dumbfire(override val width: Double, override val length: Double, override val MOI: Double, override val mass: Double)
  extends MassObject (width: Double,length: Double, MOI: Double, mass: Double){

  private val fuseRadius = 100;
  private val explosiveRadius = 100;
  private val explosiveDamage = 18000;





  def checkFuseRadius(mass: MassObject,context: Main): Boolean ={
    context.circle(this.pos.xFloat(),this.pos.yFloat(),10)
    var didExplode = false;
    val distance = pos.distanceTo(mass.getPos())
    if(distance < fuseRadius){
      if(distance < explosiveRadius){
        val damage = explosiveDamage-distance
        mass.doDamage(damage)
        this.alive = false
        //System.out.println("BANG!: " + distance)
        didExplode =true
      }
    }
    return didExplode
  }

}
