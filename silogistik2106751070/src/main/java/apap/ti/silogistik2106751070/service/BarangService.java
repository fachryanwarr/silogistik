package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Barang;

import java.util.List;

public interface BarangService {
    void saveBarang(Barang barang);

    List<Barang> getAllBarang();

    Barang getBarangBySku(String sku);
}
