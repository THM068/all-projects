import groovy.transform.IndexedProperty

import java.text.DateFormat
import java.time.format.DateTimeFormatter

println("hello world")

def num = 23
def name = "Thando"

println NV(num, name)

def value = 1..<5

value.each { println it}

def n = /thando "mafela"/
println n

def countDown(int i) {
    //println(i)
    if(i <= 0)
        return
    else
        countDown(i -1 )
}
countDown(10)

def factorial(int x) {
    if(x == 1) {
        return  x
    }
    return  x * factorial( x -1)
}

println(factorial(5))

def sum(int [] array) {
    if (array.length == 0) {
        return 0
    } else {
        int [] temp = new int[array.length -1]
        for(int x =0; x < temp.length; x ++) {
            temp[x] = array[x+1]
        }
        return array[0] + sum(temp)
    }
}
int[] x = [1, 2, 3]
int result = sum(x)
print(""+ result)

def sum = (0..20).inject(0) { acc, xx -> acc + xx }

println(sum)

StringBuffer

String fileName = "/tmp/${UUID.randomUUID().toString()}"

new File(fileName) << """
${UUID.randomUUID().toString()}
${UUID.randomUUID().toString()}
${UUID.randomUUID().toString()}
""".trim()

new File(fileName).eachLine {line ->
    println(line)
}

@IndexedProperty String[] someArray = new String[2]

someArray.