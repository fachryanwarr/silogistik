package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanBarangDb;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService{

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Autowired
    PermintaanPengirimanMapper permintaanPengirimanMapper;

    @Autowired
    PermintaanPengirimanBarangDb permintaanPengirimanBarangDb;

    @Override
    public void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengirimanDb.save(permintaanPengiriman);

        for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanPengiriman.getListPermintaanPengirimanBarang()) {
            permintaanPengirimanBarang.setPermintaanPengiriman(permintaanPengiriman);
            permintaanPengirimanBarangDb.save(permintaanPengirimanBarang);
        }
    }

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengiriman() {
        return permintaanPengirimanDb.findAll();
    }

    @Override
    public List<ReadPermintaanPengirimanResponseDTO> getAllPermintaanPengirimanFormatted() {
        List<ReadPermintaanPengirimanResponseDTO> listPermintaan = new ArrayList<>();
        for (PermintaanPengiriman permintaanPengiriman : getAllPermintaanPengirimanOrderByWaktu()) {
            listPermintaan.add(permintaanPengirimanMapper.permintaanPengirimanToReadPermintaanPengirimanResponseDTO(permintaanPengiriman));
        }

        return listPermintaan;
    }

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengirimanOrderByWaktu() {
        return permintaanPengirimanDb.findAllByIsCanceledFalseOrderByWaktuPermintaan();
    }

    @Override
    public PermintaanPengiriman getPermintaanPengirimanById(Long id) {
        for (PermintaanPengiriman permintaanPengiriman: getAllPermintaanPengiriman()) {
            if (permintaanPengiriman.getId().equals(id)) {
                return permintaanPengiriman;
            }
        }

        return null;
    }

    @Override
    public long getCount() {
        return permintaanPengirimanDb.count();
    }

    @Override
    public String generateNomorPengiriman(CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO) {
        int totalBarangDipesan = 0;

        for (PermintaanPengirimanBarang permintaanPengirimanBarang: createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang()) {
            totalBarangDipesan += permintaanPengirimanBarang.getKuantitasPengiriman();
        }

        String jenisLayanan = switch (createPermintaanPengirimanRequestDTO.getJenisLayanan()) {
            case 1 -> "SAM";
            case 2 -> "KIL";
            case 3 -> "REG";
            default -> "HEM";
        };

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        return "REQ" + (totalBarangDipesan % 100) + jenisLayanan + timeFormat.format(createPermintaanPengirimanRequestDTO.getWaktuPermintaan());
    }
}
