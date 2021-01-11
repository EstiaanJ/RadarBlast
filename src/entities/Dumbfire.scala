package entities

class Dumbfire(override val width: Double, override val length: Double, override val MOI: Double, override val mass: Double)
  extends MassObject (width: Double,length: Double, MOI: Double, mass: Double){

  private val fuseRadius = 1000;
  private val explosiveRadius = 1000;
  private val explosiveDamage = 1000;


  def checkFuseRadius(ship: Ship): Unit ={
    val distance = ship.getPos().distanceTo(this.pos)
    if(distance < fuseRadius){
      if(distance < explosiveRadius){
        val damage = explosiveDamage/distance
        ship.doDamage(damage)
      }
    }
  }

}
