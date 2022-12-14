package sia.backendtest.service

import sia.backendtest.dto.IdResponseDTO
import sia.backendtest.dto.RegionRequestDTO

interface RegionService {

    fun insertRegion(regionRequestDTO: RegionRequestDTO): IdResponseDTO

}