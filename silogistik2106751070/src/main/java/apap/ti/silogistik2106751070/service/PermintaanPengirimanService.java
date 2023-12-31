package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106751070.exception.StokKurangException;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;

import java.util.Date;
import java.util.List;

public interface PermintaanPengirimanService {
    void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) throws StokKurangException;

    List<PermintaanPengiriman> getAllPermintaanPengiriman();

    List<PermintaanPengiriman> getAllPermintaanPengirimanOrderByWaktu();

    List<ReadPermintaanPengirimanResponseDTO> getAllPermintaanPengirimanFormatted();

    PermintaanPengiriman getPermintaanPengirimanById(Long id);

    long getCount();

    String generateNomorPengiriman(CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO);

    void cancelPermintaan(PermintaanPengiriman permintaanPengiriman);

    List<ReadPermintaanPengirimanResponseDTO> getPermintaanPengirimanFiltered(Date startdate, Date endDate, Barang barang);
}
