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
import org.springframework.dao.DataIntegrityViolationException;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
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

			//data dummy karyawan
			for (int i = 0; i < 10; i++) {
				karyawanDTO.setNama(faker.name().name());
				karyawanDTO.setJenisKelamin(faker.number().numberBetween(1, 3));
				karyawanDTO.setTanggalLahir(fakeDate.birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

				var karyawan = karyawanMapper.createKaryawanRequestDTOToKaryawan(karyawanDTO);
				try {
					karyawanService.saveKaryawan(karyawan);
				} catch (DataIntegrityViolationException ignored) {}
			}

			//data dummy permintaan pengiriman
			for (int i = 0; i < 50; i++) {
				int jumlahBarangDipesan = faker.number().numberBetween(10, 99);
				int noJenisLayanan = faker.number().numberBetween(1, 5);
				String jenisLayanan = switch (noJenisLayanan) {
					case 1 -> "SAM";
					case 2 -> "KIL";
					case 3 -> "REG";
					default -> "HEM";
				};
				//random past date until 10 days ago from now
				Date waktuPermintaan = fakeDate.past(10, TimeUnit.DAYS);
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

				permintaanPengirimanDTO.setNomorPengiriman("REQ" + String.format("%2d", jumlahBarangDipesan) + jenisLayanan + timeFormat.format(waktuPermintaan));
				permintaanPengirimanDTO.setWaktuPermintaan(waktuPermintaan);

				permintaanPengirimanDTO.setNamaPenerima(faker.name().firstName());
				permintaanPengirimanDTO.setAlamatPenerima(faker.address().streetAddress());
				//random past date ten days from now
				permintaanPengirimanDTO.setTanggalPengiriman(fakeDate.future(10, TimeUnit.DAYS));

				permintaanPengirimanDTO.setBiayaPengiriman(faker.number().numberBetween(10000, 99000));
				permintaanPengirimanDTO.setJenisLayanan(noJenisLayanan);
				permintaanPengirimanDTO.setKaryawan(karyawanService.getKaryawanById((long) faker.number().numberBetween(1, 11)));

				var permintaanPengiriman = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanPengirimanDTO);
				try {
					permintaanPengirimanService.savePermintaanPengiriman(permintaanPengiriman);
				} catch (DataIntegrityViolationException ignored){}
			}

			//data dummy gudang
			for (int i = 0; i < 5; i++) {
				gudangDTO.setNama("Gudang " + faker.address().city());
				gudangDTO.setAlamatGudang(faker.address().streetAddress());

				var gudang = gudangMapper.createGudangRequestDTOToGudang(gudangDTO);
				try {
					gudangService.saveGudang(gudang);
				} catch (DataIntegrityViolationException ignored) {}
			}

			//data dummy barang
			for (int i = 0; i < 30; i++) {
				int noTipeBarang = faker.number().numberBetween(1, 6);
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
				barangDTO.setHarga((long) faker.number().numberBetween(10000, 1000000));

				var barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);
				try {
					barangService.saveBarang(barang);
				} catch (DataIntegrityViolationException ignored) {}
			}

			List<String> listSKU = barangService.getAllSku();

			//data dummy gudang barang
			for (int i = 0; i < 50; i++) {
				var gudangBarang = new GudangBarang();
				gudangBarang.setBarang(barangService.getBarangBySku(listSKU.get(faker.number().numberBetween(0, 30))));
				gudangBarang.setGudang(gudangService.getGudangById((long) faker.number().numberBetween(1, 6)));
				gudangBarang.setStok(faker.number().numberBetween(1, 99));
				try {
					gudangBarangService.saveGudangBarang(gudangBarang);
				} catch (DataIntegrityViolationException ignored) {}
			}

			//data dummy permintaan pengiriman barang
			for (int i = 0; i < 100; i++) {
				var permintaanPengirimanBarang = new PermintaanPengirimanBarang();
				permintaanPengirimanBarang.setBarang(barangService.getBarangBySku(listSKU.get(faker.number().numberBetween(0, 30))));
				permintaanPengirimanBarang.setPermintaanPengiriman(permintaanPengirimanService.getPermintaanPengirimanById((long) faker.number().numberBetween(1, 51)));
				permintaanPengirimanBarang.setKuantitasPengiriman(faker.number().numberBetween(1, 21));
				try {
					permintaanPengirimanBarangService.savePermintaanPengirimanBarang(permintaanPengirimanBarang);
				} catch (DataIntegrityViolationException ignored) {}
			}
		};
	}
}
