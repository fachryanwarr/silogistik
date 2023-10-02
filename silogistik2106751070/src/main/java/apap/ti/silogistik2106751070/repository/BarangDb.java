package apap.ti.silogistik2106751070.repository;

import apap.ti.silogistik2106751070.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BarangDb extends JpaRepository<Barang, String> {
    @Query("SELECT b.sku FROM Barang b")
    List<String> findAllSkus();

    List<Barang> findAllByOrderByMerk();

    long countByTipeBarang(Integer num);
}
