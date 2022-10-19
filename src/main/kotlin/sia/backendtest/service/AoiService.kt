package sia.backendtest.service

import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.dto.AoiResponseDTO

interface AoiService {
    fun insertAoi(aoiRequestDTO: AoiRequestDTO): Int
    fun findNearByPoint(lat: Double, lon: Double): AoiResponseDTO

}