package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.GudangBarang;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface GudangBarangService {
    void saveGudangBarang(GudangBarang gudangBarang);

    void saveAllGudangBarang(List<GudangBarang> listGudangBarang);

    List<GudangBarang> getAllGudangBarang();

    GudangBarang getGudangBarangById(Long id);

    Long getTotalStokBySku(String sku);

}
