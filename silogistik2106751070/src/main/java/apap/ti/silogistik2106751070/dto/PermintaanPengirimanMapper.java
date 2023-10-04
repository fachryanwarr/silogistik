package apap.ti.silogistik2106751070.dto;

import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadDetailPermintaanResponseDTO;
import apap.ti.silogistik2106751070.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PermintaanPengirimanMapper {
    PermintaanPengiriman createPermintaanPengirimanRequestDTOToPermintaanPengiriman(CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO);

    ReadPermintaanPengirimanResponseDTO permintaanPengirimanToReadPermintaanPengirimanResponseDTO(PermintaanPengiriman permintaanPengiriman);

    ReadDetailPermintaanResponseDTO permintaanPengirimanToReadDetailPermintaanResponseDTO(PermintaanPengiriman permintaanPengiriman);

    @AfterMapping
    default void setWaktuPermintaanFormatted(@MappingTarget ReadPermintaanPengirimanResponseDTO permintaanResponseDTO, PermintaanPengiriman permintaanPengiriman) {
        var format = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        var formatTanggal = new SimpleDateFormat("dd-MM-yyyy");

        permintaanResponseDTO.setWaktuPermintaanFormatted(format.format(permintaanPengiriman.getWaktuPermintaan()));
        permintaanResponseDTO.setTanggalPengirimanFormatted(formatTanggal.format(permintaanPengiriman.getTanggalPengiriman()));
    }
}
