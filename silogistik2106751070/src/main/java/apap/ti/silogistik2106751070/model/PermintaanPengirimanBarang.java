package apap.ti.silogistik2106751070.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permintaan_pengiriman_barang", uniqueConstraints = {@UniqueConstraint(columnNames = {"sku_barang", "id_permintaan_pengiriman"})})
public class PermintaanPengirimanBarang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_permintaan_pengiriman", referencedColumnName = "id")
    private PermintaanPengiriman permintaanPengiriman;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_barang", referencedColumnName = "sku")
    private Barang barang;

    @NotNull
    @Column(name = "kuantitas_pengiriman", nullable = false)
    @Positive(message = "Kuantitas pengiriman tidak bisa 0 atau negatif")
    private int kuantitasPengiriman;
}
