
fun gcd(a: Z, b: Z): Z = when(b) {
  0 -> a
  else -> gcd(b, a % b)
}

fun bezout(a: Z, b: Z): Triple<Z, Z, Z> {
  fun euclid(a: Z, b: Z, qs: Array<Z>): Pair<Array<Z>, Z> = when(b) {
    0 -> Pair(qs, a)
    else -> euclid(b, a % b, arrayOf(a / b) + qs)
  }

  val (qs, d) = euclid(a, b, arrayOf())
  val m = qs.fold(IntMatrix(2, 2) {
    x, y ->
    if(x == y) { 1 } else { 0 }
  }) {
    ret, value ->
    ret * IntMatrix(2, 2, arrayOf(0, 1, 1, -value))
  }

  return Triple(m[0, 0], m[0, 1], d)
}
