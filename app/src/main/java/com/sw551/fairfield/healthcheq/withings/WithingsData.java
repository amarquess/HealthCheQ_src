package com.sw551.fairfield.healthcheq.withings;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.sw551.fairfield.healthcheq.Record;
import com.sw551.fairfield.healthcheq.SqlDbHelper;

import java.util.ArrayList;
import java.util.List;

public class WithingsData extends AsyncTask<Void, Integer, ArrayList<Record>>
{
    private Context context;
	//private static Dialog dialog;
	static TextView tv;
    ArrayList<Record> db_record;
    private final Gson gson;
    private final JsonParser jsonParser;

    String apiKey = "1db033576b61cb595e9e15d964f021fb9743bb3b626914d7ef6eff4e6";
    String apiSecret = "3692332b4244dbfc18eec29c6e001b07e1f78000993e41c74df3e64fd54c1";
    OAuthService service;
    static String userId;
    static Token accessToken;



    public WithingsData(Context context) {

        this.context=context;
        this.gson = createGsonBuilder().create();
        this.jsonParser = new JsonParser();
    }


    public static void recordAndAlignTask(String withingsUserId, Token acToken) {
        //tv = mainTextView;
        userId = withingsUserId;
        accessToken = acToken;
    }

	@Override
	protected ArrayList<Record> doInBackground(Void... params) {

		String output = "";
        ArrayList<Record> output_records;

        service = new ServiceBuilder()
                .provider(WithingsApi.class)
                .apiKey(apiKey).apiSecret(apiSecret)
                .signatureType(SignatureType.QueryString)
                .build();

        OAuthRequest request = new OAuthRequest(Verb.GET, "http://wbsapi.withings.net/measure");
        request.addQuerystringParameter("userid", userId);
        request.addQuerystringParameter("action", "getmeas");
        service.signRequest(accessToken, request);

        //OAuthRequest request = new OAuthRequest(Verb.GET, "http://wbsapi.withings.net/measure?action=getmeas&userid=4991622&oauth_consumer_key=1db033576b61cb595e9e15d964f021fb9743bb3b626914d7ef6eff4e6&oauth_nonce=df3a3adeaba1d7214abb36729c84dc4b&oauth_signature=wKfDRPJcx0x1CPtwgx%2BgZl2hi%2BU%3D&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1415862994&oauth_token=de21b24f332959d963918cce14e620990097e026fd42cf687bbe5e9c48&oauth_version=1.0");

        Response response = request.send();
		output = response.getBody();

        output_records = convertData(output);

		return output_records;
	}

	
	  // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {

    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(ArrayList<Record> result) {

        //override in MainActivity.java

    }

    private ArrayList convertData(String withings_records)
    {
        ArrayList<Record> records = new ArrayList<Record>();
        List<MeasureGroup> withings_measures = new ArrayList<>();
        String record_test = "";

        try {

            JsonObject jsonObject = new JsonParser().parse(withings_records).getAsJsonObject();

            int status = jsonObject.get("status").getAsInt();

            if (status == 0) {
                JsonElement body = jsonObject.get("body");
                withings_measures = gson.fromJson(body.getAsJsonObject(),
                        MeasureResult.class).measureGroups;
            }
//            else {
//                throw new WithingsConnectionException(
//                        "Withings API call failed: " + status);
//            }

            for(MeasureGroup measure : withings_measures)
            {
                Record record = new Record();

                record.setDate(Integer.toString(measure.date));

                for(Measure item : measure.measures)
                {
                    if(item.type == MeasureType.WEIGHT)
                    {
                        record.setWeight(item.getActualValue());
                    }
                }
                records.add(0,record);
            }
        } catch (Exception ex) {
//            throw new WithingsConnectionException("Could not connect to URL: "
//                    + ex.getMessage(), ex);
        }

        //Json jsonObj = new Json();
        //Gson gson = new Gson();


        return records;
    }

    private GsonBuilder createGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MeasureType.class,
                new JsonDeserializers.MeasureTypeJsonDeserializer());
        gsonBuilder.registerTypeAdapter(Category.class,
                new JsonDeserializers.CategoryJsonDeserializer());
        gsonBuilder.registerTypeAdapter(Attribute.class,
                new JsonDeserializers.AttributeJsonDeserializer());
        return gsonBuilder;
    }

    private void cleanInvalidRecords(ArrayList<Record> records)
    {
        int compare_number = 0;
        for(Record item : records)
        {
            compare_number = Double.compare(item.getWeight(), 0);
            if(compare_number == 0)
            {
                records.remove(item);
                cleanInvalidRecords(records);
                break;
            }


        }

    }

    private Record addRecordsOnDB(ArrayList<Record> records)
    {
        SqlDbHelper db = SqlDbHelper.getInstance(context);
        Record last_record;

        last_record = db.getLastRecord(1);

        if(last_record == null)
        {
            last_record = new Record();
        }

        for(Record item : records)
        {
            if(Integer.parseInt(last_record.getDate()) < Integer.parseInt(item.getDate()))
            {
                db.addRecord(item, 1);
            }
        }

        return last_record;
    }


	
}
