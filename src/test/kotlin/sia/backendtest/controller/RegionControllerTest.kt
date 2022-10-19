package sia.backendtest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import sia.backendtest.dto.*
import sia.backendtest.entity.Region
import sia.backendtest.service.RegionService
import sia.backendtest.util.DTOConverter
import java.nio.charset.StandardCharsets

@WebMvcTest(RegionController::class)
internal class RegionControllerTest {

    @MockBean
    private lateinit var regionService: RegionService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @Test
    fun insertRegionTest() {
        val body = RegionRequestDTO(
            name = "인천대학교", area = listOf(
                PointDTO(126.637633, 37.376078),
                PointDTO(126.632383, 37.379237),
                PointDTO(126.628187, 37.375380),
                PointDTO(126.633852, 37.372120),
                PointDTO(126.637633, 37.376078),
            )
        )
        val region = Region(
            id = 3, name = body.name, area = DTOConverter().convertPolygon(body.area)
        )

        given(regionService.insertRegion(any())).willReturn(region.id)
        val response = IdResponseDTO(region.id)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/regions").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        ).andExpect(MockMvcResultMatchers.status().isOk).andExpect(
            MockMvcResultMatchers.content().string(
                objectMapper.writeValueAsString(response)
            )
        )
    }

    @Test
    fun getRegionIntersectedAoisTest() {
        val response = RegionIntersectResponseDTO(
            listOf(
                AoiResponseDTO(
                    id = 1, name = "북한산", area = listOf(
                        PointDTO(126.637633, 37.376078),
                        PointDTO(126.632383, 37.379237),
                        PointDTO(126.628187, 37.375380),
                        PointDTO(126.633852, 37.372120),
                        PointDTO(126.637633, 37.376078),
                    )
                )
            )
        )
        val regionId = 1
        given(regionService.findAoisByRegionId(regionId)).willReturn(response)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/regions/${regionId}/aois/intersects")
                .accept(MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        )
            .andExpect(MockMvcResultMatchers.status().isOk).andExpect(
                MockMvcResultMatchers.content().string(
                    objectMapper.writeValueAsString(response)
                )
            )
    }

}