package apap.ti.silogistik2106751070.repository;

import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermintaanPengirimanDb extends JpaRepository<PermintaanPengiriman, Long> {
    List<PermintaanPengiriman> findAllByOrderByWaktuPermintaanDesc();
}
