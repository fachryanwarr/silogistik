package apap.ti.silogistik2106751070.dto;

import apap.ti.silogistik2106751070.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751070.model.Barang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarangMapper {
    Barang createBarangRequestDTOToBarang(CreateBarangRequestDTO createBarangRequestDTO);
}
