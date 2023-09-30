package apap.ti.silogistik2106751070;

import apap.ti.silogistik2106751070.dto.KaryawanMapper;
import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.request.CreateKaryawanRequestDTO;
import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.service.KaryawanService;
import apap.ti.silogistik2106751070.service.PermintaanPengirimanService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Date;
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
						  PermintaanPengirimanMapper permintaanPengirimanMapper
	) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));
			var fakeDate = faker.date();

			var karyawanDTO = new CreateKaryawanRequestDTO();
			var permintaanPengirimanDTO = new CreatePermintaanPengirimanRequestDTO();

			for (int i = 0; i < 10; i++) {
				karyawanDTO.setNama(faker.name().name());
				karyawanDTO.setJenisKelamin((int) Math.floor(Math.random() * 2));
				karyawanDTO.setTanggalLahir(fakeDate.birthday());

				var karyawan = karyawanMapper.createKaryawanRequestDTOToKaryawan(karyawanDTO);
				karyawanService.saveKaryawan(karyawan);
			}

			for (int i = 0; i < 30; i++) {
				permintaanPengirimanDTO.setNomorPengiriman("");
				permintaanPengirimanDTO.setNamaPenerima(faker.name().firstName());
				permintaanPengirimanDTO.setAlamatPenerima(faker.address().streetAddress());
				//random past date ten months from now
				Date utilDate = fakeDate.past(10 * 30, TimeUnit.DAYS);
				permintaanPengirimanDTO.setTanggalPengiriman(new java.sql.Date(utilDate.getTime()));

				permintaanPengirimanDTO.setBiayaPengiriman(10000 + (int) Math.floor(Math.random() * 90000));
				permintaanPengirimanDTO.setJenisLayanan(1 + (int) Math.floor(Math.random() * 4));
				permintaanPengirimanDTO.setWaktuPermintaan(LocalTime.of((int) Math.floor(Math.random() * 21), (int) Math.floor(Math.random() * 59)));
				permintaanPengirimanDTO.setKaryawan(karyawanService.getKaryawanById(BigInteger.valueOf(1 + (int) Math.floor(Math.random() * 10))));

				var permintaanPengiriman = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanPengirimanDTO);
				permintaanPengirimanService.savePermintaanPengiriman(permintaanPengiriman);
			}
		};
	}
}
