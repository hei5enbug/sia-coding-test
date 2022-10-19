package sia.backendtest.service.impl

import org.springframework.stereotype.Service
import sia.backendtest.dto.AoiResponseDTO
import sia.backendtest.dto.PointDTO
import sia.backendtest.dto.RegionIntersectAoisResponseDTO
import sia.backendtest.dto.RegionRequestDTO
import sia.backendtest.repository.AoiRepository
import sia.backendtest.repository.RegionRepository
import sia.backendtest.service.RegionService
import sia.backendtest.util.DTOConverter

@Service
class RegionServiceImpl(
    private val regionRepository: RegionRepository,
    private val aoiRepository: AoiRepository
) : RegionService {

    override fun insertRegion(regionRequestDTO: RegionRequestDTO): Int {
        val region = DTOConverter().convertRegion(regionRequestDTO)
        return regionRepository.save(region).id
    }

    override fun findAoisByRegionId(id: Int): RegionIntersectAoisResponseDTO {
        val aois = aoiRepository.findByRegionId(id).map { aoi ->
            AoiResponseDTO(
                aoi.id,
                aoi.name,
                aoi.area.coordinates.map { PointDTO(it.x, it.y) })
        }
        return RegionIntersectAoisResponseDTO(aois)
    }

}