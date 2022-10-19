package sia.backendtest.service.impl

import org.springframework.stereotype.Service
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.RegionRequestDTO
import sia.backendtest.entity.Region
import sia.backendtest.repository.RegionRepository
import sia.backendtest.service.RegionService
import sia.backendtest.util.GeometryConverter

@Service
class RegionServiceImpl(
    private val regionRepository: RegionRepository
) : RegionService {

    override fun insertRegion(regionRequestDTO: RegionRequestDTO): IdResponseDTO {
        val region = convertDtoToRegion(regionRequestDTO)
        return regionRepository.save(region).toIdResponseDto()
    }

    private fun convertDtoToRegion(regionDTO: RegionRequestDTO): Region {
        return Region(name = regionDTO.name, area = GeometryConverter().convertPolygon(regionDTO.area))
    }

}