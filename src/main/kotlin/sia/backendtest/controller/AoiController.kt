package sia.backendtest.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.service.AoiService

@RestController
class AoiController(
    private val aoiService: AoiService
) {

    @PostMapping("/aois")
    fun insertAois(@RequestBody aoiRequestDTO: AoiRequestDTO): IdResponseDTO {
        val id = aoiService.insertAoi(aoiRequestDTO)
        return IdResponseDTO(id)
    }

}