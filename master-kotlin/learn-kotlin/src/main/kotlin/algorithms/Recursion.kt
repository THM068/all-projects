package algorithms

fun main() {
    countDown(2 -1)
    println(factorial(4))
}

tailrec fun countDown(counter: Long) {
    println(counter)
    if(counter <= 0)
        return
    else
        countDown(counter -1)
}

fun factorial(n: Long) : Long {
    if(n == 1L) {
       println(n)
       return n
    }
    else {
        return n.times(factorial( n -1))
    }
}
