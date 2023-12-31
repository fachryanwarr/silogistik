package apap.ti.silogistik2106751070.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "barang")
public class Barang {
    @Id
    private String sku;

    @NotNull
    @Column(name = "tipe_barang", nullable = false)
    @Max(value = 5)
    @Min(value = 1)
    private Integer tipeBarang;

    @NotNull
    @Column(name = "merk", nullable = false, unique = true)
    private String merk;

    @NotNull
    @Column(name = "harga", nullable = false)
    private Long harga;

    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangBarang> listGudangBarang = new ArrayList<>();

    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = new ArrayList<>();
}
