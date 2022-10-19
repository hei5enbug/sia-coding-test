package sia.backendtest.service.impl

import org.springframework.stereotype.Service
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.RegionIntersectResponseDTO
import sia.backendtest.dto.RegionRequestDTO
import sia.backendtest.entity.Region
import sia.backendtest.repository.AoiRepository
import sia.backendtest.repository.RegionRepository
import sia.backendtest.service.RegionService
import sia.backendtest.util.GeometryConverter

@Service
class RegionServiceImpl(
    private val regionRepository: RegionRepository,
    private val aoiRepository: AoiRepository
) : RegionService {

    override fun insertRegion(regionRequestDTO: RegionRequestDTO): IdResponseDTO {
        val region = convertDtoToRegion(regionRequestDTO)
        return regionRepository.save(region).toIdResponseDto()
    }

    override fun findByRegionId(id: Int): RegionIntersectResponseDTO {
        val aois = aoiRepository.findAllByRegionId(id).map { it.toAoiResponseDto() }
        return RegionIntersectResponseDTO(aois)
    }

    private fun convertDtoToRegion(regionDTO: RegionRequestDTO): Region {
        return Region(name = regionDTO.name, area = GeometryConverter().convertPolygon(regionDTO.area))
    }

}