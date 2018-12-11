class LightTracker {

    data class MessageData(val sky: List<String>, val time: Int)

    fun findMessage(rawInitialLights: List<String>, fontHeight: Int): MessageData {
        var lights = rawInitialLights.map(SpatialData.Companion::fromString)

        var second = 0

        while (true) {
            second += 1

            lights = lights.map { light -> light.copy(position = light.position + light.velocity) }

            val lightPositions = lights.map(SpatialData::position).toSet()

            val ys = lightPositions.map(GridPoint2d::y)
            val minY = ys.min()!!
            val maxY = ys.max()!!

            if (maxY - minY <= fontHeight) {
                val xs = lightPositions.map(GridPoint2d::x)

                val sky = (minY..maxY).map { y ->
                    return@map (xs.min()!!..xs.max()!!)
                        .map { x -> GridPoint2d(x, y) }
                        .map { skyPosition -> if (lightPositions.contains(skyPosition)) '█' else ' ' }
                        .joinToString("")
                }

                return MessageData(sky = sky, time = second)
            }
        }
    }

}
