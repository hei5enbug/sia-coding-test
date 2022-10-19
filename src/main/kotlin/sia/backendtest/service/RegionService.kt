package sia.backendtest.service

import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.RegionIntersectResponseDTO
import sia.backendtest.dto.RegionRequestDTO

interface RegionService {
    fun insertRegion(regionRequestDTO: RegionRequestDTO): IdResponseDTO
    fun findAoisByRegionId(id: Int): RegionIntersectResponseDTO
}