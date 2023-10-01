package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.service.BarangService;
import apap.ti.silogistik2106751070.service.GudangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@Controller
public class GudangController {

    @Autowired
    GudangService gudangService;

    @Autowired
    BarangService barangService;

    @GetMapping("/gudang")
    public String viewAllGudang(Model model) {
        model.addAttribute("listGudang", gudangService.getAllGudang());

        return "gudang/viewall-gudang";
    }

    @GetMapping("/gudang/{id}")
    public String viewGudangDetail(@PathVariable(value = "id") BigInteger id, Model model) {
        model.addAttribute("gudang", gudangService.getGudangById(id));          

        return "gudang/view-gudang";
    }

    @GetMapping("gudang/cari-barang")
    public String cariBarang(@RequestParam(name = "sku", required = false) String skuBarang, Model model) {
        if (skuBarang != null && !skuBarang.isBlank()) {
            var barang = barangService.getBarangBySku(skuBarang);

            model.addAttribute("listGudangBarang", barang.getListGudangBarang());
            model.addAttribute("selectedValue", skuBarang);
        }

        model.addAttribute("listBarang", barangService.getAllBarang());

        return "gudang/cari-barang";
    }
}