package com.suricapp.rest.client;

/**
 * Created by Franck on 16/03/15.
 */
public interface RestClientInterface {

    /*

     */
    public String PUT(String url,Object param);
    public String GET(String url);
    public String POST(String url,Object param);
    public String DELETE(String url);
}
