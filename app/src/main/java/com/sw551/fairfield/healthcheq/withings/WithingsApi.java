package com.sw551.fairfield.healthcheq.withings;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class WithingsApi extends DefaultApi10a
{
	  private static final String AUTHORIZE_URL = "https://oauth.withings.com/account/authorize?oauth_token=%s";

	  @Override
	  public String getAccessTokenEndpoint()
	  {
	    return "https://oauth.withings.com/account/access_token";
	  }

	  @Override
	  public String getRequestTokenEndpoint()
	  {
	    return "https://oauth.withings.com/account/request_token";
	  }

	  @Override
	  public String getAuthorizationUrl(Token requestToken)
	  {
	    return String.format(AUTHORIZE_URL, requestToken.getToken());
	  }

	}
