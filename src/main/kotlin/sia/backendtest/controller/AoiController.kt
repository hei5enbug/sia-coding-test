package sia.backendtest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.AoiResponseDTO
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.service.AoiService

@RestController
class AoiController(
    private val aoiService: AoiService
) {

    @PostMapping("/aois")
    fun postAoi(@RequestBody aoiRequestDTO: AoiRequestDTO): IdResponseDTO {
        val id = aoiService.insertAoi(aoiRequestDTO)
        return IdResponseDTO(id)
    }

    @GetMapping("/aois")
    fun getAoiByPoint(
        @RequestParam(name = "lat") lat: Double,
        @RequestParam(name = "long") lon: Double
    ): AoiResponseDTO {
        return aoiService.findNearByPoint(lat, lon)
    }

}