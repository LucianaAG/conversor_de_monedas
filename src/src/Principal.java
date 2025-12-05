import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Principal {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese moneda base: ");
        String monedaBase = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese moneda objetivo: ");
        String monedaObjetivo = scanner.nextLine().toUpperCase();

        System.out.print("Cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        String apiKey = "d40d1225ecc062512f48d8e8";

        String urlMonedas = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestMonedas = HttpRequest.newBuilder()
                .uri(URI.create(urlMonedas))
                .build();

        HttpResponse<String> responseMonedas = client.send(requestMonedas, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonMonedas = JsonParser.parseString(responseMonedas.body()).getAsJsonObject();

        if (!jsonMonedas.get("result").getAsString().equals("success")) {
            System.out.println("Error obteniendo tasas: " + jsonMonedas.get("error-type").getAsString());
            return;
        }

        System.out.println("\n=== Filtrado de monedas ===");

        JsonObject rates = jsonMonedas.getAsJsonObject("conversion_rates");

        String[] filtradas = {"ARS", "USD", "BRL", "CLP", "COP", "BOB"};

        for (String codigo : filtradas) {
            if (rates.has(codigo)) {
                System.out.println(codigo + ": " + rates.get(codigo).getAsDouble());
            }
        }

        String url = "https://v6.exchangerate-api.com/v6/" + apiKey +
                "/pair/" + monedaBase + "/" + monedaObjetivo + "/" + cantidad;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!json.get("result").getAsString().equals("success")) {
            System.out.println("Error en la conversión: " + json.get("error-type").getAsString());
            return;
        }

        double tasa = json.get("conversion_rate").getAsDouble();
        double resultado = json.get("conversion_result").getAsDouble();

        Moneda conversion = new Moneda(
                monedaBase,
                monedaObjetivo,
                cantidad,
                tasa,
                resultado
        );

        System.out.println("\n=== Resultado de la conversión ===");
        System.out.println(conversion);
    }
}
