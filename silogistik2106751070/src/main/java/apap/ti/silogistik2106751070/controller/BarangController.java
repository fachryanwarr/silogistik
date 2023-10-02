package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.dto.BarangMapper;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BarangController {
    @Autowired
    BarangService barangService;

    @Autowired
    BarangMapper barangMapper;

    @GetMapping("/barang")
    public String viewAllBarang(Model model) {
        model.addAttribute("listBarang", barangService.getAllBarangSortedByMerk());
        model.addAttribute("index", "2");

        return "barang/viewall-barang";
    }

    @GetMapping("/barang/{sku}")
    public String viewDetailBarang(@PathVariable(value = "sku") String sku, Model model) {
        Barang barang = barangService.getBarangBySku(sku);

        if (barang != null) {
            model.addAttribute("barang", barangMapper.barangToReadBarangResponseDTO(barang));
        } else {
            model.addAttribute("error", "Barang not found");
        }
        return "barang/view-barang";
    }
}
