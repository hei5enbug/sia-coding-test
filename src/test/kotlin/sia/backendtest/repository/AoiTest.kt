package sia.backendtest.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import sia.backendtest.dto.PointDTO
import sia.backendtest.entity.Aoi
import sia.backendtest.entity.Region
import sia.backendtest.util.GeometryConverter


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class AoiTest {

    @Autowired
    private lateinit var regionRepository: RegionRepository

    @Autowired
    private lateinit var aoiRepository: AoiRepository

    @BeforeEach
    fun setUp(){
        regionRepository.deleteAll()
        aoiRepository.deleteAll()
    }

    @Test
    fun saveTest() {
        val expected = Aoi(
            name = "인천대학교",
            area = GeometryConverter().convertPolygon(
                listOf(
                    PointDTO(126.637633, 37.376078),
                    PointDTO(126.632383, 37.379237),
                    PointDTO(126.628187, 37.375380),
                    PointDTO(126.633852, 37.372120),
                    PointDTO(126.637633, 37.376078),
                )
            )
        )
        val result = aoiRepository.save(expected)

        assertEquals(expected.name, result.name)
    }

    @Test
    fun findAllByRegionIdTest() {
        val region = Region(
            name = "인천시",
            area = GeometryConverter().convertPolygon(
                listOf(
                    PointDTO(126.575759, 37.626045),
                    PointDTO(126.787484, 37.580330),
                    PointDTO(126.770138, 37.430966),
                    PointDTO(126.673702, 37.334926),
                    PointDTO(126.577265, 37.389092),
                    PointDTO(126.575759, 37.626045),
                )
            )
        )
        val aoi = Aoi(
            name = "인천대학교",
            area = GeometryConverter().convertPolygon(
                listOf(
                    PointDTO(126.637633, 37.376078),
                    PointDTO(126.632383, 37.379237),
                    PointDTO(126.628187, 37.375380),
                    PointDTO(126.633852, 37.372120),
                    PointDTO(126.637633, 37.376078),
                )
            )
        )

        val expected = aoiRepository.save(aoi)
        val regionId = regionRepository.save(region).id
        val result = aoiRepository.findAllByRegionId(regionId)

        assertEquals(expected.id, result[0].id)
    }

    @Test
    fun findNearByPointTest() {
        val expected = Aoi(
            name = "인천대학교",
            area = GeometryConverter().convertPolygon(
                listOf(
                    PointDTO(126.637633, 37.376078),
                    PointDTO(126.632383, 37.379237),
                    PointDTO(126.628187, 37.375380),
                    PointDTO(126.633852, 37.372120),
                    PointDTO(126.637633, 37.376078),
                )
            )
        )

        aoiRepository.save(expected)
        val point = GeometryConverter().convertPoint(126.3, 37.2)
        val result = aoiRepository.findNearByPoint(point)

        assertEquals(expected.name, result.name)
    }

}