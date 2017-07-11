
interface Field<K>: Group<K>, Ring<K>
operator fun<K: Field<K>> K.div(other: K) = this * other.inverse
infix fun <K> Field<K>.pow(n: Int): K = if(n == 0) { this.identity } else { this * this.pow(n - 1) }
