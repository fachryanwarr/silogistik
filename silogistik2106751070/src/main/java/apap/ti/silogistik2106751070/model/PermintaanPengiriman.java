package apap.ti.silogistik2106751070.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permintaan_pengiriman")
public class PermintaanPengiriman {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @NotNull
    @Size(max = 12)
    @Column(name = "nomor_pengiriman", nullable = false)
    private String nomorPengiriman;

    @NotNull
    @Column(name = "is_canceled", nullable = false)
    private Boolean isCanceled;

    @NotNull
    @Column(name = "nama_penerima", nullable = false)
    private String namaPenerima;

    @NotNull
    @Column(name = "alamat_penerima", nullable = false)
    private String alamatPenerima;

    @NotNull
    @Column(name = "tanggal_pengiriman", nullable = false)
    private Date tanggalPengiriman;

    @NotNull
    @Column(name = "biaya_pengiriman", nullable = false)
    private Integer biayaPengiriman;

    @NotNull
    @Column(name = "jenis_layanan", nullable = false)
    private Integer jenisLayanan;

    @NotNull
    @Column(name = "waktu_pengiriman", nullable = false)
    private LocalDateTime waktuPermintaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_karyawan", referencedColumnName = "id")
    private Karyawan karyawan;

    @OneToMany(mappedBy = "permintaanPengiriman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = new ArrayList<>();
}
