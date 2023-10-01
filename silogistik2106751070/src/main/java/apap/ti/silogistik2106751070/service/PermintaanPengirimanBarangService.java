package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;

import java.math.BigInteger;
import java.util.List;

public interface PermintaanPengirimanBarangService {
    void savePermintaanPengirimanBarang(PermintaanPengirimanBarang permintaanPengirimanBarang);

    List<PermintaanPengirimanBarang> getAllPermintaanPengirimanBarang();

    PermintaanPengirimanBarang getPermintaanPengirimanBarangById(BigInteger id);
}