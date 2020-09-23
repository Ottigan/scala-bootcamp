package com.evolutiongaming.bootcamp.basics

object ClassesAndTraits {
  // You can follow your progress using the tests in `ClassesAndTraitsSpec`.

  // Classes in Scala are blueprints for creating objects. They can contain methods, values, variables,
  // types, objects, traits, and classes which are collectively called members.

  class MutablePoint(var x: Double, var y: Double) {
    def move(dx: Double, dy: Double): Unit = {
      x = x + dx
      y = y + dy
    }

    override def toString: String =
      s"($x, $y)"
  }

  val point1 = new MutablePoint(3, 4)
  println(point1.x) // 3.0
  println(point1) // (3.0, 4.0)

  // Question. Is MutablePoint a good design? Why or why not?

  // Traits define a common interface that classes conform to. They are similar to Java's interfaces.
  // Classes and objects can extend traits but traits cannot be instantiated and therefore have no parameters.

  // Subtyping
  // Where a given trait is required, a subtype of the trait can be used instead.

  sealed trait Shape extends Located with Bounded

  sealed trait Located {
    def x: Double
    def y: Double
  }

  sealed trait Bounded {
    def minX: Double
    def maxX: Double
    def minY: Double
    def maxY: Double
  }

  final case class Point(x: Double, y: Double) extends Shape {
    override def minX: Double = x
    override def maxX: Double = x
    override def minY: Double = y
    override def maxY: Double = y
  }

  final case class Circle(centerX: Double, centerY: Double, radius: Double) extends Shape {
    override def x: Double = centerX
    override def y: Double = centerY
    override def minX: Double = x - radius
    override def maxX: Double = x + radius
    override def minY: Double = y - radius
    override def maxY: Double = y + radius
  }

  final case class Rectangle(centerX: Double, centerY: Double, width: Double, height: Double) extends Shape {
    override def x: Double = centerX
    override def y: Double = centerY
    override def minX: Double = x - width / 2
    override def maxX: Double = x + width / 2
    override def minY: Double = y - height / 2
    override def maxY: Double = y + height / 2
  }

  // Case Classes
  //
  // Case classes are like regular classes, but with extra features which make them good for modelling
  // immutable data. They have all the functionality of regular classes, but the compiler generates additional
  // code:
  // - Case class constructor parameters are public `val` fields, publicly accessible
  // - `apply` method is created in the companion object, so you don't need to use `new` to create a new
  //   instance of the class
  // - `unapply` method which allows you to use case classes in `match` expressions (pattern matching)
  // - a `copy` method is generated
  // - `equals` and `hashCode` methods are generated, which let you compare objects & use them in collections
  // - `toString` method is created for easier debugging purposes

  val point2 = Point(1, 2)
  println(point2.x)

  val shape: Shape = point2
  val point2Description = shape match {
    case Point(x, y) => s"x = $x, y = $y"
    case _ => "other shape"
  }

  val point3 = point2.copy(x = 3)
  println(point3.toString) // Point(3, 2)

  // Exercise. Implement an algorithm for finding the minimum bounding rectangle
  // (https://en.wikipedia.org/wiki/Minimum_bounding_rectangle) for a set of `Bounded` objects.
  //
  def minimumBoundingRectangle1(objects: Set[Bounded]): Bounded = {
    new Bounded {
      implicit private val doubleOrdering: Ordering[Double] = Ordering.Double.IeeeOrdering

      // if needed, fix the code to be correct
      override def minX: Double = objects.map(_.minX).min
      override def maxX: Double = objects.map(_.maxX).max
      override def minY: Double = objects.map(_.minY).min
      override def maxY: Double = objects.map(_.maxY).max
    }
  }
  def minimumBoundingRectangle(objects: Set[Bounded]): Rectangle = {
    implicit val doubleOrdering: Ordering[Double] = Ordering.Double.IeeeOrdering

    def minX: Double = objects.map(_.minX).min
    def maxX: Double = objects.map(_.maxX).max
    def minY: Double = objects.map(_.minY).min
    def maxY: Double = objects.map(_.maxY).max
    val width: Double = maxX - minX
    val height: Double = maxY - minY
    val x: Double = maxX - width / 2
    val y: Double = maxY - height / 2

    Rectangle(x, y, width, height)
  }

  // Pattern matching and exhaustiveness checking
  def describe(x: Shape): String = x match {
    case Point(x, y) => s"Point(x = $x, y = $y)"
    case Circle(centerX, centerY, radius) => s"Circle(centerX = $centerX, centerY = $centerY, radius = $radius)"
    case Rectangle(centerX, centerY, width, height) => s"Rectangle(centerX = $centerX, centerY = $centerY, width = $width, height = $height)"
  }

  // Exercise. Add another Shape class called Rectangle and check that the compiler catches that we are
  // missing code to handle it in `describe`.

  // Exercise. Change the implementation of `minimumBoundingRectangle` to return a `Rectangle` instance.
  // What are the pros & cons of each implementation?

  // Exercise. The tests for `minimumBoundingRectangle` in `ClassesAndTraitsSpec` are insufficient.
  // Improve the tests.

  // Generic classes and type parameters

  // In a similar way as we saw with polymorphic methods, classes and traits can also take type parameters.
  // For example, you can define a Stack[A] which works with any type of element A.
  final case class Stack[A](elements: List[A] = Nil) {
    def push(x: A): Stack[A] = Stack(x :: elements)
    def peek: Option[A] = elements.headOption
    def pop: Option[(A, Stack[A])] = peek map { x =>
      (x, Stack(elements.tail))
    }
  }
}
