package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.GudangBarang;
import apap.ti.silogistik2106751070.repository.GudangBarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class GudangBarangServiceImpl implements GudangBarangService{
    @Autowired
    GudangBarangDb gudangBarangDb;

    @Override
    public void saveGudangBarang(GudangBarang gudangBarang) {
        gudangBarangDb.save(gudangBarang);
    }

    @Override
    public List<GudangBarang> getAllGudangBarang() {
        return gudangBarangDb.findAll();
    }

    @Override
    public GudangBarang getGudangBarangById(BigInteger id) {
        for (GudangBarang gudangBarang: getAllGudangBarang()) {
            if (gudangBarang.getId().equals(id)) {
                return gudangBarang;
            }
        }

        return null;
    }
}
