package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751070.dto.response.ReadDetailPermintaanResponseDTO;
import apap.ti.silogistik2106751070.model.Barang;
import apap.ti.silogistik2106751070.model.Karyawan;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import apap.ti.silogistik2106751070.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751070.service.BarangService;
import apap.ti.silogistik2106751070.service.KaryawanService;
import apap.ti.silogistik2106751070.service.PermintaanPengirimanService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class PermintaanPengirimanController {

    @Autowired
    PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    PermintaanPengirimanMapper permintaanPengirimanMapper;

    @Autowired
    KaryawanService karyawanService;

    @Autowired
    BarangService barangService;

    @GetMapping("/permintaan-pengiriman")
    public String viewAllPermintaan(Model model) {
        String successMessage = (String) model.getAttribute("successMessage");

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        model.addAttribute("listPermintaan", permintaanPengirimanService.getAllPermintaanPengirimanFormatted());
        model.addAttribute("index", "3");

        return "permintaanPengiriman/viewall-permintaanPengiriman";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}")
    public String detailPermintaanPengiriman(@PathVariable(value = "idPermintaanPengiriman") Long idPermintaanPengiriman, Model model) {
        PermintaanPengiriman permintaanPengiriman = permintaanPengirimanService.getPermintaanPengirimanById(idPermintaanPengiriman);

        if (permintaanPengiriman != null) {
            ReadDetailPermintaanResponseDTO detailPermintaanDTO = permintaanPengirimanMapper.permintaanPengirimanToReadDetailPermintaanResponseDTO(permintaanPengiriman);

            model.addAttribute("index", 3);
            model.addAttribute("permintaanPengiriman", detailPermintaanDTO);
        } else {
            model.addAttribute("error", "Permintaan pengiriman not found :(");
        }
        return "permintaanPengiriman/view-permintaanPengiriman";
    }

    @GetMapping("/permintaan-pengiriman/tambah")
    public String formTambahPermintaan(Model model) {
        List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        var permintaanPengirimanDTO = new CreatePermintaanPengirimanRequestDTO();

        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("permintaanPengirimanDTO", permintaanPengirimanDTO);

        return "permintaanPengiriman/form-permintaanPengiriman";
    }

    @PostMapping(value="/permintaan-pengiriman/tambah", params = {"addRow"})
    public String addRowPermintaanPengiriman(@ModelAttribute CreatePermintaanPengirimanRequestDTO permintaanPengirimanDTO, Model model) {
        if (permintaanPengirimanDTO.getListPermintaanPengirimanBarang() == null || permintaanPengirimanDTO.getListPermintaanPengirimanBarang().size() == 0) {
            permintaanPengirimanDTO.setListPermintaanPengirimanBarang(new ArrayList<>());
        }

        permintaanPengirimanDTO.getListPermintaanPengirimanBarang().add(new PermintaanPengirimanBarang());

        List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        model.addAttribute("permintaanPengirimanDTO", permintaanPengirimanDTO);
        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listBarang", listBarang);

        return "permintaanPengiriman/form-permintaanPengiriman";
    }

    @PostMapping("/permintaan-pengiriman/tambah")
    public RedirectView tambahPermintaanPengiriman(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO permintaanPengirimanDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            errors.append("Invalid input").append(" |");

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getDefaultMessage()).append(" | ");
            }

            redirectAttributes.addFlashAttribute("error", errors);

            return new RedirectView("/permintaan-pengiriman/tambah");
        }

        permintaanPengirimanDTO.setWaktuPermintaan(new Date());
        permintaanPengirimanDTO.setNomorPengiriman(permintaanPengirimanService.generateNomorPengiriman(permintaanPengirimanDTO));

        var permintaanPengiriman = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanPengirimanDTO);

        try {
            permintaanPengirimanService.savePermintaanPengiriman(permintaanPengiriman);
            redirectAttributes.addFlashAttribute("successMessage", "Berhasil membuat permintaan pengiriman");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Barang tidak boleh duplikat");
            return new RedirectView("/permintaan-pengiriman/tambah");
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Kuantitas pengiriman harus positif");
            return new RedirectView("/permintaan-pengiriman/tambah");
        }
        return new RedirectView("/permintaan-pengiriman");
    }

    @GetMapping("/permintaan-pengiriman/{id}/cancel")
    public RedirectView cancelPermintaan(@PathVariable(value = "id") Long idPermintaan, RedirectAttributes redirectAttributes) {
        var permintaanPengiriman = permintaanPengirimanService.getPermintaanPengirimanById(idPermintaan);
        try {
            permintaanPengirimanService.cancelPermintaan(permintaanPengiriman);
            redirectAttributes.addFlashAttribute("successMessage", "Berhasil membatalkan permintaan pengiriman dengan nomor pengiriman " + permintaanPengiriman.getNomorPengiriman());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return new RedirectView("/permintaan-pengiriman/" + permintaanPengiriman.getId());
    }

    @GetMapping("/filter-permintaan-pengiriman")
    public String filterPermintaan(@RequestParam(name = "start-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                   @RequestParam(name = "end-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                   @RequestParam(name = "sku", required = false) String sku,
                                   Model model) {

        if (startDate != null && endDate != null && sku != null) {
            Barang barang = barangService.getBarangBySku(sku);

            model.addAttribute("listPermintaan", permintaanPengirimanService.getPermintaanPengirimanFiltered(startDate, endDate, barang));
            model.addAttribute("selectedStartDate", startDate);
            model.addAttribute("selectedEndDate", endDate);
            model.addAttribute("selectedSKU", sku);
        }

        model.addAttribute("listBarang", barangService.getAllBarangSortedByMerk());
        model.addAttribute("index", "4");
        return "permintaanPengiriman/filter";
    }
}
