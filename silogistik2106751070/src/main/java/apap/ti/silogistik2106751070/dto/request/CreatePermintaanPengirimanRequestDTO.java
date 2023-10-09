package apap.ti.silogistik2106751070.dto.request;

import apap.ti.silogistik2106751070.model.Karyawan;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermintaanPengirimanRequestDTO {
    @Size(max = 16)
    private String nomorPengiriman;

    @NotNull
    @NotBlank(message = "Nama penerima tidak boleh kosong")
    private String namaPenerima;

    @NotNull
    @NotBlank(message = "Alamat penerima tidak boleh kosong")
    private String alamatPenerima;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tanggalPengiriman;

    @NotNull
    @Positive(message = "Biaya pengiriman harus positif")
    private Integer biayaPengiriman;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    private Integer jenisLayanan;

    private Date waktuPermintaan;

    @NotNull
    private Karyawan karyawan;

    @Size(min = 1, message = "Permintaan pengiriman harus menyertakan barang!")
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = new ArrayList<>();
}
