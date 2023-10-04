package apap.ti.silogistik2106751070.dto.request;

import apap.ti.silogistik2106751070.model.Karyawan;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.Date;

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
    private LocalDate tanggalPengiriman;

    @NotNull
    private Integer biayaPengiriman;

    @NotNull
    private Integer jenisLayanan;

    @NotNull
    private Date waktuPermintaan;

    @NotNull
    private Karyawan karyawan;
}
