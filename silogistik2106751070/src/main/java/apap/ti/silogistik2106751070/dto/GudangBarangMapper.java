package apap.ti.silogistik2106751070.dto;

import apap.ti.silogistik2106751070.dto.response.ReadGudangBarangResponseDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.model.Gudang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GudangBarangMapper {
    ReadGudangBarangResponseDTO gudangAndBarangToReadGudangBarangResponseDTO(Gudang gudang, Barang barang);
}
