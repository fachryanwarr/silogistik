package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751070.model.Gudang;
import apap.ti.silogistik2106751070.model.GudangBarang;
import apap.ti.silogistik2106751070.repository.GudangBarangDb;
import apap.ti.silogistik2106751070.repository.GudangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class GudangServiceImpl implements GudangService{
    @Autowired
    GudangDb gudangDb;

    @Autowired
    GudangBarangDb gudangBarangDb;

    @Autowired
    GudangBarangService gudangBarangService;

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

    @Override
    public long getCount() {

        return gudangDb.count();
    }

    @Override
    public void restockBarang(Gudang gudangFromDTO) {
        Gudang gudang = getGudangById(gudangFromDTO.getId());

        for (GudangBarang gudangBarangDTO : gudangFromDTO.getListGudangBarang()) {
            if (gudangBarangDTO.getId() != null && gudangBarangService.getGudangBarangById(gudangBarangDTO.getId()) != null) {
                GudangBarang gudangBarang = gudangBarangService.getGudangBarangById(gudangBarangDTO.getId());
                gudangBarang.setStok(gudangBarangDTO.getStok());
                gudangBarangDb.save(gudangBarang);
            } else {
                gudangBarangDTO.setGudang(gudang);
                gudangBarangDb.save(gudangBarangDTO);
            }
        }
    }
}
