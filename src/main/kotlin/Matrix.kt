
class Matrix<R: Ring<R>>(val rows: Int, val columns: Int, val elements: List<R>): AdditiveGroup<Matrix<R>> {
  constructor(rows: Int, columns: Int, generator: (Int, Int)->R): this(rows, columns, (0 .. rows * columns - 1).map{ generator(it / rows, it % columns) })
  constructor(rows: Int, columns: Int, elements: Array<R>): this(rows, columns, elements.toList())

  override val zero
    get() = Matrix(rows, columns){ _, _ -> elements[0].zero }
  val identity
    get() = Matrix(rows, columns) {
      x, y ->
      if (x == y) { elements[0].identity } else { elements[0].zero }
    }

  override operator fun plus(other: Matrix<R>) = Matrix(rows, columns) {
    i, j ->
    this[i, j] + other[i, j]
  }

  override operator fun unaryMinus() = Matrix(rows, columns) {
    i, j ->
    -this[i, j]
  }

  operator fun times(n: Int) = Matrix(rows, columns) {
    i, j ->
    this[i, j] * n
  }

  operator fun times(other: Matrix<R>) = Matrix(rows, columns) {
    i, k ->
    (0 until this.rows).map {
      this[i, it] * other[it, k]
    }.reduce {
      ret, value ->
      ret + value
    }
  }

  override fun equals(other: Any?): Boolean = when(other) {
    is Matrix<*> -> if((this.rows != other.columns) || (this.rows != other.columns)) {
      false
    } else {
      this.elements.zip(other.elements).all{ (t, o) -> t==o }
    }
    else -> false
  }

  operator fun get(i: Int, j: Int) = elements[(i * columns) + j]

  override fun hashCode(): Int {
    var result = rows
    result = 31 * result + columns
    result = 31 * result + elements.hashCode()
    return result
  }
}

class IntMatrix(val rows: Int, val columns: Int, val elements: List<Int>): AdditiveGroup<IntMatrix> {
  constructor(rows: Int, columns: Int, generator: (Int, Int)->Int): this(rows, columns, (0 until rows * columns).map{ generator(it / rows, it % columns) })
  constructor(rows: Int, columns: Int, elements: Array<Int>): this(rows, columns, elements.toList())

  override val zero: IntMatrix
    get() {
      return IntMatrix(rows, columns) { _, _ -> 0 }
    }
  val identity: IntMatrix
    get() = IntMatrix(rows, columns) {
      x, y ->
      if(x == y) { 1 } else { 0 }
    }

  override operator fun plus(other: IntMatrix) = IntMatrix(rows, columns) {
    i, j ->
    this[i, j] + other[i, j]
  }

  override operator fun unaryMinus() = IntMatrix(rows, columns) {
    i, j ->
    -this[i, j]
  }

  operator fun times(n: Int) = IntMatrix(rows, columns) {
    i, j ->
    this[i, j] * n
  }

  operator fun times(other: IntMatrix) = IntMatrix(rows, columns) {
    i, k ->
    (0 until this.rows).map {
      this[i, it] * other[it, k]
    }.reduce {
      ret, value ->
      ret + value
    }
  }

  override fun equals(other: Any?): Boolean = when(other) {
    is IntMatrix -> if((this.rows != other.columns) || (this.rows != other.columns)) {
      false
    } else {
      this.elements.zip(other.elements).all{ (t, o) -> t == o }
    }
    else -> false
  }

  operator fun get(i: Int, j: Int) = elements[(i * columns) + j]

  override fun hashCode(): Int {
    var result = rows
    result = 31 * result + columns
    result = 31 * result + elements.hashCode()
    return result
  }
}
