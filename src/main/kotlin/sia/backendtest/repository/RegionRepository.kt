package sia.backendtest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sia.backendtest.entity.Region

@Repository
interface RegionRepository : JpaRepository<Region, Long>