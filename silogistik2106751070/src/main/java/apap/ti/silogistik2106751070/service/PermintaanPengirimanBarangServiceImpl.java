package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanBarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PermintaanPengirimanBarangServiceImpl implements PermintaanPengirimanBarangService {

    @Autowired
    PermintaanPengirimanBarangDb permintaanPengirimanBarangDb;

    @Override
    public void savePermintaanPengirimanBarang(PermintaanPengirimanBarang permintaanPengirimanBarang) {
        permintaanPengirimanBarangDb.save(permintaanPengirimanBarang);
    }

    @Override
    public List<PermintaanPengirimanBarang> getAllPermintaanPengirimanBarang() {
        return permintaanPengirimanBarangDb.findAll();
    }

    @Override
    public PermintaanPengirimanBarang getPermintaanPengirimanBarangById(BigInteger id) {
        for (PermintaanPengirimanBarang permintaanPengirimanBarang : getAllPermintaanPengirimanBarang()) {
            if (permintaanPengirimanBarang.getId().equals(id)) {
                return permintaanPengirimanBarang;
            }
        }

        return null;
    }
}
