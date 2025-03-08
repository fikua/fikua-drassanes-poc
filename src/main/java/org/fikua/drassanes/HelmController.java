package org.fikua.drassanes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/helm")
@RequiredArgsConstructor
public class HelmController {

    private final HelmService helmService;

    @GetMapping("/")
    public String showForm() {
        return "createChart";
    }

    @PostMapping("/chart")
    public String createChart(@RequestParam("chartName") String chartName, Model model) {
        String result = helmService.createHelmChart(chartName);
        model.addAttribute("message", result);
        return "createChart";
    }

}
