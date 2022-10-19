package sia.backendtest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import sia.backendtest.entity.Aoi


interface AoiRepository : JpaRepository<Aoi, Long> {

    @Query(
        value =
        """
        SELECT a.id, a.name, a.area
        FROM aoi a, region b
        WHERE b.id = :id
            AND st_intersects(a.area, b.area)
        """, nativeQuery = true
    )
    fun findByRegionId(id: Int): List<Aoi>

}