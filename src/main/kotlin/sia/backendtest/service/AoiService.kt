package sia.backendtest.service

import sia.backendtest.dto.AoiRequestDTO

interface AoiService {
    fun insertAoi(aoiRequestDTO: AoiRequestDTO): Int
}