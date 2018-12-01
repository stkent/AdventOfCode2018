fun main() {
    val input = resourceFile("input.txt")

//    println(input.readLines()
//        .map { Integer.parseInt(it) }
//        .sum())

    var acc = 0
    val seen = mutableSetOf(0)

    val allLines = input.readLines()
    var index = 0

    while (true) {
        acc += Integer.parseInt(allLines[index])

        if (seen.contains(acc)) {
            println(acc)
            break
        }

        seen.add(acc)
        index = (++index).rem(allLines.size)
    }
}
