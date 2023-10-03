package apap.ti.silogistik2106751070.repository;

import apap.ti.silogistik2106751070.model.GudangBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GudangBarangDb extends JpaRepository<GudangBarang, Long> {
    @Query("SELECT SUM(gb.stok) FROM GudangBarang gb WHERE gb.barang.sku = :skuBarang")
    Long getTotalStokBySkuBarang(@Param("skuBarang") String skuBarang);
}
