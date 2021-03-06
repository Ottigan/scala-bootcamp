package com.evolutiongaming.bootcamp.basics

import java.io.FileNotFoundException

import scala.annotation.tailrec
import scala.io.Source
import scala.util.{Failure, Success, Try}

object ControlStructures {
  // You can follow your progress using the tests in `ControlStructuresSpec`.
  //   sbt "testOnly com.evolutiongaming.bootcamp.basics.ControlStructuresSpec"

  // The if-then-else construct is as follows:
  //
  // val result =
  //   if (boolean1) {
  //     result1
  //   } else if (boolean2) {
  //     result2
  //   } else {
  //     otherResult
  //   }
  //
  // Note that it returns a result value.

  // Note that curly braces can be omitted
  //  val result =
  //    if (boolean1) result1
  //    else if (boolean2) result2
  //    else otherResult

  // Exercise. Implement a "Fizz-Buzz" https://en.wikipedia.org/wiki/Fizz_buzz function using the if-then-else,
  // returning "fizzbuzz" for numbers which divide with 15, "fizz" for those which divide by 3 and "buzz" for
  // those which divide with 5, and returning the input number as a string for other numbers:
  def fizzBuzz1(n: Int): String = {
    if (n % 15 == 0) "fizzbuzz"
    else if (n % 5 == 0) "buzz"
    else if (n % 3 == 0) "fizz"
    else n.toString
  }

  // Pattern Matching
  //
  // Using the match-case construct we can write constructs equivalent to if-then-else statements in, often,
  // a more readable and concise form:
  //
  // val result = someValue match {
  //    case pattern1                       => result1
  //    case pattern2 if (guardCondition)   => result2
  //    case _                              => fallbackResult
  // }

  type ErrorMessage = String

  def monthName1(x: Int): Either[ErrorMessage, String] = {
    val names = Array("Jan", "Feb", "...")
    names.lift(x - 1).toRight(if (x > 12) "too large" else "too small")
  }

  def monthName(x: Int): Either[ErrorMessage, String] = {
    x match {
      case 1           => Right("January")
      case 2           => Right("February")
      case 3           => Right("March")
      case 4           => Right("April")
      case 5           => Right("May")
      case 6           => Right("June")
      case 7           => Right("July")
      case 8           => Right("August")
      case 9           => Right("September")
      case 10          => Right("October")
      case 11          => Right("November")
      case 12          => Right("December")
      case x if x <= 0 => Left(s"Month $x is too small")
      case x           => Left(s"Month $x is too large")
    }
  }

  // Question. How would you improve `monthName`?
  // Question. What would you use in its place if you wanted to more properly handle multiple locales?

  // Exercise. Implement a "Fizz-Buzz" function using pattern matching:
  def fizzBuzz2(x: Int): String = {
    x match {
      case x if x % 15 == 0 => "fizzbuzz"
      case x if x % 5 == 0  => "buzz"
      case x if x % 3 == 0  => "fizz"
      case x                => s"$x"
    }
  }

  def fizzBuzz3(x: Int): String = (x % 5, x % 3) match {
    case (0, 0) => "fizzbuzz"
    case (0, _) => "buzz"
    case (_, 0) => "fizz"
    case _      => s"$x"
  }

  // Recursion
  //
  // A function which calls itself is called a recursive function. This is a commonly used way how to
  // express looping constructs in Functional Programming languages.

  def sum1(list: List[Int]): Int = {
    if (list.isEmpty) 0
    else list.head + sum1(list.tail)
  }

  // Question. What are the risks of List#head and List#tail? How can you refactor `sum1` to avoid these invocations?

  def sumMatch(list: List[Int]): Int = list match {
    case Nil     => 0
    case x :: xs => x + sumMatch(xs)
  }

  // Question. What are the risks of recursion when applied without sufficient foresight?

  // @tailrec annotation verifies that a method will be compiled with tail call optimisation.
  @tailrec
  def last[A](list: List[A]): Option[A] = list match {
    case Nil      => None
    case x :: Nil => Some(x)
    case _ :: xs  => last(xs)
  }

  // Recursion isn't used that often as it can be replaced with `foldLeft`, `foldRight`,
  // `reduce` or other larger building blocks.

  def sum2(list: List[String]): String = list.foldLeft("")((acc, x) => acc + x)

  def sum3(list: List[String]): String = list.foldRight("")((x, acc) => acc + x)

  def sum4(list: List[String]): String = {
    if (list.isEmpty) "Empty" // reduce will crash for empty List
    else list.reduce((a, b) => a + b)
  }

  def sum5(list: List[Int]): Int = list.sum // only for Numeric lists

  // Exercise: Implement a function `applyNTimes` which takes a function `f` and an integer `n` and
  // returns a function which applies the function `f` `n` times.
  //
  // Thus `applyNTimesForInts(_ + 1, 4)(3)` should return `((((3 + 1) + 1) + 1) + 1)` or `7`.
  def applyNTimesForInts(f: Int => Int, n: Int): Int => Int = { x: Int =>
    @tailrec
    def helper(x: Int, n: Int): Int = {
      if (n == 1) f(x)
      else helper(f(x), n - 1)
    }

    helper(f(x), n - 1)
  }

  // Exercise: Convert the function `applyNTimesForInts` into a polymorphic function `applyNTimes`:
  def applyNTimesAlt[A](f: A => A, n: Int): A => A = { x: A =>
    @tailrec
    def helper(x: A, n: Int): A = {
      if (n == 1) f(x)
      else helper(f(x), n - 1)
    }

    helper(f(x), n - 1)
  }

  def applyNTimes[A](f: A => A, n: Int): A => A = { x: A =>
    if (n == 0) x
    else applyNTimes[A](f, n - 1)(f(x))
  }

  // `map`, `flatMap` and `filter` are not control structures, but methods that various collections (and
  // not only collections) have. We will discuss them now as they are important to understand a key
  // control structure called "for comprehensions".

  // `map` is a higher order function which - in case of collections - transforms each element of the
  // collection into a different element (and returns the resulting collection)

  // For example, for `List` it is defined as
  object list_map_example { // name-spacing to not break other code in this lesson
    class List[A] {
      def map[B](f: A => B): List[B] = ???
    }
  }

  // Question. What is the value of this code?
  val listMapExample = List(1, 2, 3).map(x => x * 2)

  // As we will see in later lessons, `map` is a method that `Functor`-s have, and there are more `Functor`-s
  // than just collections (`IO`, `Future`, `Either`, `Option` are all `Functor`-s too).

  // For now, we will have a utilitarian focus and not go into `Functor`-s and other type classes.

  // `flatMap` is a higher order function which - for collections - transforms each element of the collection
  // into a collection, and then `flatten`-s these collections.

  // For example, for `List` it could be defined as:
  object list_flatmap_example { // name-spacing to not break other code in this lesson
    class List[A] {
      def flatMap[B](f: A => List[B]): List[B] = ???
    }
  }

  // Question. What is the value of this code?
  val listFlatMapExample = List(1, 2, 3).flatMap(x => List(x, x * 2))

  // Question. Do you think only collections can have `flatMap`?

  // `filter` takes a predicate function returning a boolean and - for collections - returns a collection
  // with only these elements which satisfy this predicate.

  // For example, for `List` it is defined as:
  object list_filter_example {
    class List[A] {
      def filter(p: A => Boolean): List[A] = ???
    }
  }

  // Question. What is the value of this code?
  val listFilterExample = List(1, 2, 3).filter(_ % 2 == 0)

  // For Comprehensions

  // A `for-yield` syntax is syntactic sugar for composing multiple `map`, `flatMap` and `filter` operations
  // together in a more readable form.

  // val result = for {
  //   x <- a
  //   y <- b
  // } yield x + y
  //
  // gets translated to
  //
  // val result = a.flatMap(x => b.map(y => x + y))

  private val a = List(1, 2, 3)
  private val b = List(10, 100)

  val c = for {
    x <- a
    y <- b
  } yield x * y

  val d = a.flatMap(x => b.map(y => x * y))

  // Question: What is the value of `c` above?
  // Question: What is the value of `d` above?

  // You can also add `if` guards to `for` comprehensions:
  val e = for {
    x <- a
    if x % 2 == 1
    y <- b
  } yield x + y

  // Question. What is the value of `e` above?

  // In idiomatic functional Scala, much of the code ends up written in "for comprehensions".
  // Exercise. Implement `makeTransfer` using `for` comprehensions and the methods provided in `UserService`.

  type UserId = String
  type Amount = BigDecimal

  trait UserService {
    def validateUserName(name: String): Either[ErrorMessage, Unit]
    def findUserId(name: String): Either[ErrorMessage, UserId]
    def validateAmount(amount: Amount): Either[ErrorMessage, Unit]
    def findBalance(userId: UserId): Either[ErrorMessage, Amount]

    /** Upon success, returns the resulting balance */
    def updateAccount(userId: UserId, previousBalance: Amount, delta: Amount): Either[ErrorMessage, Amount]
  }

  // Upon success, should return the remaining amounts on both accounts as a tuple.
  def makeTransfer(
      service: UserService,
      fromUserWithName: String,
      toUserWithName: String,
      amount: Amount
  ): Either[ErrorMessage, (Amount, Amount)] = {
    // Replace with a proper implementation that uses validateUserName on each name,
    // findUserId to find UserId, validateAmount on the amount, findBalance to find previous
    // balances, and then updateAccount for both userId-s (with a positive and negative
    // amount, respectively):
    import service._ // to avoid constantly calling service.something

    for {
      _           <- validateAmount(amount)
      _           <- validateUserName(fromUserWithName)
      _           <- validateUserName(toUserWithName)
      fromID      <- findUserId(fromUserWithName)
      fromBalance <- findBalance(fromID)
      toID        <- findUserId(toUserWithName)
      toBalance   <- findBalance(toID)
      fromUpdated <- updateAccount(fromID, fromBalance, -amount)
      toUpdated   <- updateAccount(toID, toBalance, amount)
    } yield (fromUpdated, toUpdated)
  }

  // Question. What are the questions would you ask - especially about requirements - before implementing
  // this function? What issues does this implementation have?

  // Question. Does the implementation of `makeTransfer` depend on the "container" being an `Either`?

  // Let us return to our "intuition about types" exercises from before.

  // Exercise:
  //
  // Given:
  //  A = Set(0, 1, 2)
  //  B = Set(true, false)
  //
  // List all the elements in `A * B`.
  //
  // Use a "for comprehension" in your solution.

  val AProductB: Set[(Int, Boolean)] =
    for {
      x <- Set(0, 1, 2)
      y <- Set(true, false)
    } yield (x, y)

  // Exercise:
  //
  // Given:
  val A = Set(0, 1, 2)
  val B = Set(true, false)
  //
  // List all the elements in `A + B`.
  //
  // Use "map" and `++` (`Set` union operation) in your solution.

  val ASumB: Set[Either[Int, Boolean]] = A.map(Left(_)) ++ B.map(Right(_))
  val ASumB1: Set[Either[Int, Boolean]] = A.map(x => Left(x)) ++ B.map(x => Right(x))
  val ASumB2: Set[Either[Int, Boolean]] = A.map(Left.apply) ++ B.map(Right.apply)

  // Scala inherits the standard try-catch-finally construct from Java:
  def printFile(fileName: String): Unit = {
    // This code is only here to illustrate try-catch-finally, it shouldn't be considered as good code
    val source = Source.fromFile(fileName)
    try { // executed until an exception happens
      source.getLines() foreach println
    } catch { // exception handlers
      case e: FileNotFoundException => println(s"Couldn't find the file: $e")
      case e: Exception             => println(s"Exception occurred: $e")
    } finally { // executed even if an exception happens
      source.close
    }
  }

  // Question. What issues can you find with the above `printFile` method?

  // However, in idiomatic functional Scala, other error handling mechanisms are usually used.
  // Throwing exceptions is an anti-pattern - it introduces another exit path from a function and breaks
  // referential transparency.
  // It can be thought of as a "`goto` to an unknown place up the call stack".

  // One of these other mechanisms is `Try` which can be thought of as an `Either[Throwable, A]`:

  def parseInt1(x: String): Try[Int] = Try(x.toInt)
  def parseInt2(x: String): Option[Int] = x.toIntOption
  def parseInt3(x: String, default: Int = 0): Int = x.toIntOption.getOrElse(default)

  parseInt1("asdf") match {
    case Success(value) => println(value)
    case Failure(error) => println(error)
  }

  // Question. What other ways of representing the "parse string to integer" results can you think of?
  // What are the benefits and drawbacks of each?

  // For homework, refer to `ControlStructuresHomework`
}
