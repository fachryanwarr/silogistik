package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Gudang;
import apap.ti.silogistik2106751070.repository.GudangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class GudangServiceImpl implements GudangService{
    @Autowired
    GudangDb gudangDb;

    @Override
    public void saveGudang(Gudang gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public List<Gudang> getAllGudang() {
        return gudangDb.findAll();
    }

    @Override
    public Gudang getGudangById(BigInteger id) {
        for (Gudang gudang: getAllGudang()) {
            if (gudang.getId().equals(id)) {
                return gudang;
            }
        }

        return null;
    }
}
