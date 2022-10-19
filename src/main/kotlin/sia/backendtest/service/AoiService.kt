package sia.backendtest.service

import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.AoiResponseDTO
import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.RegionIntersectResponseDTO

interface AoiService {
    fun insertAoi(aoiRequestDTO: AoiRequestDTO): IdResponseDTO

    fun findByRegionId(id: Int): RegionIntersectResponseDTO

    fun findNearByPoint(lat: Double, lon: Double): AoiResponseDTO

}