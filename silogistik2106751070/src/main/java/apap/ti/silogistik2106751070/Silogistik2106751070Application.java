package apap.ti.silogistik2106751070;

import apap.ti.silogistik2106751070.dto.BarangMapper;
import apap.ti.silogistik2106751070.dto.GudangMapper;
import apap.ti.silogistik2106751070.dto.KaryawanMapper;
import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751070.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751070.dto.request.CreateKaryawanRequestDTO;
import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.model.GudangBarang;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751070.service.*;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Silogistik2106751070Application {

	public static void main(String[] args) {
		SpringApplication.run(Silogistik2106751070Application.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(KaryawanService karyawanService,
						  KaryawanMapper karyawanMapper,
						  PermintaanPengirimanService permintaanPengirimanService,
						  PermintaanPengirimanMapper permintaanPengirimanMapper,
						  GudangService gudangService,
						  GudangMapper gudangMapper,
						  BarangService barangService,
						  BarangMapper barangMapper,
						  GudangBarangService gudangBarangService,
						  PermintaanPengirimanBarangService permintaanPengirimanBarangService
	) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));
			var fakeDate = faker.date();

			var karyawanDTO = new CreateKaryawanRequestDTO();
			var permintaanPengirimanDTO = new CreatePermintaanPengirimanRequestDTO();
			var gudangDTO = new CreateGudangRequestDTO();
			var barangDTO = new CreateBarangRequestDTO();

			for (int i = 0; i < 10; i++) {
				karyawanDTO.setNama(faker.name().name());
				karyawanDTO.setJenisKelamin((int) Math.floor(Math.random() * 2));
				karyawanDTO.setTanggalLahir(fakeDate.birthday());

				var karyawan = karyawanMapper.createKaryawanRequestDTOToKaryawan(karyawanDTO);
				karyawanService.saveKaryawan(karyawan);
			}

			for (int i = 0; i < 30; i++) {
				int jumlahBarangDipesan = (int) Math.floor(10 + Math.random() * 90);
				int noJenisLayanan = 1 + (int) Math.floor(Math.random() * 4);
				String jenisLayanan = switch (noJenisLayanan) {
					case 1 -> "SAM";
					case 2 -> "KIL";
					case 3 -> "REG";
					default -> "HEM";
				};
				LocalTime waktuPengiriman = LocalTime.of((int) Math.floor(Math.random() * 21), (int) Math.floor(Math.random() * 59), (int) Math.floor(Math.random() * 59));

				permintaanPengirimanDTO.setNomorPengiriman("REQ" + jumlahBarangDipesan + jenisLayanan + waktuPengiriman.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
				permintaanPengirimanDTO.setNamaPenerima(faker.name().firstName());
				permintaanPengirimanDTO.setAlamatPenerima(faker.address().streetAddress());
				//random past date ten months from now
				Date utilDate = fakeDate.past(10 * 30, TimeUnit.DAYS);
				permintaanPengirimanDTO.setTanggalPengiriman(new java.sql.Date(utilDate.getTime()).toLocalDate());

				permintaanPengirimanDTO.setBiayaPengiriman(10000 + (int) Math.floor(Math.random() * 90000));
				permintaanPengirimanDTO.setJenisLayanan(noJenisLayanan);
				permintaanPengirimanDTO.setWaktuPermintaan(waktuPengiriman);
				permintaanPengirimanDTO.setKaryawan(karyawanService.getKaryawanById(1 + (long) Math.floor(Math.random() * 10)));

				var permintaanPengiriman = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanPengirimanDTO);
				permintaanPengirimanService.savePermintaanPengiriman(permintaanPengiriman);
			}

			for (int i = 0; i < 5; i++) {
				gudangDTO.setNama("Gudang " + faker.address().city());
				gudangDTO.setAlamatGudang(faker.address().streetAddress());

				var gudang = gudangMapper.createGudangRequestDTOToGudang(gudangDTO);
				gudangService.saveGudang(gudang);
			}

			for (int i = 0; i < 50; i++) {
				int noTipeBarang = (int) Math.floor(1 + Math.random() * 5);
				String tipeBarang = switch (noTipeBarang) {
					case 1 -> "ELEC";
					case 2 -> "CLOT";
					case 3 -> "FOOD";
					case 4 -> "COSM";
					default -> "TOOL";
				};

				barangDTO.setTipeBarang(noTipeBarang);
				barangDTO.setSku(tipeBarang + String.format("%03d", barangService.getNextNumForSKU(noTipeBarang)));
				barangDTO.setMerk(faker.commerce().productName());
				barangDTO.setHarga(10000 + (long) Math.floor(Math.random() * 1000000));

				var barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);
				barangService.saveBarang(barang);
			}

			List<String> listSKU = barangService.getAllSku();

			for (int i = 0; i < 30; i++) {
				var gudangBarang = new GudangBarang();
				gudangBarang.setBarang(barangService.getBarangBySku(listSKU.get((int) Math.floor(Math.random() * 49))));
				gudangBarang.setGudang(gudangService.getGudangById((long) Math.floor(1 + Math.random() * 5)));
				gudangBarang.setStok((int) Math.floor(1 + Math.random() * 99));
				gudangBarangService.saveGudangBarang(gudangBarang);
			}

			for (int i = 0; i < 30; i++) {
				var permintaanPengirimanBarang = new PermintaanPengirimanBarang();
				permintaanPengirimanBarang.setBarang(barangService.getBarangBySku(listSKU.get((int) Math.floor(Math.random() * 49))));
				permintaanPengirimanBarang.setPermintaanPengiriman(permintaanPengirimanService.getPermintaanPengirimanById((long) Math.floor(1 + Math.random() * 30)));
				permintaanPengirimanBarang.setKuantitasPengiriman((int) Math.floor(1 + Math.random() * 10));
				permintaanPengirimanBarangService.savePermintaanPengirimanBarang(permintaanPengirimanBarang);
			}
		};
	}
}
