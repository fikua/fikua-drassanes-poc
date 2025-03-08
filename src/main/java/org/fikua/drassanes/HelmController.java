package org.fikua.drassanes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HelmController {

    private final HelmService helmService;

    @GetMapping("/")
    public String showForm() {
        return "createChart";
    }

    @PostMapping("/chart")
    public String createChart(@RequestParam("chartName") String chartName, Model model) {
        log.info("Creating chart with name: {}", chartName);
        String result = helmService.createHelmChart(chartName);
        model.addAttribute("message", result);
        log.info(result);
        return "createChart";
    }

}
