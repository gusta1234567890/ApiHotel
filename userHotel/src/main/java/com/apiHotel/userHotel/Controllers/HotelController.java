package com.apiHotel.userHotel.Controllers;

import com.apiHotel.userHotel.Resquesties.HotelRequest;
import com.apiHotel.userHotel.entities.*;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value = "")
public class HotelController {
    private final String urlHoteis = "http://api.infotravel.com.br/api/v1/avail/hotel?destination=%d&start=%s&end=%s&occupancy=%d";
    private final String urlHotel = "http://api.infotravel.com.br/api/v1/utility/hotelDetail/";
    private static Date second =  new Date();
    private static HotelRequest request =  null;

    @GetMapping(value = "/search/{destination}/{start}/{end}/{occupancy}")
    public String Hoteis(@PathVariable int destination, @PathVariable String start, @PathVariable String end, @PathVariable int occupancy)
    {
        String retornoRequest;
        ArrayList<SimpleHotel> hoteis = new ArrayList<>();
        JSONObject jsObject;
        JSONArray jsonArray;
        String retorno =  null;
        Date dtAux;

        try
        {
            //recebe data atual
            dtAux = new Date();

            //filtro que verica se o tolken ainda é valido
            if (dtAux.compareTo(second) >= 0)
            {
                //se não for valido cria um novo tolken
                request = new HotelRequest();
                //depois de criado seta o filtro para funcionar a 7200 secundo depois
                second.setTime(second.getTime() + 7200*60);
            }

            //faz o get request
            retornoRequest = request.Request(String.format(urlHoteis, destination, start, end, occupancy));

            //recebe a resposta da request e tranforma em um json object
            jsObject = new JSONObject(retornoRequest);

            //extrai o json array do json
            jsonArray = new JSONArray(jsObject.getJSONArray("hotelAvail"));

            //percore a array json
            for (Object item: jsonArray)
            {
                //faz o cast para jsonobject por que o item vem em tipo Object
                jsObject = (JSONObject) item;

                //extrai as informações do json e tranforma em simple hotel
                hoteis.add(new SimpleHotel(getHotel(jsObject)));
            }

            //transforma a lista de hoteis em json
            Gson gson = new Gson();
            retorno = "{\"hotels\":"+ gson.toJson(hoteis) +"}";
        }
        catch (Exception e)
        {
            retorno = "Bad Request \n Try Again";
        }

        //retorna a string json
        return retorno;
    }

    @GetMapping(value = "/hotel/{keyDetail}")
    public String Hotel(@PathVariable String keyDetail)
    {
        String retornoRequest;
        Hotel hotel =  null;
        JSONObject jsObject = null;
        String retorno = "";
        Gson gson;
        Date dtAux;

        try
        {
            //recebe data atual
            dtAux = new Date();

            //filtro que verica se o tolken ainda é valido
            if (dtAux.compareTo(second) >= 0)
            {
                //se não for valido cria um novo tolken
                request = new HotelRequest();
                //depois de criado seta o filtro para funcionar a 7200 secundo depois
                second.setTime(second.getTime() + 7200*60);
            }

            //faz o get request
            retornoRequest = request.Request(urlHotel + keyDetail );

            //recebe o json do hotel
            jsObject = new JSONObject("\\");

            //instancia o classe hotel seta o key e as outras informações
            hotel = new Hotel();
            hotel.setKeyDetail(keyDetail);
            hotel = getHotel(jsObject);

            //tranforma a classe em json
            gson = new Gson();
            retorno = "{\"hotel\":"+ gson.toJson(hotel) +"}";
        }
        catch (Exception e)
        {
            retorno = "Bad Request \n Try Again";
        }

        //retorna o json
        return retorno;
    }

    private Hotel getHotel(JSONObject jsonObject)
    {
        //instancia a variavel hotel que vai servir de base para o resto das funções
        Hotel hotel = new Hotel();
        //recebe dados basicos do hotel
        getHotelAux(jsonObject, hotel);

        //se tem lowestFare define o check in e o check out
        if(jsonObject.has("lowestFare"))
        {
            getCheckInAndOut(jsonObject, hotel);
        }

        //seta a descrição
        hotel.setDescription("Descrição do hotel");

        //retorna a hotel
        return hotel;
    }

    private static void getHotelAux(JSONObject jsonObject, Hotel hotel)
    {
        //faz um parse pagar o elmento hotel
        JSONObject jAux = jsonObject.getJSONObject("hotel");

        //se tem a chave id define id o id
        if(jAux.has("id"))
        {
            hotel.setId(jAux.getInt("id"));
        }
        //se tiver keyDetail define o keyDetail
        if(jAux.has("keyDetail"))
        {
            hotel.setKeyDetail(jAux.getString("keyDetail"));
        }

        //define o nome
        hotel.setName(jAux.getString("name"));

        //se tem images seta images
        if(jAux.has("images"))
        {
            getImage(jAux, hotel);
        }

        //sete o adress
        getAdress(jAux, hotel);
    }

    private static void getImage(JSONObject jsonObject, Hotel hotel)
    {
        //cria um objeto imagem
        Imagem imagem = new Imagem();

        //da um parse no json pai e paga o array images
        JSONArray jAuxArray = jsonObject.getJSONArray("images");
        JSONObject jAux;

        //percore as imagens e seta o valor de cada tamanho
        for (Object item: jAuxArray) {
            jAux = (JSONObject) item;
            imagem.setSmall(jAux.getString("small"));
            imagem.setMedium(jAux.getString("medium"));
            imagem.setLarge(jAux.getString("large"));

            //deine a imagens do hotel
            hotel.setImages(imagem);
        }
    }

    private static void getAdress(JSONObject jsonObject, Hotel hotel)
    {
        //cria um objeto endereco
        Endereco endereco = new Endereco();

        //parse no json pai para pegar o item adress
        JSONObject jAux = jsonObject.getJSONObject("address");

        //se tem zipcode define zipcode
        if (jAux.has("zipcode"))
        {
            endereco.setZipcode(jAux.getInt("zipcode"));
        }
        //se tem number define number
        if (jAux.has("number"))
        {
            endereco.setNumber(jAux.getInt("number"));
        }
        //se tem address define address
        if (jAux.has("address"))
        {
            endereco.setAddresss(jAux.getString("address"));
        }
        //se tem neighborhood define neighborhood
        if (jAux.has("neighborhood"))
        {
            endereco.setNeighborhood(jAux.getString("neighborhood"));
        }
        //se tem complement define complement
        if (jAux.has("complement"))
        {
            endereco.setComplement(jAux.getString("complement"));
        }

        //seta coordenadas do endereco
        getCoordinates(jAux, endereco);

        //seta a cidade do endereco
        getCity(jAux, endereco);

        //seta o address do hotel
        hotel.setAddress(endereco);
    }

    private static void getCoordinates(JSONObject jsonObject, Endereco endereco)
    {
        //cria uma classe coordenadas
        Cordenadas cordenadas = new Cordenadas();

        //da um parse no json pai
        JSONObject jAux = jsonObject.getJSONObject("coordinates");

        //pega a latitude e logitude do endereco
        cordenadas.setLatitude(jAux.getFloat("latitude"));
        cordenadas.setLogtitude(jAux.getFloat("longitude"));

        //seta as coordenadas do endereco
        endereco.setCoordinates(cordenadas);
    }

    private static void getCity(JSONObject jsonObject, Endereco endereco)
    {
        //cria um objeto cidade
        Cidade cidade = new Cidade();

        //da um parse no json pai para pegar o valor city
        JSONObject jAux = jsonObject.getJSONObject("city");

        //seta o nome da cidade
        cidade.setNome(jAux.getString("name"));

        //seta o pais da cidade
        getCountry(jAux, cidade);

        //seta a cidade do endereço
        endereco.setCidade(cidade);
    }

    private static void getCountry(JSONObject jsonObject, Cidade cidade)
    {
        //cria um objeto pais
        Pais pais = new Pais();

        //da um parse no json country
        JSONObject jAux = jsonObject.getJSONObject("country");

        //se tem um code define o code
        if (jAux.has("code")){
            pais.setCode(jAux.getString("code"));
        }
        //se tem um name define um name
        if (jAux.has("name")){
            pais.setName(jAux.getString("name"));
        }

        //define o pais da cidade
        cidade.setPais(pais);
    }

    private static void getCheckInAndOut(JSONObject jsonObject, Hotel hotel){
        //parse no pai para pegar o lowesFare
        JSONObject jAux = jsonObject.getJSONObject("lowestFare");

        //seta o checkIn e o checkOut do hotel
        hotel.setCheckIn(jAux.getString("checkIn"));
        hotel.setCheckOut(jAux.getString("checkOut"));
    }
}
