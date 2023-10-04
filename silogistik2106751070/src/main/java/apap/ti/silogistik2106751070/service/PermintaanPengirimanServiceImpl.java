package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService{

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Autowired
    PermintaanPengirimanMapper permintaanPengirimanMapper;

    @Override
    public void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengirimanDb.save(permintaanPengiriman);
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
        return permintaanPengirimanDb.findAllByOrderByWaktuPermintaan();
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
}
