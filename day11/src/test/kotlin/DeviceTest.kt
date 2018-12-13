import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class DeviceTest : StringSpec({

    "Cell power" {
        forall(
            // @formatter:off
            row( 8, Cell(  3,   5),  4),
            row(57, Cell(122,  79), -5),
            row(39, Cell(217, 196),  0),
            row(71, Cell(101, 153),  4)
            // @formatter:on
        ) { serialNumber, cell, expected ->
            Device(serialNumber).cellPower(cell) shouldBe expected
        }
    }

    "Most powerful 3x3 square" {
        forall(
            row(18, Cell(33, 45), 29),
            row(42, Cell(21, 61), 30)
        ) { serialNumber, expectedCorner, expectedPower ->
            val (corner, size, power) = Device(serialNumber).maxSquarePower(minSize = 3, maxSize = 3)

            corner shouldBe expectedCorner
            size shouldBe 3
            power shouldBe expectedPower
        }
    }

})
