package apap.ti.silogistik2106751070.dto.response;

import apap.ti.silogistik2106751070.model.Karyawan;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadDetailPermintaanResponseDTO extends ReadPermintaanPengirimanResponseDTO{
    private Boolean isCanceled = true;
    private Date tanggalPengiriman;
    private Integer biayaPengiriman;
    private Integer jenisLayanan;
    private Date waktuPermintaan;
    private Karyawan karyawan;
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang;
}
