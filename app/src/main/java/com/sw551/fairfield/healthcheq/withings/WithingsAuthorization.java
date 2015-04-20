package com.sw551.fairfield.healthcheq.withings;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import android.os.AsyncTask;
import android.webkit.WebView;

    public class WithingsAuthorization extends AsyncTask<Void, Integer, Token>
{
    static WebView bindingWeb;
    String apiKey = "1db033576b61cb595e9e15d964f021fb9743bb3b626914d7ef6eff4e6";
    String apiSecret = "3692332b4244dbfc18eec29c6e001b07e1f78000993e41c74df3e64fd54c1";
    OAuthService service;
    String authorizationUrl;
    Token requestToken;

    public static void recordAndAlignTask(WebView userWeb) {

        bindingWeb = userWeb;
    }

    @Override
    protected Token doInBackground(Void... params) {

        service = new ServiceBuilder()
                .provider(WithingsApi.class)
                .apiKey(apiKey).apiSecret(apiSecret)
                .signatureType(SignatureType.QueryString)
                .build();

        requestToken = service.getRequestToken();
        authorizationUrl = service.getAuthorizationUrl(requestToken);



        bindingWeb.loadUrl(authorizationUrl);

        //return requestToken.getToken();
        return requestToken;

    }


    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {

    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Token reqToken) {

        //bindingWeb.loadUrl(authorizationUrl);
    }

}
