package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.Karyawan;

import java.util.List;

public interface KaryawanService {
    void saveKaryawan(Karyawan karyawan);

    List<Karyawan> getAllKaryawan();

    Karyawan getKaryawanById(Long id);

    long getCount();
}
