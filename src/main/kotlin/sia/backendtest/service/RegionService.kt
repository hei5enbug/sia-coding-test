package sia.backendtest.service

import sia.backendtest.dto.RegionIntersectResponseDTO
import sia.backendtest.dto.RegionRequestDTO

interface RegionService {
    fun insertRegion(regionRequestDTO: RegionRequestDTO): Int
    fun findAoisByRegionId(id: Int): RegionIntersectResponseDTO
}