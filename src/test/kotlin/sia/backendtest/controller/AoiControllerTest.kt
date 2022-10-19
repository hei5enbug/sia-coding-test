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
import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.AoiResponseDTO
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.PointDTO
import sia.backendtest.service.AoiService
import java.nio.charset.StandardCharsets

@WebMvcTest(AoiController::class)
internal class AoiControllerTest {

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
    fun postAoiTest() {
        val body = AoiRequestDTO(
            name = "인천대학교", area = listOf(
                PointDTO(126.637633, 37.376078),
                PointDTO(126.632383, 37.379237),
                PointDTO(126.628187, 37.375380),
                PointDTO(126.633852, 37.372120),
                PointDTO(126.637633, 37.376078),
            )
        )

        val expected = IdResponseDTO(id = 3)
        given(aoiService.insertAoi(any())).willReturn(expected)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/aois").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        ).andExpect(MockMvcResultMatchers.status().isOk).andExpect(
            MockMvcResultMatchers.content().string(
                objectMapper.writeValueAsString(expected)
            )
        )
    }

    @Test
    fun getAoiByPointTest() {

        val lat = 126.3
        val lon = 37.2
        val expected = AoiResponseDTO(
            id = 10, name = "인천대학교", area = listOf(
                PointDTO(126.637633, 37.376078),
                PointDTO(126.632383, 37.379237),
                PointDTO(126.628187, 37.375380),
                PointDTO(126.633852, 37.372120),
                PointDTO(126.637633, 37.376078),
            )
        )

        given(aoiService.findNearByPoint(lat, lon)).willReturn(expected)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/aois?lat=${lat}&long=${lon}")
                .accept(MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        )
            .andExpect(MockMvcResultMatchers.status().isOk).andExpect(
                MockMvcResultMatchers.content().string(
                    objectMapper.writeValueAsString(expected)
                )
            )
    }

}