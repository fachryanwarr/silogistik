package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.service.BarangService;
import apap.ti.silogistik2106751070.service.GudangService;
import apap.ti.silogistik2106751070.service.KaryawanService;
import apap.ti.silogistik2106751070.service.PermintaanPengirimanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @Autowired
    BarangService barangService;

    @Autowired
    GudangService gudangService;

    @Autowired
    KaryawanService karyawanService;

    @Autowired
    PermintaanPengirimanService permintaanPengirimanService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("index", "0");
        model.addAttribute("jumlahBarang", barangService.getCount());
        model.addAttribute("jumlahKaryawan", karyawanService.getCount());
        model.addAttribute("jumlahGudang", gudangService.getCount());
        model.addAttribute("jumlahPermintaan", permintaanPengirimanService.getCount());
        return "home";
    }
}
