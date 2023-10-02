package apap.ti.silogistik2106751070.dto.request;

import apap.ti.silogistik2106751070.model.GudangBarang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGudangRequestDTO extends CreateGudangRequestDTO {
    private BigInteger id;
    private List<GudangBarang> listGudangBarang;
}
