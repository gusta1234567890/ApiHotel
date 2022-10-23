package com.apiHotel.userHotel.Resquesties;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

public class HotelRequest {
    public HotelRequest() throws URISyntaxException, IOException, InterruptedException {
        this.token = getToken();
    }

    private String token;

    public String Request(String Url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client;
        HttpRequest request;
        String retorno;

        try
        {
            //cria um http client
            client = HttpClient.newHttpClient();

            //faz o request do tipo get com os headers
            request = HttpRequest.newBuilder()
                    .uri(new URI(Url))
                    .GET()
                    .setHeader("Authorization", token)
                    .setHeader("Content-Type", "application/json")
                    .build();

            //recebe a reponse
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            retorno = httpResponse.body();
        }
        catch (Exception e)
        {
            throw e;
        }

        return retorno;
    }

    private static String getToken() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client;
        HttpRequest request;
        String retorno = "";
        String json = "{\"username\": \"sandboxws\",\"password\": \"!sdb2022#\",\"client\": \"SANDBOX\",\"agency\": \"2\"}";

        //cria um http client
        client = HttpClient.newHttpClient();

        //faz o request post com body definido
        request = HttpRequest.newBuilder()
                .uri(new URI("http://api.infotravel.com.br/api/v1/authenticate"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .setHeader("Content-Type", "application/json")
                .build();


        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(httpResponse.body().toString());

        retorno = jsonObject.getString("type")+" "+jsonObject.getString("accessToken");

        return retorno;
    }
}
