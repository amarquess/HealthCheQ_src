package com.sw551.fairfield.healthcheq;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sw551.fairfield.healthcheq.withings.WithingsAccess;
import com.sw551.fairfield.healthcheq.withings.WithingsAuthorization;

import org.scribe.model.Token;

public class AuthActivity extends ActionBarActivity {

    WebView withingsWebView;
    String withingsVerifier;
    //String withingsRequestToken;
    Token withingsRequestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        withingsWebView = (WebView) findViewById(R.id.webView);

        withingsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code
               // Log.d("My Webview", url);
                if(url.contains("http://oauth.withings.com/account/oob"))
                {
                    Uri callbackAuth = Uri.parse(url);
                    String withingsUserId = callbackAuth.getQueryParameter("userid");
                    SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("withings_user", withingsUserId);
                    editor.commit();

                    withingsVerifier = callbackAuth.getQueryParameter("oauth_verifier");

                    WithingsAccess webAccess = new WithingsAccess(){
                        @Override
                        protected void onPostExecute(Token acToken)
                        {
                            SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("accessToken", acToken.getToken());
                            editor.putString("accessTokenSecret", acToken.getSecret());
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Withings Server Connected.", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    };

                    webAccess.recordAndAlignTask(withingsVerifier, withingsRequestToken);
                    //new WithingsAuthorization().execute();
                    webAccess.execute();



                }

                // return true; //Indicates WebView to NOT load the url;
                return false; //Allow WebView to load url
            }
        });
        withingsWebView.getSettings().setJavaScriptEnabled(true);
        withingsWebView.requestFocus(View.FOCUS_DOWN);

        WithingsAuthorization webAuth = new WithingsAuthorization() {
            @Override
            protected void onPostExecute(Token reqToken)
            {
                withingsRequestToken = reqToken;
            }
        };
        webAuth.recordAndAlignTask(withingsWebView);
        //new WithingsAuthorization().execute();
        webAuth.execute();

        //withingsWebView.loadUrl("http://www.google.com");

    }






}
