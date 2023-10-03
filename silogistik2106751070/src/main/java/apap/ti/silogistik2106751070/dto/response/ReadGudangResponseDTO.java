package apap.ti.silogistik2106751070.dto.response;

import apap.ti.silogistik2106751070.model.Barang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadGudangResponseDTO {
    private Long id;
    private String nama;
    private String alamatGudang;
    private Map<Barang, Integer> barangStok = new HashMap<>();
}
