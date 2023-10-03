package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.GudangBarang;

import java.util.List;

public interface GudangBarangService {
    void saveGudangBarang(GudangBarang gudangBarang);

    List<GudangBarang> getAllGudangBarang();

    GudangBarang getGudangBarangById(Long id);

    Long getTotalStokBySku(String sku);
}
