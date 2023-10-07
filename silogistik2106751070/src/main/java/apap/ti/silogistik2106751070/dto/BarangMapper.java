package apap.ti.silogistik2106751070.dto;

import apap.ti.silogistik2106751070.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751070.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadBarangResponseDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.model.GudangBarang;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BarangMapper {
    Barang createBarangRequestDTOToBarang(CreateBarangRequestDTO createBarangRequestDTO);

    ReadBarangResponseDTO barangToReadBarangResponseDTO(Barang barang);

    UpdateBarangRequestDTO barangToUpdateBarangRequestDTO(Barang barang);

    Barang updateBarangRequestDTOToBarang(UpdateBarangRequestDTO updateBarangRequestDTO);

    @AfterMapping
    default void setExtraAttribute(@MappingTarget ReadBarangResponseDTO barangResponseDTO, Barang barang) {
        //set total stok
        int totalStok = 0;

        //set list gudang and stok
        for (GudangBarang gudangBarang : barang.getListGudangBarang()) {
            barangResponseDTO.getGudangStok().put(gudangBarang.getGudang(), gudangBarang.getStok());
            totalStok += gudangBarang.getStok();
        }

        barangResponseDTO.setTotalStok(totalStok);

        //set tipe barang
        String tipeBarang = switch (barang.getTipeBarang()) {
            case 1 -> "Produk Elektronik";
            case 2 -> "Pakaian & Aksesoris";
            case 3 -> "Makanan & Minuman";
            case 4 -> "Kosmetik";
            default -> "Perlengkapan Rumah";
        };

        //set tipe barang
        barangResponseDTO.setNamaTipeBarang(tipeBarang);
    }

    @AfterMapping
    default void setNamaTipeBarang(@MappingTarget UpdateBarangRequestDTO barangRequestDTO, Barang barang) {
        String tipeBarang = switch (barang.getTipeBarang()) {
            case 1 -> "Produk Elektronik";
            case 2 -> "Pakaian & Aksesoris";
            case 3 -> "Makanan & Minuman";
            case 4 -> "Kosmetik";
            default -> "Perlengkapan Rumah";
        };

        //set tipe barang
        barangRequestDTO.setNamaTipeBarang(tipeBarang);
    }
}
