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
import sia.backendtest.service.AoiService
import sia.backendtest.service.RegionService
import java.nio.charset.StandardCharsets

@WebMvcTest(RegionController::class)
internal class RegionControllerTest {

    @MockBean
    private lateinit var regionService: RegionService

    @MockBean
    private lateinit var aoiService: AoiService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @Test
    fun postRegionTest() {
        val body = RegionRequestDTO(
            name = "인천시", area = listOf(
                PointDTO(126.575759, 37.626045),
                PointDTO(126.787484, 37.580330),
                PointDTO(126.770138, 37.430966),
                PointDTO(126.738636, 37.392667),
                PointDTO(126.577265, 37.389092),
                PointDTO(126.575759, 37.626045),
            )
        )

        val expected = IdResponseDTO(id = 3)
        given(regionService.insertRegion(any())).willReturn(expected)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/regions").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        ).andExpect(MockMvcResultMatchers.status().isOk).andExpect(
            MockMvcResultMatchers.content().string(
                objectMapper.writeValueAsString(expected)
            )
        )
    }

    @Test
    fun getRegionIntersectedAoisTest() {
        val expected = RegionIntersectResponseDTO(
            listOf(
                AoiResponseDTO(
                    id = 1, name = "인천대학교", area = listOf(
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
        given(aoiService.findByRegionId(regionId)).willReturn(expected)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/regions/${regionId}/aois/intersects")
                .accept(MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        )
            .andExpect(MockMvcResultMatchers.status().isOk).andExpect(
                MockMvcResultMatchers.content().string(
                    objectMapper.writeValueAsString(expected)
                )
            )
    }

}