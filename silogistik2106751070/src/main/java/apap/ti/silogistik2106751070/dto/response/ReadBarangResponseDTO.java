package apap.ti.silogistik2106751070.dto.response;

import apap.ti.silogistik2106751070.model.Gudang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadBarangResponseDTO {
    private String sku;
    private Integer tipeBarang;
    private String namaTipeBarang;
    private String merk;
    private Long harga;
    private int totalStok;
    private Map<Gudang, Integer> gudangStok = new HashMap<>();
}
