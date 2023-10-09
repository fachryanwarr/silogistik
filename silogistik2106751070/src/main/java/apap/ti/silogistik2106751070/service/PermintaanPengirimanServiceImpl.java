package apap.ti.silogistik2106751070.service;

import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanBarangDb;
import apap.ti.silogistik2106751070.repository.PermintaanPengirimanDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @Transactional
    public void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengirimanDb.save(permintaanPengiriman);

        for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanPengiriman.getListPermintaanPengirimanBarang()) {
            permintaanPengirimanBarang.setPermintaanPengiriman(permintaanPengiriman);
        }

        permintaanPengirimanBarangDb.saveAll(permintaanPengiriman.getListPermintaanPengirimanBarang());
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
        return permintaanPengirimanDb.findAllByOrderByWaktuPermintaanDesc();
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

        return "REQ" + String.format("%02d", (totalBarangDipesan % 100)) + jenisLayanan + timeFormat.format(createPermintaanPengirimanRequestDTO.getWaktuPermintaan());
    }

    @Override
    public void cancelPermintaan(PermintaanPengiriman permintaanPengiriman) {
        Calendar now = Calendar.getInstance();
        Calendar waktuPermintaan = Calendar.getInstance();
        waktuPermintaan.setTime(permintaanPengiriman.getWaktuPermintaan());
        waktuPermintaan.add(Calendar.HOUR_OF_DAY , 24);

        if (!now.after(waktuPermintaan)) {
            permintaanPengiriman.setIsCanceled(true);
            permintaanPengirimanDb.save(permintaanPengiriman);
        } else {
            throw new DataIntegrityViolationException("Permintaan pengiriman dengan nomor " + permintaanPengiriman.getNomorPengiriman() + " sudah tidak dapat dicancel!");
        }
    }

    @Override
    public List<ReadPermintaanPengirimanResponseDTO> getPermintaanPengirimanFiltered(Date startdate, Date endDate, Barang barang) {
        List<Long> listIdPermintaanFromBarang = new ArrayList<>();
        for (PermintaanPengirimanBarang permintaanPengirimanBarang : barang.getListPermintaanPengirimanBarang()) {
            listIdPermintaanFromBarang.add(permintaanPengirimanBarang.getPermintaanPengiriman().getId());
        }

        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanDb.findByWaktuPermintaanBetweenAndIdInOrderByWaktuPermintaanDesc(startdate, endDate, listIdPermintaanFromBarang);

        List<ReadPermintaanPengirimanResponseDTO> listPermintaanFormatted = new ArrayList<>();
        for (PermintaanPengiriman permintaanPengiriman : listPermintaanPengiriman) {
            listPermintaanFormatted.add(permintaanPengirimanMapper.permintaanPengirimanToReadPermintaanPengirimanResponseDTO(permintaanPengiriman));
        }

        return listPermintaanFormatted;
    }
}
