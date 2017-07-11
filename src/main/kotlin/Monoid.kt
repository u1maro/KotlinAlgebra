interface Monoid<T> {
  operator fun times(other: T): T
  operator fun times(other: Int): T
  val identity: T
}
