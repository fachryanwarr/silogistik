package apap.ti.silogistik2106751070.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "karyawan")
public class Karyawan {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "jenis_kelamin", nullable = false)
    private Integer jenisKelamin;

    @NotNull
    @Column(name = "tanggal_lahir", nullable = false)
    private Date tanggalLahir;

    @OneToMany(mappedBy = "karyawan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengiriman> listPermintaanPengiriman = new ArrayList<>();
}
