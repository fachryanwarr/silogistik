package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.dto.GudangMapper;
import apap.ti.silogistik2106751070.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.model.Gudang;
import apap.ti.silogistik2106751070.model.GudangBarang;
import apap.ti.silogistik2106751070.service.BarangService;
import apap.ti.silogistik2106751070.service.GudangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GudangController {

    @Autowired
    GudangService gudangService;

    @Autowired
    GudangMapper gudangMapper;

    @Autowired
    BarangService barangService;

    @GetMapping("/gudang")
    public String viewAllGudang(Model model) {
        model.addAttribute("listGudang", gudangService.getAllGudang());

        return "gudang/viewall-gudang";
    }

    @GetMapping("/gudang/{id}")
    public String viewDetailGudang(@PathVariable(value = "id") Long id, Model model) {
        Gudang gudang = gudangService.getGudangById(id);
        var gudangResponse = gudangMapper.gudangToReadGudangResponseDTO(gudang);

        model.addAttribute("gudang", gudangResponse);

        return "gudang/view-gudang";
    }

    @GetMapping("gudang/cari-barang")
    public String cariBarang(@RequestParam(name = "sku", required = false) String skuBarang, Model model) {
        String successMessage = (String) model.getAttribute("successMessage");

        if (skuBarang != null && !skuBarang.isBlank()) {
            var barang = barangService.getBarangBySku(skuBarang);

            model.addAttribute("listGudangBarang", barang.getListGudangBarang());
            model.addAttribute("selectedValue", skuBarang);
        }

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        model.addAttribute("listBarang", barangService.getAllBarangSortedByMerk());

        return "gudang/cari-barang";
    }

    @GetMapping("/gudang/{idGudang}/restock-barang")
    public String formRestockBarang(@PathVariable(value = "idGudang") Long id, Model model) {
        Gudang gudang = gudangService.getGudangById(id);
        UpdateGudangRequestDTO gudangDTO = gudangMapper.gudangToUpdateGudangRequestDTO(gudang);
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        model.addAttribute("gudangDTO", gudangDTO);
        model.addAttribute("listBarang", listBarang);

        return "gudang/form-restock-barang";
    }

    @PostMapping(value="/gudang/{idGudang}/restock-barang", params={"addRow"})
    public String addRowRestockBarang(@ModelAttribute UpdateGudangRequestDTO gudangDTO, Model model) {
        if (gudangDTO.getListGudangBarang() == null || gudangDTO.getListGudangBarang().size() == 0) {
            gudangDTO.setListGudangBarang(new ArrayList<>());
        }

        gudangDTO.getListGudangBarang().add(new GudangBarang());

        model.addAttribute("gudangDTO", gudangDTO);
        model.addAttribute("listBarang", barangService.getAllBarangSortedByMerk());

        return "gudang/form-restock-barang";
    }

    @PostMapping("/gudang/{idGudang}/restock-barang")
    public RedirectView updateRestockBarang(@ModelAttribute UpdateGudangRequestDTO gudangDTO, RedirectAttributes redirectAttributes, Model model) {
        Gudang gudang = gudangMapper.updateGudangRequestDTOToGudang(gudangDTO);

        gudangService.restockBarang(gudang);

        redirectAttributes.addFlashAttribute("successMessage", "Berhasil restock barang");

        return new RedirectView("/gudang/" + gudangDTO.getId());
    }
}