package gamemath


case class VectorD(val x: Double, val y: Double) {

  def abs(): VectorD = {
    new VectorD(Math.abs(x), Math.abs(y))
  }

  def crossProduct(vec: VectorD): Double = {
    x * vec.y - y * vec.x
  }

  def dotProduct(vec: VectorD): Double = {
    x  * vec.x + y * vec.y
  }

  def scale(scalar: Double): VectorD = {
    new VectorD(x * scalar, y * scalar)
  }

  def +(translation: VectorD): VectorD = {
    new VectorD(x + translation.x, y + translation.y)
  }

  def +(translation: Double): VectorD = {
    this + new VectorD(translation,translation)
  }

  def -(translation: VectorD): VectorD = {
    new VectorD(x - translation.x, y - translation.y)
  }

  def difference(vec: VectorD): VectorD = {
    (this - vec).abs()
  }

  def negate(): VectorD = {
    new VectorD(-x,-y)
  }

  //Most likely incorrect
  def bearingTo(vector: VectorD): Double = {
    flippedAtan2(vector.y -y, vector.x - x)
  }

  def distanceTo(vector: VectorD): Double = {
    Math.abs(this.subtractBearing(vector))
  }

  def r (): Double = {
    return Math.sqrt((x * x) + (y * y))
  }

  def theta (): Double = {
    flippedAtan2(y,x)
  }

  def thetaDegrees(): Double = {
    theta() * (180 / Math.PI)
  }

  def subtractMagnitude(vector: VectorD): Double = {
    this.r() - vector.r()
  }

  def subtractBearing(vector: VectorD): Double = {
    return theta() - vector.theta()
  }

  def xFloat(): Float = {
    x.toFloat
  }

  def yFloat(): Float = {
    y.toFloat
  }


  def fromPolar(mag: Double, angleIn: Double): VectorD = {
    val flippedAngle: Double = flipAngle(angleIn)
    new VectorD(mag * Math.cos(flippedAngle), mag * Math.sin(flippedAngle))
  }

  def flipAngle(angle: Double): Double ={
    Math.PI / 2 - angle;
  }

  def flippedAtan2(y :Double, x: Double): Double = {
    val angle: Double = Math.atan2(y,x)
    val flippedAngle: Double = flipAngle(angle)
    if(flippedAngle >= 0) {
      return flippedAngle
    }
    else {
      return flippedAngle + 2 * Math.PI
    }
  }

  override def toString: String = {
    "X: " + x + "  | Y: " + y
  }
}