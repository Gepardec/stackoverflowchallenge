/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

/**
 *
 * @author praktikant_ankermann
 */
public class EndpointUtils {
    /**
     * Base URL to the StackExchange API
     */
    private static final String BASE_URL = "http://api.stackexchange.com/2.2/";
    
    /**
     * Sends a request to the StackExchange API and retrieves data in .gzip format.
     * After unzipping the response, the method checks what type of response it gets.
     * It could either be a JSON object with the key "error_id" or a regular JSON object with an empty "items" array.
     * These two cases mean that there was no successful response.
     * 
     * @param resourceURL the endpoint to connect with (e.g users/, answers/ or questions/)
     * @param requestMethod the HTTP method of the request (e.g GET, PUT, POST, ...)
     * @return null if any Exception occurs or at least one of the two error responses occur, else the response packed in a JsonObject
     */
    public static final JsonObject sendRequestAndGetJson(String resourceURL, String requestParams, String requestMethod) {
        BufferedReader in = null;
        JsonObject o;
        JsonReader r;
        try {
            in = createBufferedReader(resourceURL, requestParams, requestMethod);
            r = Json.createReader(in);
            o = r.readObject();
            if (o.containsKey("error_id") || o.getJsonArray("items").isEmpty()) {
                return null;
            } else {
                return o;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                return null;
            }
        }

    }
    
    /**
     * Creates a BufferedReader from a given HttpURLConnection to the StackExchange API.
     * 
     * @param resourceURL the endpoint to connect with (e.g users/, answers/ or questions/)
     * @param requestMethod the HTTP method of the request (e.g GET, PUT, POST, ...)
     * @return BufferedReader from the HttpURLConnection
     * @throws ProtocolException
     * @throws IOException 
     */
    private static BufferedReader createBufferedReader(String resourceURL, String requestParams, String requestMethod) throws ProtocolException, IOException {
        HttpURLConnection con = openHttpURLConnection(resourceURL, requestParams);
        con.setRequestMethod(requestMethod);
        return new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
    }
    
    /**
     * Opens and returns a HttpURLConnection to the StackExchange API.
     * 
     * @param resourceURL the endpoint to connect with (e.g users/, answers/ or questions/)
     * @return the opened HttpURLConnection 
     * @throws MalformedURLException
     * @throws IOException 
     */
    private static HttpURLConnection openHttpURLConnection(String resourceURL, String requestParams) throws MalformedURLException, IOException {
        URL obj = new URL(BASE_URL + resourceURL + requestParams);
        return (HttpURLConnection) obj.openConnection();
    }
    
    /**
     * Is used to make the return statement of the endpoints more readable
     * 
     * @return Response of type NOT_ACCEPTABLE (406)
     */
    public static final Response notAcceptable() {
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    /**
     * Is used to make the return statement of the endpoints more readable
     * 
     * @return Response of type NOT_FOUND (404)
     */
    public static final Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
