interface AdditiveGroup<T> {
  operator fun plus(other: T): T
  operator fun unaryMinus(): T
  override fun equals(other: Any?): Boolean
  val zero: T
}

operator fun<T: AdditiveGroup<T>> T.minus(other: T) = this + (-other)
