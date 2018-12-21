fun main() {
    val inputIpRegister = 3
    val inputProgram = resourceFile("input.txt").readLines()

    val device = Device(ipRegister = inputIpRegister)

    println(device.computeHaltingR0s(rawProgram = inputProgram, r0Value = 0))
}
