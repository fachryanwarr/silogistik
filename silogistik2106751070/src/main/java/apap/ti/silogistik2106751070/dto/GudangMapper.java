package apap.ti.silogistik2106751070.dto;

import apap.ti.silogistik2106751070.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751070.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadGudangResponseDTO;
import apap.ti.silogistik2106751070.model.Gudang;
import apap.ti.silogistik2106751070.model.GudangBarang;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GudangMapper {
    Gudang createGudangRequestDTOToGudang(CreateGudangRequestDTO createGudangRequestDTO);

    UpdateGudangRequestDTO gudangToUpdateGudangRequestDTO(Gudang gudang);

    Gudang updateGudangRequestDTOToGudang(UpdateGudangRequestDTO updateGudangRequestDTO);

    ReadGudangResponseDTO gudangToReadGudangResponseDTO(Gudang gudang);

    @AfterMapping
    default void setBarangStok(@MappingTarget ReadGudangResponseDTO gudangResponseDTO, Gudang gudang)  {
        for (GudangBarang gudangBarang : gudang.getListGudangBarang()) {
            gudangResponseDTO.getBarangStok().put(gudangBarang.getBarang(), gudangBarang.getStok());
        }
    }
}
