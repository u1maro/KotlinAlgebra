
abstract class Z_<T: Z__> (override val value: Z): Ring<T>, Z__ {
  abstract val generator: (Z) -> T
  abstract val modulo: Int

  override val identity
    get() = generator(1)

  override val zero
    get() = generator(0)

  override operator fun times(other: Int) = generator(this.value * other)
  override operator fun times(other: T) = generator(this.value * other.value)
  override operator fun plus(other: T) = generator(this.value + other.value)
  override operator fun unaryMinus() = generator(-this.value)

  override fun equals(other: Any?): Boolean = when(other) {
    is Z_<*> -> (((this.value - other.value) % this.modulo) == 0)
    else -> false
  }
  override fun toString() = "${this.value % this.modulo} mod ${this.modulo}"

  override fun hashCode(): Int {
    var result = value
    result = 31 * result + generator.hashCode()
    result = 31 * result + modulo
    return result
  }
}
