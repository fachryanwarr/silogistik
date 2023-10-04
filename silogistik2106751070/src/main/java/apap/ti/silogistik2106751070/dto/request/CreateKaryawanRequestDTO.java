package apap.ti.silogistik2106751070.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKaryawanRequestDTO {
    @NotNull
    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;

    @NotNull
    @NotBlank(message = "Jenis kelamin tidak boleh kosong")
    private Integer jenisKelamin;

    @NotNull
    private LocalDate tanggalLahir;
}
