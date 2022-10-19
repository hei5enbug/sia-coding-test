package sia.backendtest.util

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.Polygon
import sia.backendtest.dto.PointDTO


class GeometryConverter() {

    private val geometryFactory = GeometryFactory()

    fun convertPolygon(pointsDTO: List<PointDTO>): Polygon {
        val coordinates = pointsDTO.map { Coordinate(it.x, it.y) }.toTypedArray()
        val sequence = geometryFactory.coordinateSequenceFactory.create(coordinates)
        return geometryFactory.createPolygon(sequence)
    }

    fun convertPoint(lat: Double, lon: Double): Point {
        return geometryFactory.createPoint(Coordinate(lat, lon))
    }

}