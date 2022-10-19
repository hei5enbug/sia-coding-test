package sia.backendtest.entity


import org.locationtech.jts.geom.Polygon
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Aoi(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    name: String,
    area: Polygon
) : Area(name, area)