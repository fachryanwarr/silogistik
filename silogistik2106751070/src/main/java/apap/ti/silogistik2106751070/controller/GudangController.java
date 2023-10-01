package apap.ti.silogistik2106751070.controller;

import apap.ti.silogistik2106751070.service.GudangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GudangController {

    @Autowired
    GudangService gudangService;

    @GetMapping("/gudang")
    public String viewAllGudang(Model model) {
        model.addAttribute("listGudang", gudangService.getAllGudang());

        return "gudang/viewall-gudang";
    }
}
