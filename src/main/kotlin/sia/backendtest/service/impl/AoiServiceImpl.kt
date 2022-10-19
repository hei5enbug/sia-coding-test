package sia.backendtest.service.impl

import org.springframework.stereotype.Service
import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.AoiResponseDTO
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.RegionIntersectResponseDTO
import sia.backendtest.entity.Aoi
import sia.backendtest.repository.AoiRepository
import sia.backendtest.service.AoiService
import sia.backendtest.util.GeometryConverter

@Service
class AoiServiceImpl(private val aoiRepository: AoiRepository) : AoiService {

    override fun insertAoi(aoiRequestDTO: AoiRequestDTO): IdResponseDTO {
        val aoi = convertDtoToAoi(aoiRequestDTO)
        return aoiRepository.save(aoi).toIdResponseDto()
    }

    override fun findByRegionId(id: Int): RegionIntersectResponseDTO {
        val aois = aoiRepository.findAllByRegionId(id).map { it.toAoiResponseDto() }
        return RegionIntersectResponseDTO(aois)
    }

    override fun findNearByPoint(lat: Double, lon: Double): AoiResponseDTO {
        val point = GeometryConverter().convertPoint(lat, lon)
        val aoi = aoiRepository.findNearByPoint(point)
        return aoi.toAoiResponseDto()
    }

    private fun convertDtoToAoi(aoiRequestDTO: AoiRequestDTO): Aoi {
        return Aoi(
            name = aoiRequestDTO.name,
            area = GeometryConverter().convertPolygon(aoiRequestDTO.area)
        )
    }

}