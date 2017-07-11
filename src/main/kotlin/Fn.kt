
interface Z__ {
  val value: Z
}

abstract class F_<T: Z__>(override val value: Z): Field<T>, Z__ {
  abstract val generator:(Z)->T
  abstract val modulo: Z

  override val zero: T
    get() = generator(0)
  override val identity: T
    get() = generator(1)
  override val inverse: T
    get() {
      val (x, _, _) = bezout(value, modulo)
      return generator(x)
    }

  val reduced: T
    get() = generator(((this.value % this.modulo) + this.modulo) % this.modulo)

  override operator fun times(other: Z) = generator(this.value * other)
  override operator fun times(other: T) = generator(this.value * other.value)
  override operator fun plus(other: T) = generator(this.value + other.value)
  override operator fun unaryMinus() = generator(-this.value)

  override fun equals(other: Any?): Boolean = when(other) {
    is F_<*> -> ((this.value - other.value) % this.modulo == 0)
    else -> false
  }

  override fun toString(): String {
    val r = this.reduced
    return "${r.value} mod ${this.modulo}"
  }

  override fun hashCode(): Int {
    var result = value
    result = 31 * result + generator.hashCode()
    result = 31 * result + modulo
    return result
  }
}
