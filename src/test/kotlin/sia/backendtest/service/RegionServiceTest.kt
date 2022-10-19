package sia.backendtest.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.PointDTO
import sia.backendtest.dto.RegionRequestDTO
import sia.backendtest.entity.Region
import sia.backendtest.repository.RegionRepository
import sia.backendtest.service.impl.RegionServiceImpl
import sia.backendtest.util.GeometryConverter

@ExtendWith(MockitoExtension::class)
internal class RegionServiceTest {

    @Mock
    private lateinit var regionRepository: RegionRepository

    @InjectMocks
    private lateinit var regionService: RegionServiceImpl

    @Test
    internal fun insertRegionTest() {
        val regionRequestDTO = RegionRequestDTO(
            name = "인천시", area = listOf(
                PointDTO(126.575759, 37.626045),
                PointDTO(126.787484, 37.580330),
                PointDTO(126.770138, 37.430966),
                PointDTO(126.738636, 37.392667),
                PointDTO(126.577265, 37.389092),
                PointDTO(126.575759, 37.626045),
            )
        )
        val region = Region(
            id = 5, name = regionRequestDTO.name, area = GeometryConverter().convertPolygon(regionRequestDTO.area)
        )

        given(regionRepository.save(any())).willReturn(region)
        val result = regionService.insertRegion(regionRequestDTO)

        assertEquals(IdResponseDTO(region.id), result)
    }

}