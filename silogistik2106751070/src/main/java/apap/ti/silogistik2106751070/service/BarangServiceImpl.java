package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.repository.BarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarangServiceImpl implements BarangService {
    @Autowired
    BarangDb barangDb;

    @Override
    public void saveBarang(Barang barang) {
        barangDb.save(barang);
    }

    @Override
    public List<Barang> getAllBarang() {
        return barangDb.findAll();
    }

    @Override
    public List<Barang> getAllBarangSortedByMerk() {
        return barangDb.findAllByOrderByMerk();
    }

    @Override
    public Barang getBarangBySku(String sku) {
        for (Barang barang : getAllBarang()) {
            if (barang.getSku().equals(sku)) {
                return barang;
            }
        }

        return null;
    }

    @Override
    public List<String> getAllSku() {
        return barangDb.findAllSkus();
    }

    @Override
    public long getCount() {
        return barangDb.count();
    }

    @Override
    public String getNamaTipeBarang(Integer tipeBarang) {
        return switch (tipeBarang) {
            case 1 -> "ELEC";
            case 2 -> "CLOT";
            case 3 -> "FOOD";
            case 4 -> "COSM";
            default -> "TOOL";
        };
    }

    @Override
    public long getNextNumForSKU(Integer tipeBarang) {
        return 1 + barangDb.countByTipeBarang(tipeBarang);
    }

    @Override
    public void updateBarang(Barang barangFromDTO) {
        Barang barang = getBarangBySku(barangFromDTO.getSku());

        barang.setHarga(barangFromDTO.getHarga());
        barang.setMerk(barangFromDTO.getMerk());
        barangDb.save(barang);
    }
}
