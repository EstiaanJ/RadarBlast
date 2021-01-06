package gamemath


class Ray(val origin: VectorD, val angle: Double, val radius: Double) {


  def destination(): VectorD = {
    val postDest = VectorD(0,0).fromPolar(radius,angle)
    return postDest + origin
  }

  def intersectionCircle(circlePos: VectorD): Double = {
    distance(this.origin,this.destination(),circlePos)
  }



  def distance(origin: VectorD, destination: VectorD, targetPos: VectorD): Double ={
    Math.abs((destination.x-origin.x)*targetPos.x + (origin.y - destination.y)*targetPos.y +
      (origin.x - destination.x)*origin.y + origin.x*(destination.y - origin.y))/
      Math.sqrt((destination.x - origin.x)*(destination.x - origin.x) +
        (origin.y - destination.y)*(origin.y - destination.y))
  }

}
