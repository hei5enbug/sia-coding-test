package sia.backendtest.repository

import org.locationtech.jts.geom.Point
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
    fun findAllByRegionId(id: Int): List<Aoi>

    @Query(
        value =
        """
        SELECT a.id, a.name, a.area, b.distance
        FROM aoi a
        JOIN (
            SELECT
                id,
                ST_DistanceSphere(
                    area,
                    :point
                ) AS distance
            FROM aoi
        ) AS b
        ON a.id = b.id
        ORDER BY b.distance
        LIMIT 1
        """, nativeQuery = true
    )
    fun findNearByPoint(point: Point): Aoi

}