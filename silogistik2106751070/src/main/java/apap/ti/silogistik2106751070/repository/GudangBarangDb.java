package apap.ti.silogistik2106751070.repository;

import apap.ti.silogistik2106751070.model.GudangBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface GudangBarangDb extends JpaRepository<GudangBarang, BigInteger> {
}
