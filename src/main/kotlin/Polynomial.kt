
class Polynomial<K: Field<K>>(val coeffs: List<K>): EuclideanRing<Polynomial<K>> {
  constructor(degree: Int, generator: (Int)->K): this((0 .. degree).map{ generator(it) })
  constructor(vararg coeffs: K): this(coeffs.toList())

  override val zero: Polynomial<K>
    get() = Polynomial(0){_ -> coeffs[0].zero}
  override val identity: Polynomial<K>
    get() = Polynomial(0){_ -> coeffs[0].identity}

  val degree: Int
    get() {
      (coeffs.size - 1 downTo 0).forEach { if(coeffs[it] != coeffs[0].zero) { return it } }
      return 0
    }

  val leadCoeff: K = coeffs[degree]

  override operator fun times(other: Polynomial<K>): Polynomial<K> = Polynomial(this.degree + other.degree) {
    (0 .. it).fold(coeffs[0].zero) {
      res, j ->
      res + this[j] * other[it-j]
    }
  }

  override operator fun times(other: Int): Polynomial<K> = Polynomial(this.degree) { this[it] * other }

  override operator fun plus(other: Polynomial<K>): Polynomial<K> {
    val d = if(this.degree > other.degree) { this.degree } else { other.degree }
    return Polynomial(d){this[it] + other[it]}
  }

  override operator fun unaryMinus(): Polynomial<K> = Polynomial(this.degree){ -this[it] }

  fun toMonic(): Polynomial<K> {
    val a = this.leadCoeff
    return this.map{ it / a }
  }

  infix override fun eucDiv(other: Polynomial<K>): Pair<Polynomial<K>, Polynomial<K>> {
    fun eucDivMonomial(f: Polynomial<K>, g: Polynomial<K>): Pair<Polynomial<K>, Polynomial<K>> {
      val n = f.degree - g.degree
      if(n < 0) {
        return Pair(Polynomial(0){ _ -> f[0].zero }, f)
      } else {
        val a = f.leadCoeff / g.leadCoeff
        val q = Polynomial(n){ if(it == n) { a } else { f[0].zero } }
        val r = f - q * g
        return Pair(q, r)
      }
    }

    val d = if(this.degree > other.degree) { this.degree - other.degree } else { 0 }

    return (d downTo 0).fold(Pair(this.zero, this)) {
      (q, r), _ ->
        val (mq, mr) = eucDivMonomial(r, other)
        Pair(q + mq, mr)
    }
  }

  override operator fun rem(other: Polynomial<K>): Polynomial<K> {
    val (_, r) = this eucDiv other
    return r
  }

  override fun equals(other: Any?): Boolean = when(other) {
    is Polynomial<*> -> if(this.degree == other.degree) {
      this.coeffs.zip(other.coeffs).all{ (t, o) -> t == o }
    } else {
      false
    }
    else -> false
  }

  override fun toString(): String {
    return (this.degree downTo 0).map {i ->
      val z = coeffs[0].zero
      val o = coeffs[0].identity
      val m = -o
      when {
        coeffs[i] == z -> ""
        i == 0 -> "${coeffs[i]}"
        coeffs[i] == o && i == 1 -> "x"
        coeffs[i] == m && i == 1 -> "-x"
        i == 1 -> "${coeffs[i]}x"
        coeffs[i] == o -> "x^$i"
        coeffs[i] == m -> "-x^$i"
        else -> "${coeffs[i]}x^$i"
      }
    }.filter(String::isNotEmpty).joinToString(" + ")
  }

  operator fun get(i: Int): K = if(i <= this.degree) { coeffs[i] } else { coeffs[0].zero }

  fun apply(x: K): K = (0..this.degree).fold(coeffs[0].zero) { sum, i -> sum + this[i] * (x pow i)}

  fun map(acc: (K)->K): Polynomial<K> = Polynomial(coeffs.map(acc))
  override fun hashCode(): Int {
    var result = coeffs.hashCode()
    result = 31 * result + leadCoeff.hashCode()
    return result
  }
}
