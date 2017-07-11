
interface Group<T>: Monoid<T> {
  override fun equals(other: Any?): Boolean
  val inverse: T
}
