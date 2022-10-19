package sia.backendtest.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import sia.backendtest.dto.*
import sia.backendtest.entity.Aoi
import sia.backendtest.entity.Region
import sia.backendtest.repository.AoiRepository
import sia.backendtest.repository.RegionRepository
import sia.backendtest.service.impl.RegionServiceImpl
import sia.backendtest.util.GeometryConverter

@ExtendWith(MockitoExtension::class)
internal class RegionServiceTest {

    @Mock
    private lateinit var regionRepository: RegionRepository

    @Mock
    private lateinit var aoiRepository: AoiRepository

    @InjectMocks
    private lateinit var regionService: RegionServiceImpl


    @Test
    internal fun insertRegionTest() {
        val regionRequestDTO = RegionRequestDTO(
            name = "인천대학교",
            area = listOf(
                PointDTO(126.637633, 37.376078),
                PointDTO(126.632383, 37.379237),
                PointDTO(126.628187, 37.375380),
                PointDTO(126.633852, 37.372120),
                PointDTO(126.637633, 37.376078),
            )
        )
        val region = Region(
            id = 5,
            name = regionRequestDTO.name,
            area = GeometryConverter().convertPolygon(regionRequestDTO.area)
        )

        given(regionRepository.save(any())).willReturn(region)
        val result = regionService.insertRegion(regionRequestDTO)

        assertEquals(IdResponseDTO(region.id), result)
    }

    @Test
    internal fun findAoisByRegionIdTest() {
        val expected = RegionIntersectResponseDTO(
            listOf(
                AoiResponseDTO(
                    id = 2, name = "북한산", area = listOf(
                        PointDTO(126.637633, 37.376078),
                        PointDTO(126.632383, 37.379237),
                        PointDTO(126.628187, 37.375380),
                        PointDTO(126.633852, 37.372120),
                        PointDTO(126.637633, 37.376078)
                    )
                )
            )
        )
        val aois = expected.aois.map {
            Aoi(id = it.id, name = it.name, area = GeometryConverter().convertPolygon(it.area))
        }
        given(aoiRepository.findByRegionId(anyInt())).willReturn(aois)

        val result = regionService.findAoisByRegionId(1)
        assertEquals(expected, result)
    }

}