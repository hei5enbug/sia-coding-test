package sia.backendtest.service

import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.AoiResponseDTO
import sia.backendtest.dto.IdResponseDTO

interface AoiService {
    fun insertAoi(aoiRequestDTO: AoiRequestDTO): IdResponseDTO
    fun findNearByPoint(lat: Double, lon: Double): AoiResponseDTO

}