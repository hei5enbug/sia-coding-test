package sia.backendtest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sia.backendtest.dto.RegionIntersectResponseDTO
import sia.backendtest.dto.RegionRequestDTO
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.service.RegionService

@RestController
class RegionController(
    private val regionService: RegionService,
) {

    @PostMapping("/regions")
    fun insertRegion(@RequestBody regionRequestDTO: RegionRequestDTO): IdResponseDTO {
        return regionService.insertRegion(regionRequestDTO)
    }

    @GetMapping("/regions/{id}/aois/intersects")
    fun getRegionIntersectedAois(@PathVariable(name = "id") id: Int): RegionIntersectResponseDTO {
        return regionService.findAoisByRegionId(id)
    }

}