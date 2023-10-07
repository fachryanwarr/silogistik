package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.dto.BarangMapper;
import apap.ti.silogistik2106751070.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751070.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.service.BarangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

        model.addAttribute("listBarangStok", barangService.getAllBarangWithTotalStok());
        model.addAttribute("index", "2");

        return "barang/viewall-barang";
    }

    @GetMapping("/barang/{sku}")
    public String viewDetailBarang(@PathVariable(value = "sku") String sku, Model model) {
        Barang barang = barangService.getBarangBySku(sku);

        if (barang != null) {
            model.addAttribute("barang", barangMapper.barangToReadBarangResponseDTO(barang));
        } else {
            model.addAttribute("error", "Barang not found :(");
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
    public RedirectView tambahBarang(@Valid @ModelAttribute CreateBarangRequestDTO barangDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            errors.append("Invalid input").append(" ");

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append("| ").append(error.getDefaultMessage()).append(" | ");
            }

            redirectAttributes.addFlashAttribute("error", errors);

            return new RedirectView("/barang/tambah");
        }

        Barang barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);

        String namaTipeBarang = barangService.getNamaTipeBarang(barang.getTipeBarang());
        long noSKU = barangService.getNextNumForSKU(barang.getTipeBarang());

        barang.setSku(namaTipeBarang + String.format("%03d", noSKU));

        try {
            barangService.saveBarang(barang);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Merk " + barang.getMerk() + " sudah ada!");
            return new RedirectView("/barang/tambah");
        }

        redirectAttributes.addFlashAttribute("successMessage", "Berhasil menambah barang: " + barang.getMerk());

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
    public RedirectView ubahBarang(@Valid @ModelAttribute UpdateBarangRequestDTO barangRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            errors.append("Invalid input").append(" ");

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append("| ").append(error.getDefaultMessage()).append(" | ");
            }

            redirectAttributes.addFlashAttribute("error", errors);

            return new RedirectView("/barang/" + barangRequestDTO.getSku() + "/ubah");
        }

        Barang barang = barangMapper.updateBarangRequestDTOToBarang(barangRequestDTO);

        try {
            barangService.updateBarang(barang);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Merk " + barang.getMerk() + " sudah ada!");
            return new RedirectView("/barang/" + barang.getSku() + "/ubah");
        }

        redirectAttributes.addFlashAttribute("successMessage", "Berhasil meng-update data barang dengan SKU " + barang.getSku());

        return new RedirectView("/barang/" + barangRequestDTO.getSku());
    }
}
