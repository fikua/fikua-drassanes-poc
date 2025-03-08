package org.fikua.drassanes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class HelmService {

    private static final String HELM_REPO_DIR = "helm-repository";

    public String createHelmChart(String chartName) {
        File helmRepo = new File(HELM_REPO_DIR);
        if (!helmRepo.exists()) {
            helmRepo.mkdirs();
        }

        ProcessBuilder processBuilder = new ProcessBuilder("helm", "create", chartName);
        processBuilder.directory(helmRepo);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return "Helm Chart creado en " + HELM_REPO_DIR + "/" + chartName + "\n" + output;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error al ejecutar Helm: " + e.getMessage();
        }
    }

}
