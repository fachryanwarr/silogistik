package apap.ti.silogistik2106751070.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBarangRequestDTO {
    private String sku;

    @NotNull
    private Integer tipeBarang;

    @NotNull
    private String merk;

    @NotNull
    private Long harga;
}
