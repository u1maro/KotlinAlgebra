
abstract class PolynomialQuotientRing<P: P__<K>, K: Field<K>> (override val poly: Polynomial<K>): Ring<P>, P__<K> {
  abstract val generator: (Polynomial<K>) -> P
  //abstract val arrayGen: (Array<K>) -> P
  abstract val monomialGen: (K) -> P
  abstract val modulo: Polynomial<K>

  override val identity
    get() = monomialGen(poly.coeffs[0].identity)
  override val zero
    get() = monomialGen(poly.coeffs[0].zero)

  override operator fun times(other: P): P = generator(this.poly * other.poly)
  override operator fun times(other: Int): P = generator(this.poly * other)
  override operator fun plus(other: P): P = generator(this.poly + other.poly)
  override operator fun unaryMinus(): P = generator(-this.poly)

  override fun toString(): String = "$poly mod $modulo"
}
