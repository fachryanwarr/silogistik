package apap.ti.silogistik2106751070.repository;

import apap.ti.silogistik2106751070.model.Karyawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface KaryawanDb extends JpaRepository<Karyawan, BigInteger> {
}
