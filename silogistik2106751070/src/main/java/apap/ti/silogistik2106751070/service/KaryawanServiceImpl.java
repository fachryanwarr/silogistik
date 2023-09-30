package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Karyawan;
import apap.ti.silogistik2106751070.repository.KaryawanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class KaryawanServiceImpl implements KaryawanService{

    @Autowired
    KaryawanDb karyawanDb;

    @Override
    public void saveKaryawan(Karyawan karyawan) {
        karyawanDb.save(karyawan);
    }

    @Override
    public List<Karyawan> getAllKaryawan() {
        return karyawanDb.findAll();
    }

    @Override
    public Karyawan getKaryawanById(BigInteger id) {
        for (Karyawan karyawan: getAllKaryawan()) {
            if (karyawan.getId().equals(id)) {
                return karyawan;
            }
        }

        return null;
    }
}
