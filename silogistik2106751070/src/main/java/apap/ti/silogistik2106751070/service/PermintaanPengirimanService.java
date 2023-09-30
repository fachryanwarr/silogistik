package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.model.PermintaanPengiriman;

import java.math.BigInteger;
import java.util.List;

public interface PermintaanPengirimanService {
    void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman);

    List<PermintaanPengiriman> getAllPermintaanPengiriman();

    PermintaanPengiriman getPermintaanPengirimanById(BigInteger id);
}
