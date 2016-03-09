package libraries;

import com.google.gson.Gson;
import com.stripe.Stripe;
import data.LoadProperties;
import maps.CollectionOfCharges;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.TestException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

/**
 * Created by tmaher on 2/16/2016.
 */
public class StripeUtils {
    private static String getAPIKey() {
        return Stripe.apiKey = LoadProperties.qaData.get("stripe.api.key").toString();
    }

    private static String getAPIVersion() {
        return Stripe.apiVersion = LoadProperties.qaData.get("stripe.api.version").toString();
    }

    private static void getAPIKey(Locale country) {
        getAPIVersion();
        if (country.equals(Locale.US)) {
            getAPIKey();
        } else {
            throw new TestException("ERROR: Stripe is used only for US. Country entered: " + country);
        }
    }

    public StripeUtils(Locale country) {
        getAPIKey(country);
    }

    private static StripeUtils setAPIKey(Locale country) {
        return new StripeUtils(country);
    }

    public CollectionOfCharges getListOfCharges(Integer limit){
        CollectionOfCharges chargesRetrieved = new CollectionOfCharges();
        Gson gson = new Gson();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https")
                .setHost("api.stripe.com")
                .setPath("/v1/charges")
                .setParameter("limit", limit.toString());
        try {
            URI uri = uriBuilder.build();
            HttpGet httpGet = new HttpGet(uri);
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(Stripe.apiKey, ""));
            CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
            HttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                String errorMessage = "ERROR: Attempting to Capture Charge: " + statusCode + "\n"
                        + "Reason: " +
                        response.getStatusLine().getReasonPhrase();
                throw new TestException (errorMessage);
            }
            String json = EntityUtils.toString(response.getEntity());
            chargesRetrieved = gson.fromJson(json, CollectionOfCharges.class);
            client.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chargesRetrieved;
    }
}
