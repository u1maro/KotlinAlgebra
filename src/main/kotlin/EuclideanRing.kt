
interface EuclideanRing<T>: Ring<T> {
  infix fun eucDiv(other: T): Pair<T, T>
  operator fun rem(other: T): T
}

fun <R: EuclideanRing<R>> gcd(lhs: R, rhs: R): R = when(rhs) {
  lhs.zero -> lhs
  else -> gcd(rhs, lhs % rhs)
}

inline fun <reified R: EuclideanRing<R>> euclid(lhs: R, rhs: R): Pair<Array<R>, R> {
  var a = lhs
  var b = rhs
  var qsp: Array<R> = arrayOf()

  while(b != a.zero) {
    val (q, r) = a eucDiv b
    a = b
    b = r
    qsp = arrayOf(q) + qsp
  }

  return Pair(qsp, a)
}

inline fun <reified R: EuclideanRing<R>> bezout(a: R, b: R): Triple<R, R, R> {
  val (qs, d) = euclid(a, b)
  val m: Matrix<R> = qs.fold(Matrix(2, 2, arrayOf(a.identity)).identity) {
    ret, value ->
    ret * Matrix(2, 2, arrayOf(a.zero, a.identity, a.identity, -value))
  }

  return Triple(m[0, 0], m[0, 1], d)
}
