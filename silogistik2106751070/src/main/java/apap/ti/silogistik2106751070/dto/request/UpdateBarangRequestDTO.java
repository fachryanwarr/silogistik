package apap.ti.silogistik2106751070.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBarangRequestDTO extends CreateBarangRequestDTO{

    private String namaTipeBarang;
}
