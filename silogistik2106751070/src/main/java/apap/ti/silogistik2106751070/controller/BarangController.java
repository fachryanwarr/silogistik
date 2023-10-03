package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.dto.BarangMapper;
import apap.ti.silogistik2106751070.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751070.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BarangController {
    @Autowired
    BarangService barangService;

    @Autowired
    BarangMapper barangMapper;

    @GetMapping("/barang")
    public String viewAllBarang(Model model) {
        String successMessage = (String) model.getAttribute("successMessage");

        model.addAttribute("listBarangStok", barangService.getAllBarangWithTotalStok());
        model.addAttribute("index", "2");

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        return "barang/viewall-barang";
    }

    @GetMapping("/barang/{sku}")
    public String viewDetailBarang(@PathVariable(value = "sku") String sku, Model model) {
        Barang barang = barangService.getBarangBySku(sku);

        String successMessage = (String) model.getAttribute("successMessage");

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        if (barang != null) {
            model.addAttribute("barang", barangMapper.barangToReadBarangResponseDTO(barang));
        } else {
            model.addAttribute("error", "Barang not found");
        }
        model.addAttribute("index", "2");
        return "barang/view-barang";
    }

    @GetMapping("/barang/tambah")
    public String formTambahBarang(Model model) {
        var barangDTO = new CreateBarangRequestDTO();

        model.addAttribute("barangDTO", barangDTO);
        model.addAttribute("index", "2");

        return "barang/form-tambah-barang";
    }

    @PostMapping("/barang/tambah")
    public RedirectView tambahBarang(@ModelAttribute CreateBarangRequestDTO barangDTO, RedirectAttributes redirectAttributes, Model model) {
        Barang barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);

        String namaTipeBarang = barangService.getNamaTipeBarang(barang.getTipeBarang());
        long noSKU = barangService.getNextNumForSKU(barang.getTipeBarang());

        barang.setSku(namaTipeBarang + String.format("%03d", noSKU));
        barangService.saveBarang(barang);

        redirectAttributes.addFlashAttribute("successMessage", "Berhasil menambah barang");

        return new RedirectView("/barang");
    }

    @GetMapping("/barang/{sku}/ubah")
    public String formUbahBarang(@PathVariable(value = "sku") String sku, Model model) {
        Barang barang = barangService.getBarangBySku(sku);

        var barangDTO = barangMapper.barangToUpdateBarangRequestDTO(barang);

        model.addAttribute("barangDTO", barangDTO);

        return "barang/form-ubah-barang";
    }

    @PostMapping("/barang/{sku}/ubah")
    public RedirectView ubahBarang(@ModelAttribute UpdateBarangRequestDTO barangRequestDTO, RedirectAttributes redirectAttributes) {
        Barang barang = barangMapper.updateBarangRequestDTOToBarang(barangRequestDTO);

        barangService.updateBarang(barang);

        redirectAttributes.addFlashAttribute("successMessage", "Berhasil meng-update data barang");

        return new RedirectView("/barang/" + barangRequestDTO.getSku());
    }
}
