
interface P__<K: Field<K>> {
  val poly: Polynomial<K>
}

abstract class PolynomialQuotientField<P: P__<F>, F: Field<F>>(override val poly: Polynomial<F>): Field<P>, P__<F> {
  abstract val generator: (Polynomial<F>) -> P
  abstract val monomialGen: (F) -> P
  abstract val polyGen: (F) -> Polynomial<F>

  abstract val modulo: Polynomial<F>

  override val identity
    get() = monomialGen(poly.coeffs[0].identity)
  override val zero
    get() = monomialGen(poly.coeffs[0].zero)

  override val inverse: P
    get() {
      val (p, _, r) = bezout(poly, modulo)
      return generator(p * polyGen(r.coeffs[0].inverse))
    }

  override operator fun times(other: P): P = generator(this.poly * other.poly)
  override operator fun times(other: Int): P = generator(this.poly * other)
  override operator fun plus(other: P): P = generator(this.poly + other.poly)
  override operator fun unaryMinus(): P = generator(-this.poly)

  override fun toString(): String = "${poly % modulo} mod $modulo"
}
