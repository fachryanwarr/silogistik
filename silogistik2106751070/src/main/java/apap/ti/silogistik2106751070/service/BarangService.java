package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Barang;

import java.util.List;
import java.util.Map;

public interface BarangService {
    void saveBarang(Barang barang);

    List<Barang> getAllBarang();

    List<Barang> getAllBarangSortedByMerk();

    Map<Barang, Long> getAllBarangWithTotalStok();

    Barang getBarangBySku(String sku);

    List<String> getAllSku();

    long getCount();

    String getNamaTipeBarang(Integer tipeBarang);

    long getNextNumForSKU(Integer tipeBarang);

    void updateBarang(Barang barang);
}
