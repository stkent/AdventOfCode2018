import java.io.File

fun main() {
    // Advent of Code Full Example 1
    val input1 = listOf(
            "███████",
            "█.G...█",
            "█...EG█",
            "█.█.█G█",
            "█..G█E█",
            "█.....█",
            "███████"
    )

    // Advent of Code Full Example 2
    val input2 = listOf(
            "███████",
            "█G..█E█",
            "█E█E.E█",
            "█G.██.█",
            "█...█E█",
            "█...E.█",
            "███████"
    )

    // Advent of Code Full Example 3
    val input3 = listOf(
            "███████",
            "█E..EG█",
            "█.█G.E█",
            "█E.██E█",
            "█G..█.█",
            "█..E█.█",
            "███████"
    )

    // Advent of Code Full Example 4
    val input4 = listOf(
            "███████",
            "█E.G█.█",
            "█.█G..█",
            "█G.█.G█",
            "█G..█.█",
            "█...E.█",
            "███████"
    )

    // Advent of Code Full Example 5
    val input5 = listOf(
            "███████",
            "█.E...█",
            "█.█..G█",
            "█.███.█",
            "█E█G█G█",
            "█...█G█",
            "███████"
    )

    // Advent of Code Full Example 6
    val input6 = listOf(
            "█████████",
            "█G......█",
            "█.E.█...█",
            "█..██..G█",
            "█...██..█",
            "█...█...█",
            "█.G...G.█",
            "█.....G.█",
            "█████████"
    )

    // Github (ShaneMcC) moveLeft
    val input7 = listOf(
            "█████",
            "███G█",
            "███.█",
            "█.E.█",
            "█G███",
            "█████"
    )

    // Github (ShaneMcC) moveRight
    val input8 = listOf(
            "███████",
            "█.E..G█",
            "█.█████",
            "█G█████",
            "███████"
    )

    // Github (ShaneMcC) movement
    val input9 = listOf(
            "█████████",
            "█G..G..G█",
            "█.......█",
            "█.......█",
            "█G..E..G█",
            "█.......█",
            "█.......█",
            "█G..G..G█",
            "█████████"
    )

    // Github (ShaneMcC) reddit1
    val input10 = listOf(
            "████",
            "██E█",
            "█GG█",
            "████"
    )

    // Github (ShaneMcC) reddit2
    val input11 = listOf(
            "█████",
            "█GG██",
            "█.███",
            "█..E█",
            "█.█G█",
            "█.E██",
            "█████"
    )

    // Github (ShaneMcC) reddit3
    val input12 = listOf(
            "██████████",
            "█.E....G.█",
            "█......███",
            "█.G......█",
            "██████████"
    )

    // Github (ShaneMcC) reddit4
    val input13 = listOf(
            "██████████",
            "█........█",
            "█......█.█",
            "█E....G█E█",
            "█......█.█",
            "█........█",
            "██████████"
    )

    // Github (ShaneMcC) reddit5
    val input14 = listOf(
            "███████",
            "█..E█G█",
            "█.....█",
            "█G█...█",
            "███████"
    )

    // Github (ShaneMcC) reddit6
    val input15 = listOf(
            "█████████",
            "█......G█",
            "█G.G...E█",
            "█████████"
    )

    // Github (ShaneMcC) reddit7
    val input16 = listOf(
            "██████",
            "█.G..█",
            "█...E█",
            "█E...█",
            "██████"
    )

    // Github (ShaneMcC) reddit8
    val input17 = listOf(
            "██████",
            "█.G..█",
            "██..██",
            "█...E█",
            "█E...█",
            "██████"
    )

    // Github (ShaneMcC) reddit9
    val input18 = listOf(
            "████████",
            "█.E....█",
            "█......█",
            "█....G.█",
            "█...G..█",
            "█G.....█",
            "████████"
    )

    // Github (ShaneMcC) reddit10
    val input19 = listOf(
            "█████████████████",
            "██..............█",
            "██........G.....█",
            "████.....G....███",
            "█....██......████",
            "█...............█",
            "██........GG....█",
            "██.........E..█.█",
            "█████.███...█████",
            "█████████████████"
    )

    val realInput = readInputFile()

    val battle1 = Battle(rawMap = input1)
    val battle1Result = battle1.executePartI().let { it.remainingHp * it.rounds }
    val battle1Expected = 27730
    if (battle1Result != battle1Expected) {
        println("Battle 1: $battle1Result, expected $battle1Expected!")
    }

    val battle2 = Battle(rawMap = input2)
    val battle2Result = battle2.executePartI().let { it.remainingHp * it.rounds }
    val battle2Expected = 36334
    if (battle2Result != battle2Expected) {
        println("Battle 2: $battle2Result, expected $battle2Expected!")
    }

    val battle3 = Battle(rawMap = input3)
    val battle3Result = battle3.executePartI().let { it.remainingHp * it.rounds }
    val battle3Expected = 39514
    if (battle3Result != battle3Expected) {
        println("Battle 3: $battle3Result, expected $battle3Expected!")
    }

    val battle4 = Battle(rawMap = input4)
    val battle4Result = battle4.executePartI().let { it.remainingHp * it.rounds }
    val battle4Expected = 27755
    if (battle4Result != battle4Expected) {
        println("Battle 4: $battle4Result, expected $battle4Expected!")
    }

    val battle5 = Battle(rawMap = input5)
    val battle5Result = battle5.executePartI().let { it.remainingHp * it.rounds }
    val battle5Expected = 28944
    if (battle5Result != battle5Expected) {
        println("Battle 5: $battle2Result, expected $battle5Expected!")
    }

    val battle6 = Battle(rawMap = input6)
    val battle6Result = battle6.executePartI().let { it.remainingHp * it.rounds }
    val battle6Expected = 18740
    if (battle6Result != battle6Expected) {
        println("Battle 6: $battle6Result, expected $battle6Expected!")
    }

    val battle7 = Battle(rawMap = input7)
    val battle7Result = battle7.executePartI().let { it.remainingHp * it.rounds }
    val battle7Expected = 10030
    if (battle7Result != battle7Expected) {
        println("Battle 7: $battle7Result, expected $battle7Expected!")
    }

    val battle8 = Battle(rawMap = input8)
    val battle8Result = battle8.executePartI().let { it.remainingHp * it.rounds }
    val battle8Expected = 10234
    if (battle8Result != battle8Expected) {
        println("Battle 8: $battle8Result, expected $battle8Expected!")
    }

    val battle9 = Battle(rawMap = input9)
    val battle9Result = battle9.executePartI().let { it.remainingHp * it.rounds }
    val battle9Expected = 27828
    if (battle9Result != battle9Expected) {
        println("Battle 9: $battle9Result, expected $battle9Expected!")
    }

    val battle10 = Battle(rawMap = input10)
    val battle10Result = battle10.executePartI().let { it.remainingHp * it.rounds }
    val battle10Expected = 13400
    if (battle10Result != battle10Expected) {
        println("Battle 10: $battle10Result, expected $battle10Expected!")
    }

    val battle11 = Battle(rawMap = input11)
    val battle11Result = battle11.executePartI().let { it.remainingHp * it.rounds }
    val battle11Expected = 13987
    if (battle11Result != battle11Expected) {
        println("Battle 11: $battle11Result, expected $battle11Expected!")
    }

    val battle12 = Battle(rawMap = input12)
    val battle12Result = battle12.executePartI().let { it.remainingHp * it.rounds }
    val battle12Expected = 10325
    if (battle12Result != battle12Expected) {
        println("Battle 12: $battle12Result, expected $battle12Expected!")
    }

    val battle13 = Battle(rawMap = input13)
    val battle13Result = battle13.executePartI().let { it.remainingHp * it.rounds }
    val battle13Expected = 10804
    if (battle13Result != battle13Expected) {
        println("Battle 13: $battle13Result, expected $battle13Expected!")
    }

    val battle14 = Battle(rawMap = input14)
    val battle14Result = battle14.executePartI().let { it.remainingHp * it.rounds }
    val battle14Expected = 10620
    if (battle14Result != battle14Expected) {
        println("Battle 14: $battle14Result, expected $battle14Expected!")
    }

    val battle15 = Battle(rawMap = input15)
    val battle15Result = battle15.executePartI().let { it.remainingHp * it.rounds }
    val battle15Expected = 16932
    if (battle15Result != battle15Expected) {
        println("Battle 15: $battle15Result, expected $battle15Expected!")
    }

    val battle16 = Battle(rawMap = input16)
    val battle16Result = battle16.executePartI().let { it.remainingHp * it.rounds }
    val battle16Expected = 10234
    if (battle16Result != battle16Expected) {
        println("Battle 16: $battle16Result, expected $battle16Expected!")
    }

    val battle17 = Battle(rawMap = input17)
    val battle17Result = battle17.executePartI().let { it.remainingHp * it.rounds }
    val battle17Expected = 10430
    if (battle17Result != battle17Expected) {
        println("Battle 17: $battle17Result, expected $battle17Expected!")
    }

    val battle18 = Battle(rawMap = input18)
    val battle18Result = battle18.executePartI().let { it.remainingHp * it.rounds }
    val battle18Expected = 12744
    if (battle18Result != battle18Expected) {
        println("Battle 18: $battle18Result, expected $battle18Expected!")
    }

    val battle19 = Battle(rawMap = input19)
    val battle19Result = battle19.executePartI().let { it.remainingHp * it.rounds }
    val battle19Expected = 14740
    if (battle19Result != battle19Expected) {
        println("Battle 19: $battle19Result, expected $battle19Expected!")
    }

    val realBattle = Battle(rawMap = realInput)
    println("Part I: ${realBattle.executePartI().let { it.remainingHp * it.rounds }}")
    println("Part II: ${realBattle.executePartII().let { it.remainingHp * it.rounds }}")
}

fun readInputFile(): List<String> {
    return File(ClassLoader.getSystemResource("input.txt").file).readLines()
}
