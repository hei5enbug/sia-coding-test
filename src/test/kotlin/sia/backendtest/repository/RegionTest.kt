package sia.backendtest.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import sia.backendtest.dto.PointDTO
import sia.backendtest.entity.Region
import sia.backendtest.util.GeometryConverter

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class RegionTest {

    @Autowired
    private lateinit var regionRepository: RegionRepository

    @Test
    fun saveRegion() {

        val pointList = listOf(
            PointDTO(126.637633, 37.376078),
            PointDTO(126.632383, 37.379237),
            PointDTO(126.628187, 37.375380),
            PointDTO(126.633852, 37.372120),
            PointDTO(126.637633, 37.376078),
        )
        val region = Region(
            name = "인천대학교",
            area = GeometryConverter().convertPolygon(pointList)
        )
        val savedRegion = regionRepository.save(region)

        assertEquals(region.id, savedRegion.id)
    }

}