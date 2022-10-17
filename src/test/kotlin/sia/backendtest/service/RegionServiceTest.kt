package sia.backendtest.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import sia.backendtest.model.Region
import sia.backendtest.repository.RegionRepository
import sia.backendtest.service.impl.RegionServiceImpl

@ExtendWith(MockitoExtension::class)
internal class RegionServiceTest {

    @Mock
    lateinit var regionRepository: RegionRepository

    @InjectMocks
    lateinit var regionService: RegionServiceImpl

    @Test
    internal fun insertRegion() {
        val region = createRegion()
        given(regionRepository.save(region)).willReturn(region)
        val id = regionService.insertRegion(region)

        assertEquals(id, region.id)
    }

    private fun createRegion(): Region {
        val coordinates = arrayOf(
            Coordinate(126.637633, 37.376078),
            Coordinate(126.632383, 37.379237),
            Coordinate(126.628187, 37.375380),
            Coordinate(126.633852, 37.372120),
            Coordinate(126.637633, 37.376078)
        )
        val geometryFactory = GeometryFactory()
        val sequence = geometryFactory.coordinateSequenceFactory.create(coordinates)
        val polygon = geometryFactory.createPolygon(sequence)
        return Region(id = 10, name = "인천대학교", area = polygon)
    }
}