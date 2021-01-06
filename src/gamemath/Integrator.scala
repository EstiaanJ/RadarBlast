package gamemath

object Integrator {
  def stepPosition(velocity: VectorD, pos: VectorD, time: Double): VectorD = {
    pos + (velocity.scale(time))
  }

  def stepVelocity(acceleration: VectorD, velocity: VectorD, time: Double): VectorD = {
    velocity + (acceleration.scale(time))
  }

  def stepAcceleration(netForce: VectorD, mass: Double): VectorD = {
    VectorD(netForce.x/mass,netForce.y/mass)
  }


  def stepRotation(angularVelocity: Double, rotation: Double, time: Double): Double = {
    rotation + (angularVelocity/time)
  }

  def stepAngluarVelocity(angularAcceleration: Double, angularVelocity: Double, time: Double): Double = {
    angularVelocity + (angularAcceleration/time)
  }

  def stepAngularAcceleration(torque: Double, momentOfInertia: Double): Double = {
    torque/momentOfInertia
  }

  def getPosition(netForce: VectorD, mass: Double, lastVelocity: VectorD, lastPos: VectorD, time: Double): VectorD = {
    stepPosition(stepVelocity(stepAcceleration(netForce,mass),lastVelocity,time),lastPos,time)
  }

  def getRotation(torque: Double, momentOfInertia: Double, lastAV: Double, lastPos: Double, time: Double): Double = {
    stepRotation(stepAngluarVelocity(stepAngularAcceleration(torque,momentOfInertia),lastAV,time),lastPos,time)
  }

  /*
    def getPositionReal(netForce: VectorD, mass: Double, lastVelocity: VectorD, lastPos: VectorD, time: Double): VectorD = {
      stepPosition(stepVelocity(stepAcceleration(netForce,mass),lastVelocity,time),lastPos,time)
    }
    */

}
