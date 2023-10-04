package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751070.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106751070.model.PermintaanPengiriman;
import apap.ti.silogistik2106751070.service.PermintaanPengirimanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PermintaanPengirimanController {

    @Autowired
    PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    PermintaanPengirimanMapper permintaanPengirimanMapper;

    @GetMapping("/permintaan-pengiriman")
    public String viewAllPermintaan(Model model) {

        model.addAttribute("listPermintaan", permintaanPengirimanService.getAllPermintaanPengirimanFormatted());
        model.addAttribute("index", "3");

        return "permintaanPengiriman/viewall-permintaanPengiriman";
    }
}
