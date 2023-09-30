package apap.ti.silogistik2106751070.dto;

import apap.ti.silogistik2106751070.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751070.model.Gudang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GudangMapper {
    Gudang createGudangRequestDTOToGudang(CreateGudangRequestDTO createGudangRequestDTO);
}
