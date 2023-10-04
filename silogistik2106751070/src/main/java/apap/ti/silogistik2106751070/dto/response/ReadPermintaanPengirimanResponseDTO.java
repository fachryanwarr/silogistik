package apap.ti.silogistik2106751070.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadPermintaanPengirimanResponseDTO {
    private Long id;
    private String nomorPengiriman;
    private String namaPenerima;
    private String alamatPenerima;
    private String waktuPermintaanFormatted;
    private String tanggalPengirimanFormatted;
}
