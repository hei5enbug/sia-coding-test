package sia.backendtest.entity


import org.locationtech.jts.geom.Polygon
import javax.persistence.Column
import javax.persistence.MappedSuperclass


@MappedSuperclass
class Area(
    val name: String,
    @Column(columnDefinition = "geography")
    val area: Polygon
)

