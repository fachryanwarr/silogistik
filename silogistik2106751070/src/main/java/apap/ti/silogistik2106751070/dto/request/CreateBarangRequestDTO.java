package apap.ti.silogistik2106751070.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBarangRequestDTO {
    private String sku;

    @NotNull
    @Max(value = 5)
    @Min(value = 1)
    private Integer tipeBarang;

    @NotNull
    @NotBlank
    private String merk;

    @NotNull
    @Positive(message = "Harga harus positif")
    private Long harga;
}
