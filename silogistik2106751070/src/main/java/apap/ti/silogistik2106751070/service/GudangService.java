package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Gudang;

import java.util.List;

public interface GudangService {
    void saveGudang(Gudang gudang);

    List<Gudang> getAllGudang();

    Gudang getGudangById(Long id);

    long getCount();

    void restockBarang(Gudang gudang);
}
