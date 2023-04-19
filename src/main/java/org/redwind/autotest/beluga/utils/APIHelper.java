package org.redwind.autotest.beluga.utils;


import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.net.URL;

public interface APIHelper
{
    Response get(ContentType contentType, URL endpoint, Header authorization);
    Response post(ContentType contentType, URL endpoint, Header authorization, File filePath);
    Response delete(URL endpoint, Header authorization);
    Response patch(URL endpoint, Header authorization, ContentType contentType, File body);
    Response put(URL endpoint, Header authorization, ContentType contentType, File body);
}
