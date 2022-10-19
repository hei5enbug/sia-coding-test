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
        val expected = Region(
            name = "인천시",
            area = GeometryConverter().convertPolygon(
                listOf(
                    PointDTO(126.575759, 37.626045),
                    PointDTO(126.787484, 37.580330),
                    PointDTO(126.770138, 37.430966),
                    PointDTO(126.738636, 37.392667),
                    PointDTO(126.577265, 37.389092),
                    PointDTO(126.575759, 37.626045),
                )
            )
        )
        val result = regionRepository.save(expected)

        assertEquals(expected, result)
    }

}