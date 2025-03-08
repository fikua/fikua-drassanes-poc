package org.fikua.drassanes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HelmController {

    private static final String HELM_REPO_DIR = "helm-repository";
    private static final String PATH_SEPARATOR = "/";

    private final HelmService helmService;


    @GetMapping("/")
    public String showForm(Model model) {
        return listCharts(model);
    }

    @GetMapping("/charts")
    public String listCharts(Model model) {
        File repoDir = new File(HELM_REPO_DIR);
        List<String> chartList = repoDir.exists() ?
                Arrays.stream(Objects.requireNonNull(repoDir.list((dir, name) -> new File(dir, name).isDirectory()))).toList()
                : List.of();
        model.addAttribute("chartsList", chartList);
        return "charts";
    }

    @PostMapping("/create-chart")
    public String createChart(@RequestParam("chartName") String chartName, Model model) {
        log.info("Creating chart with name: {}", chartName);
        String result = helmService.createHelmChart(chartName);
        model.addAttribute("message", result);
        log.info(result);
        return "redirect:/charts";
    }

    @GetMapping("/chart/files")
    public String listChartFiles(@RequestParam String chartName, @RequestParam(required = false) String path, Model model) {
        String chartPath = path != null ? HELM_REPO_DIR + PATH_SEPARATOR + chartName + PATH_SEPARATOR + path : HELM_REPO_DIR + PATH_SEPARATOR + chartName;
        File chartDir = new File(chartPath);

        List<String> directories = chartDir.exists() ?
                Arrays.stream(Objects.requireNonNull(chartDir.list((dir, name) -> new File(dir, name).isDirectory()))).toList()
                : List.of();

        List<String> files = chartDir.exists() ?
                Arrays.stream(Objects.requireNonNull(chartDir.list((dir, name) -> new File(dir, name).isFile()))).toList()
                : List.of();

        model.addAttribute("chartName", chartName);
        model.addAttribute("currentPath", path != null ? path : "");
        model.addAttribute("directories", directories);
        model.addAttribute("files", files);
        return "chartFiles";
    }

    @GetMapping("/chart/view")
    public String viewFileContent(@RequestParam("chartName") String chartName, @RequestParam("filePath") String filePath, Model model) {
        if (filePath == null || filePath.isEmpty()) {
            model.addAttribute("content", "Error: Nombre de archivo inválido.");
            return "chartFileView";
        }
        File file = new File(HELM_REPO_DIR + PATH_SEPARATOR + chartName + PATH_SEPARATOR + filePath);
        String content = "Archivo no encontrado.";
        if (file.exists()) {
            try {
                content = new String(Files.readAllBytes(Paths.get(file.getPath())));
            } catch (Exception e) {
                content = "Error al leer el archivo.";
            }
        }
        model.addAttribute("chartName", chartName);
        model.addAttribute("filePath", filePath);
        model.addAttribute("content", content);
        return "chartFileView";
    }

}
