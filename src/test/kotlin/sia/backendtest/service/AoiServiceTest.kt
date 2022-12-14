package sia.backendtest.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import sia.backendtest.dto.*
import sia.backendtest.entity.Aoi
import sia.backendtest.repository.AoiRepository
import sia.backendtest.service.impl.AoiServiceImpl
import sia.backendtest.util.GeometryConverter

@ExtendWith(MockitoExtension::class)
internal class AoiServiceTest {

    @Mock
    private lateinit var aoiRepository: AoiRepository

    @InjectMocks
    private lateinit var aoiService: AoiServiceImpl

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    private val testName = "인천대학교"
    private val testArea = listOf(
        PointDTO(126.637633, 37.376078),
        PointDTO(126.632383, 37.379237),
        PointDTO(126.628187, 37.375380),
        PointDTO(126.633852, 37.372120),
        PointDTO(126.637633, 37.376078),
    )


    @Test
    fun insertAoiTest() {
        val aoiRequestDTO = AoiRequestDTO(name = testName, area = testArea)
        val aoi = Aoi(
            id = 10,
            name = aoiRequestDTO.name,
            area = GeometryConverter().convertPolygon(aoiRequestDTO.area)
        )

        given(aoiRepository.save(any())).willReturn(aoi)
        val result = aoiService.insertAoi(aoiRequestDTO)

        assertEquals(IdResponseDTO(aoi.id), result)
    }

    @Test
    fun findByRegionIdTest() {
        val expected = RegionIntersectResponseDTO(
            listOf(AoiResponseDTO(id = 10, name = testName, area = testArea))
        )
        val aois = expected.aois.map {
            Aoi(id = it.id, name = it.name, area = GeometryConverter().convertPolygon(it.area))
        }
        val regionId = 5

        given(aoiRepository.findAllByRegionId(regionId)).willReturn(aois)
        val result = aoiService.findByRegionId(regionId)

        assertEquals(expected, result)
    }

    @Test
    fun findNearByPoint() {
        val aoiRequestDTO = AoiRequestDTO(name = testName, area = testArea)
        val expected = Aoi(
            id = 10,
            name = aoiRequestDTO.name,
            area = GeometryConverter().convertPolygon(aoiRequestDTO.area)
        )
        given(aoiRepository.findNearByPoint(any())).willReturn(expected)
        val result = aoiService.findNearByPoint(126.3, 37.2)

        assertEquals(expected.toAoiResponseDto(), result)
    }

}