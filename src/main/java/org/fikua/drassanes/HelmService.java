package org.fikua.drassanes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class HelmService {

    public String createHelmChart(String chartName) {
        log.info("Creating Helm Chart with name {}", chartName);
        ProcessBuilder processBuilder = new ProcessBuilder("helm", "create", chartName);
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
            return "Helm Chart creado: " + chartName + "\n" + output;
        } catch (IOException | InterruptedException e) {
            log.error("Error al ejecutar Helm: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return "Error al ejecutar Helm: " + e.getMessage();
        }
    }

}
