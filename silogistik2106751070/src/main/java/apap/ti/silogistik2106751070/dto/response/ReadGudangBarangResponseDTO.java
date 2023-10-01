package apap.ti.silogistik2106751070.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadGudangBarangResponseDTO {
    private BigInteger id;
    private String nama;
    private String alamatGudang;
    private String sku;
    private Integer tipeBarang;
    private String merk;
    private BigInteger harga;
}
