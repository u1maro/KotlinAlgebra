
class Q(val p: Z, val q: Z = 1): Field<Q> {
  override fun equals(other: Any?) = when(other) {
      is Q -> (this.p * other.q) == (this.q * other.p)
      else -> false
  }

  override operator fun plus(other: Q) = Q(this.p * other.q + this.q * other.p, this.q * other.q)
  override operator fun unaryMinus() = Q(-this.p, this.q)
  override operator fun times(other: Q) = Q(this.p * other.p, this.q * other.q)
  override operator fun times(other: Int) = Q(this.p * other, this.q)

  override val inverse
	 	 get() = Q(this.q, this.p)

  override val identity
		get() = Q(1)

  override val zero
	 	get() = Q(0, 1)

  val reduced: Q
    get() {
      val d = gcd(this.p, this.q)
      return Q(p/d, q/d)
    }

  fun gcd(a: Int, b: Int): Int = when(b) {
    0 -> a
    else -> gcd(b, a % b)
  }

  override fun toString(): String {
    val r = this.reduced

    return when(r.q) {
      1 -> "${r.p}"
      else -> "${r.p} / ${r.q}"
    }
  }

  override fun hashCode(): Int {
    var result = p
    result = 31 * result + q
    return result
  }
}

operator fun Z.times(q: Q) = Q(this) * q
