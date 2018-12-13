fun main() {
    val input = 1308

    val device = Device(serialNumber = input)

    println("Part 1 solution: ${device.maxSquarePower(minSize = 3, maxSize = 3)}")
    println("Part 2 solution: ${device.maxSquarePower(minSize = 1, maxSize = 300)}")
}
