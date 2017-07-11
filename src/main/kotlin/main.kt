
fun constructPolynomialQ(vararg coeffs: Int): Polynomial<Q> = Polynomial(coeffs.map{ Q(it) })

class Qi(override val poly: Polynomial<Q>): PolynomialQuotientField<Qi, Q>(poly) {
  constructor(vararg coeffs: Int): this(Polynomial(coeffs.map{ Q(it) }))
  constructor(vararg coeffs: Q): this(Polynomial(coeffs.toList()))

  override val generator: (Polynomial<Q>) -> Qi = ::Qi
  override val monomialGen: (Q) -> Qi = { Qi(it) }
  override val polyGen: (Q) -> Polynomial<Q> = { Polynomial(it) }

  override val modulo: Polynomial<Q> = constructPolynomialQ(1, 0, 1)

  override fun equals(other: Any?): Boolean = when(other) {
    is Qi -> (this.poly - other.poly) % this.modulo == constructPolynomialQ(0)
    else -> false
  }
}

class W(override val poly: Polynomial<Q>): PolynomialQuotientField<W, Q>(poly) {
  constructor(vararg coeffs: Int): this(Polynomial(coeffs.map{ Q(it) }))
  constructor(vararg coeffs: Q): this(Polynomial(coeffs.toList()))

  override val generator: (Polynomial<Q>) -> W = ::W
  override val monomialGen: (Q) -> W = { W(it) }
  override val polyGen: (Q) -> Polynomial<Q> = { Polynomial(it) }

  override val modulo: Polynomial<Q> = constructPolynomialQ(1, 1, 1)

  override fun equals(other: Any?): Boolean = when(other) {
    is W -> (this.poly - other.poly) % this.modulo == constructPolynomialQ(0)
    else -> false
  }
}

class QSqrt2(override val poly: Polynomial<Q>): PolynomialQuotientField<QSqrt2, Q>(poly) {
  constructor(vararg coeffs: Int): this(Polynomial(coeffs.map{ Q(it) }))
  constructor(vararg coeffs: Q): this(Polynomial(coeffs.toList()))

  override val generator: (Polynomial<Q>) -> QSqrt2 = ::QSqrt2
  override val monomialGen: (Q) -> QSqrt2 = { QSqrt2(it) }
  override val polyGen: (Q) -> Polynomial<Q> = { Polynomial(it) }

  override val modulo: Polynomial<Q> = constructPolynomialQ(-2, 0, 1)

  override fun equals(other: Any?): Boolean = when(other) {
    is QSqrt2 -> (this.poly - other.poly) % this.modulo == constructPolynomialQ(0)
    else -> false
  }
}

class QSqrt3(override val poly: Polynomial<QSqrt2>): PolynomialQuotientField<QSqrt3, QSqrt2>(poly) {
  constructor(vararg coeffs: Int): this(Polynomial(coeffs.map{ QSqrt2(it) }))
  constructor(vararg coeffs: QSqrt2): this(Polynomial(coeffs.toList()))

  override val generator: (Polynomial<QSqrt2>) -> QSqrt3 = ::QSqrt3
  override val monomialGen: (QSqrt2) -> QSqrt3 = { QSqrt3(it) }
  override val polyGen: (QSqrt2) -> Polynomial<QSqrt2> = { Polynomial(it) }

  override val modulo: Polynomial<QSqrt2> = Polynomial(arrayOf(-3, 0, 1).map{ QSqrt2(it) })

  override fun equals(other: Any?): Boolean = when(other) {
    is QSqrt3 -> (this.poly - other.poly) % this.modulo == Polynomial(QSqrt2(0))
    else -> false
  }
}

class g(override val poly: Polynomial<Q>): PolynomialQuotientField<g, Q>(poly) {
  constructor(vararg coeffs: Int): this(Polynomial(coeffs.map{ Q(it) }))
  constructor(vararg coeffs: Q): this(Polynomial(coeffs.toList()))

  override val generator: (Polynomial<Q>) -> g = ::g
  override val monomialGen: (Q) -> g = { g(it) }
  override val polyGen: (Q) -> Polynomial<Q> = { Polynomial(it) }

  override val modulo: Polynomial<Q> = constructPolynomialQ(-2, 0, 1)

  override fun equals(other: Any?): Boolean = when(other) {
    is g -> (this.poly - other.poly) % this.modulo == constructPolynomialQ(0)
    else -> false
  }
}

fun main(args: Array<String>) {
  // Q[\sqrt2]
  val a1 = g(0, 1)
  println(a1)
  println(a1*a1)
  println(a1 * a1 == g(2))

  val aa = g(1) + a1
  val asq = aa * aa
  val rhs = a1 * 2 + g(3)

  println(asq)
  println(rhs)
  println(asq == rhs)

  val b1 = g(1) / aa
  val b2 = g(-1) + a1

  println(b1)
  println(b2)
  println(b1 == b2)

  // Q(i)
  val i = Qi(0, 1)
  println(i * i == Qi(-1))

  val z = Qi(3) + i * 2
  println(z * z == Qi(5) + i * 12)

  // Q(z3)
  val w3 = W(0, 1)
  println(w3 * w3 * w3)
  println(w3 * w3 * w3 == W(1))

  // Q[\sqrt2, \sqrt3]
  val a3 = QSqrt2(0, 1)
  println(a3)
  println(a3 * a3)
  println(a3 * a3 == QSqrt2(2))

  val b3 = QSqrt3(a3)
  val c3 = QSqrt3(0, 1)
  println(b3)
  println(b3 * b3)
  println(b3 * b3 == QSqrt3(2))

  println(c3)
  println(c3 * c3)
  println(c3 * c3 == QSqrt3(3))

  val d = b3 * c3
  val x = b3 + c3

  println(d)
  println(x)
  println(x * x == d * 2 + QSqrt3(5))

  // Polynomials
  val f1 = constructPolynomialQ(2, -3, 1, 2)
  val g1 = constructPolynomialQ(1, 0, 1)

  println(f1)
  println(g1)

  val (q1, r1) = f1 eucDiv g1

  println(q1)
  println(r1)

  println(f1 == g1 * q1 + r1)

  // K[x]/g(x)
  val f2 = g(2, -3, 1, 2)
  val r2 = g(1, -5)

  println(f2)
  println(r2)

  println(f2 == r2)

  // functions on Polynomials
  val f3 = constructPolynomialQ(0, 2, -3, 1)
  val g3 = constructPolynomialQ(6, -5, 1)

  println(gcd(f3, g3))

  val (p3, q3, r3) = bezout(f3, g3)

  println(p3)
  println(q3)
  println(r3)

  println(f3*p3 + g3*q3 == r3)
}
