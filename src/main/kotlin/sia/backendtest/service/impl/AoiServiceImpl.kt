package sia.backendtest.service.impl

import org.springframework.stereotype.Service
import sia.backendtest.dto.AoiRequestDTO
import sia.backendtest.repository.AoiRepository
import sia.backendtest.service.AoiService
import sia.backendtest.util.DTOConverter

@Service
class AoiServiceImpl(private val aoiRepository: AoiRepository) : AoiService {

    override fun insertAoi(aoiRequestDTO: AoiRequestDTO): Int {
        val aoi = DTOConverter().convertAoi(aoiRequestDTO)
        return aoiRepository.save(aoi).id
    }

}