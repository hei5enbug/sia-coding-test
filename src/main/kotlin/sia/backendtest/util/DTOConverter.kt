package sia.backendtest.util

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.PointDTO
import sia.backendtest.dto.RegionRequestDTO
import sia.backendtest.entity.Aoi
import sia.backendtest.entity.Region


class DTOConverter {

    fun convertRegion(regionDTO: RegionRequestDTO): Region {
        val area = convertPolygon(regionDTO.area)
        return Region(name = regionDTO.name, area = area)
    }

    fun convertAoi(aoiRequestDTO: AoiRequestDTO): Aoi {
        val area = convertPolygon(aoiRequestDTO.area)
        return Aoi(name = aoiRequestDTO.name, area = area)
    }

    fun convertPolygon(pointsDTO: List<PointDTO>): Polygon {
        val geometryFactory = GeometryFactory()
        val coordinates = pointsDTO.map { Coordinate(it.x, it.y) }.toTypedArray()
        val sequence = geometryFactory.coordinateSequenceFactory.create(coordinates)
        return geometryFactory.createPolygon(sequence)
    }

}