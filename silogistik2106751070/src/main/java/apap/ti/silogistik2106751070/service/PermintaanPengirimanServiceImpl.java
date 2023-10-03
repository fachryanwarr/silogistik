package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService{

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Override
    public void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengirimanDb.save(permintaanPengiriman);
    }

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengiriman() {
        return permintaanPengirimanDb.findAll();
    }

    @Override
    public PermintaanPengiriman getPermintaanPengirimanById(Long id) {
        for (PermintaanPengiriman permintaanPengiriman: getAllPermintaanPengiriman()) {
            if (permintaanPengiriman.getId().equals(id)) {
                return permintaanPengiriman;
            }
        }

        return null;
    }

    @Override
    public long getCount() {
        return permintaanPengirimanDb.count();
    }
}
