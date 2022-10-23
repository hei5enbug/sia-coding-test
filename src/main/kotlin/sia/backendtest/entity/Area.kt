package sia.backendtest.entity


import org.locationtech.jts.geom.Polygon
import sia.backendtest.dto.PointDTO
import javax.persistence.Column
import javax.persistence.MappedSuperclass


@MappedSuperclass
class Area(
    val name: String,
    @Column(columnDefinition = "geometry")
    val area: Polygon
) {
    fun convertPolygonToDto(): List<PointDTO> {
        return area.coordinates.map { PointDTO(it.x, it.y) }
    }
}

