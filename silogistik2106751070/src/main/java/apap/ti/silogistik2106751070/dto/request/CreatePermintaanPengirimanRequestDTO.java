package apap.ti.silogistik2106751070.dto.request;

import apap.ti.silogistik2106751070.model.Karyawan;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermintaanPengirimanRequestDTO {
    @Size(max = 16)
    private String nomorPengiriman;

    @NotNull
    private String namaPenerima;

    @NotNull
    private String alamatPenerima;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tanggalPengiriman;

    @NotNull
    private Integer biayaPengiriman;

    @NotNull
    private Integer jenisLayanan;

    private Date waktuPermintaan;

    @NotNull
    private Karyawan karyawan;

    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang;
}
