package com.sw551.fairfield.healthcheq.withings;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import android.os.AsyncTask;

public class WithingsAccess extends AsyncTask<Void, Integer, Token>
{
    String apiKey = "1db033576b61cb595e9e15d964f021fb9743bb3b626914d7ef6eff4e6";
    String apiSecret = "3692332b4244dbfc18eec29c6e001b07e1f78000993e41c74df3e64fd54c1";
    OAuthService service;
    static Token requestToken;
    Token accessToken;
    Verifier verifier;
    static String withingsVerifier;

    public static void recordAndAlignTask(String verif, Token reqToken) {
        withingsVerifier = verif;
        requestToken = reqToken;

    }

    @Override
    protected Token doInBackground(Void... params) {

        service = new ServiceBuilder()
                .provider(WithingsApi.class)
                .apiKey(apiKey).apiSecret(apiSecret)
                .signatureType(SignatureType.QueryString)
                .build();

        verifier = new Verifier(withingsVerifier);
        accessToken = service.getAccessToken(requestToken, verifier);

        return accessToken;
    }


    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {

    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Token acToken) {

    }

}
