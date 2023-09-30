package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Karyawan;

import java.math.BigInteger;
import java.util.List;

public interface KaryawanService {
    void saveKaryawan(Karyawan karyawan);

    List<Karyawan> getAllKaryawan();

    Karyawan getKaryawanById(BigInteger id);
}
