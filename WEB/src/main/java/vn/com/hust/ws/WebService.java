package vn.com.hust.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.hust.utils.CommonConstant;

/**
 * Call API Web service
 */
public class WebService implements java.io.Serializable
{
    private static final long serialVersionUID = -7597475861142562504L;
    private static final Logger LOG = LoggerFactory.getLogger(WebService.class);

    private final static String IS_AUTHENTICATED = "isAuthenticated";

    public WebService()
    {
    }

    /**
     * Call API voi method GET
     */
    public String apiServiceMethodGET(HttpServletRequest request, String patch, String shopId, String data) throws Exception
    {
        String nomeMetodo = ".apiServiceMethodGET() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient httpClient = HttpClientBuilder.create().build();
        try
        {
            // Define a postRequest request
            HttpGet getRequest = new HttpGet(patch);

            // Set the API media type in http content-type header
            getRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            getRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString());

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(getRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }

    /**
     * Call API voi method GET
     */
    public  String apiServiceMethodGETWithParam(HttpServletRequest request, String patch, String shopId, String data, HashMap<String, String> param)
            throws Exception
    {
        String nomeMetodo = ".apiServiceMethodGETWithParam() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient client = HttpClientBuilder.create().build();
        try
        {

            // Set the request post param
            List<NameValuePair> urlParameters = new ArrayList<>();
            for (Entry<String, String> select : param.entrySet())
            {
                urlParameters.add(new BasicNameValuePair(select.getKey(), select.getValue()));
            }

            // Define a postRequest request
            HttpGet getRequest = new HttpGet(patch);

            // Set the API media type in http content-type header
            getRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            getRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString());

            URI uri = new URIBuilder(getRequest.getURI()).addParameters(urlParameters).build();
            getRequest.setURI(uri);

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = client.execute(getRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            client.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }

    /**
     * Call API voi method POST
     */
    public String apiServiceMethodPOST(HttpServletRequest request, String patch, String shopId, String data) throws Exception
    {
        String nomeMetodo = ".apiServiceMethodPOST() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient httpClient = HttpClientBuilder.create().build();
        try
        {
            // Define a postRequest request
            HttpPost postRequest = new HttpPost(patch);

            // Set the API media type in http content-type header
            postRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            postRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE.toString());

            // Set the request post body
            StringEntity userEntity = new StringEntity(data, "UTF-8");
            postRequest.setEntity(userEntity);

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(postRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }

    /**
     * Call API voi method POST
     */
    public String apiServiceMethodPOSTWithPara(HttpServletRequest request, String patch, String shopId, String data, HashMap<String, String> param)
            throws Exception
    {
        String nomeMetodo = ".apiServiceMethodPOSTWithPara() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient httpClient = HttpClientBuilder.create().build();
        try
        {
            // Define a postRequest request
            HttpPost postRequest = new HttpPost(patch);

            // Set the API media type in http content-type header
            postRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            postRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE.toString());

            // Set the request post param
            List<NameValuePair> urlParameters = new ArrayList<>();
            for (Entry<String, String> select : param.entrySet())
            {
                urlParameters.add(new BasicNameValuePair(select.getKey(), select.getValue()));
            }
            postRequest.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(postRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }

    /**
     * Call API voi method PUT
     */
    public String apiServiceMethodPUTWithParam(HttpServletRequest request, String patch, String shopId, String data, HashMap<String, String> param)
            throws Exception
    {
        String nomeMetodo = ".apiServiceMethodPUTWithParam() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient client = HttpClientBuilder.create().build();
        try
        {
            // Set the request post param
            List<NameValuePair> urlParameters = new ArrayList<>();
            for (Entry<String, String> select : param.entrySet())
            {
                urlParameters.add(new BasicNameValuePair(select.getKey(), select.getValue()));
            }

            // Define a postRequest request
            HttpPut getRequest = new HttpPut(patch);

            // Set the API media type in http content-type header
            getRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            getRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString());

            URI uri = new URIBuilder(getRequest.getURI()).addParameters(urlParameters).build();
            getRequest.setURI(uri);

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = client.execute(getRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            client.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }

    /**
     * Call API voi method PUT
     */
    public String apiServiceMethodPUT(HttpServletRequest request, String patch, String shopId, String data) throws Exception
    {
        String nomeMetodo = ".apiServiceMethodPOST() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient httpClient = HttpClientBuilder.create().build();
        try
        {
            // Define a postRequest request
            HttpPut postRequest = new HttpPut(patch);

            // Set the API media type in http content-type header
            postRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            postRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE.toString());

            // Set the request post body
            StringEntity userEntity = new StringEntity(data, "UTF-8");
            postRequest.setEntity(userEntity);

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(postRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }
    public String apiServiceMethodPOSTRefreshToken(HttpServletRequest request, String patch, String shopId, String data,String transId) throws Exception
    {
        String nomeMetodo = ".apiServiceMethodPOSTRefreshToken() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        StringBuffer result = new StringBuffer("");
        HttpClient httpClient = HttpClientBuilder.create().build();
        try
        {
            // Define a postRequest request
            HttpPost postRequest = new HttpPost(patch);

            // Set the API media type in http content-type header
            postRequest.addHeader("Accept", MediaType.APPLICATION_JSON.toString());
            postRequest.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE.toString());
            postRequest.addHeader("Authorization", "Bearer " + transId);
            postRequest.addHeader("ShopId", shopId);

            // Set the request post body
            StringEntity userEntity = new StringEntity(data, "UTF-8");
            postRequest.setEntity(userEntity);

            // Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(postRequest);

            // verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                result.append(line);
            }
            br.close();

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        finally
        {
            // Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return result.toString();
    }
}
