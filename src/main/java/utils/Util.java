package utils;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import entities.Alert;
import entities.Statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Util {


    public static void setInterval(Runnable task, long interval, TimeUnit timeUnit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(task, 0, interval, timeUnit);
    }

    public static void sendAlert(Alert alert){
        String slackToken = "SLACK_TOKEN_USER";
        Slack slack = Slack.getInstance();

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#test-integracao")
                .text("Olá, Slack! Esta é uma mensagem de integração.")
                .build();

        try {
            ChatPostMessageResponse response = slack.methods(slackToken).chatPostMessage(request);

            if (response.isOk()) {
                System.out.println("Mensagem postada com sucesso no Slack!");
            } else {
                System.err.println("Erro ao postar a mensagem no Slack: " + response.getError());
            }
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }

    public static void createTextFileInRootDirectory(Statistics statistics, String name) {
        String fileName = name + ".txt";
        String directoryPath = System.getProperty("java.io.tmpdir");

        try {
            File file = new File(directoryPath, fileName);

            if (file.createNewFile()) {
                System.out.println("Arquivo criado com sucesso: " + file.getAbsolutePath());
                writeToFile(file, templateLog(statistics));
            } else {
                System.out.println("O arquivo já existe: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeToFile(File file, String content) {
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(content);
            System.out.println("Conteúdo adicionado ao arquivo");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static String templateLog(Statistics statistics) {
        return
                "-----------------------------------\n" +
                        "REGISTRO DE CAPTURA DE HARDWARE\n" +
                        "Data e Hora: " + Instant.now() + "\n" +
                        "Dispositivo: " + statistics.getComputer().getId() +
                        "-----------------------------------\n" +
                        "\n" +
                        "[Detalhes da Captura]\n" +
                        "\n" +
                        "1. Componentes de Hardware:\n" +
                        "   - CPU:\n" +
                        "        - Em uso: " + String.format("%.2f (percentual)", statistics.getCpuUsage()) + "\n" +
                        "   - RAM:\n" +
                        "        - Disponível: " + String.format("%.2fGB", statistics.getRamAvailable()) + "\n" +
                        "        - Em Uso: " + String.format("%.2fGB", statistics.getRamUsage()) + "\n" +
                        "        - Total: " + String.format("%.2fGB", statistics.getRamTotal()) + "\n" +
                        "   - Armazenamento:\n" +
                        "        - Em Uso: " + String.format("%.2fGB", statistics.getDiskUsage()) + "\n" +
                        "        - Total: " + String.format("%.2fGB", statistics.getDiskTotal()) + "\n" +
                        "2. Problemas ou Observações:\n" +
                        "   - Entre em contato com a equipe Paragon Tech\n" +
                        "\n" +
                        "-----------------------------------\n" +
                        "FIM DO REGISTRO DE CAPTURA DE HARDWARE\n" +
                        "-----------------------------------";
    }

}
