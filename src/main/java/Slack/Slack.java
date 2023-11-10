package Slack;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class Slack {

    private static HttpClient client = HttpClient.newHttpClient();
    private static final String urlMonitoramento = "https://hooks.slack.com/services/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";
    private static final String urlSuporte = "https://hooks.slack.com/services/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";

    public static void enviarAlertaToten(JSONObject content) throws IOException, InterruptedException {
        HttpRequest request01 = HttpRequest.newBuilder(
                URI.create(urlMonitoramento))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request01, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status %s", response.statusCode()));
        System.out.println(String.format("Response %s", response.body()));
    }
    public static void enviarAlertaSuporte(JSONObject content) throws IOException, InterruptedException {
        HttpRequest request02 = HttpRequest.newBuilder(
                        URI.create(urlSuporte))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request02, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status %s", response.statusCode()));
        System.out.println(String.format("Response %s", response.body()));
    }
}
