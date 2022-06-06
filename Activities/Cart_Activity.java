package com.example.bigfamilyv20.Activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.utils.Pair;
import com.example.bigfamilyv20.Entities.ProductCard;
import com.example.bigfamilyv20.R;
import com.example.bigfamilyv20.Utils.Database_helper;
import com.example.bigfamilyv20.Utils.Mpesa;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Cart_Activity extends AppCompatActivity  {
private static TextView cartPrice,serviceCharge;
private static Button pay;
private static String PAYBILL="174379",reason,number,docs,pass,docId;
private static String PASSKEY="bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
private static String CONS_KEY="vlNnSf7ywwY6pPmS8Al6JzUVvwuwLrvK";
private static String CONSUMER_SECRET="a1YyGz3UfXDHcf3S";
    String callbackurl="http://mpesa-requestbin.herokuapp.com/17gpvp51";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);

        final String price=getIntent().getExtras().getString("price");
        String service=getIntent().getExtras().getString("Service");
        number=getIntent().getExtras().getString("number");

        cartPrice=(TextView)findViewById(R.id.total_amountdue);
        serviceCharge=(TextView)findViewById(R.id.service_charge);
        serviceCharge.setText(service);
        pay=(Button)findViewById(R.id.paybutton);
        cartPrice.setText(price);
        reason=getIntent().getExtras().getString("reason");
        docs=getIntent().getExtras().getString("docs") ;
        pass=getIntent().getExtras().getString("pass");
        docId=getIntent().getExtras().getString("userDocId");
        final Daraja daraja = Daraja.with(CONS_KEY, CONSUMER_SECRET, new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(  Cart_Activity.this.getClass().getSimpleName(), accessToken.getAccess_token());

            }

            @Override
            public void onError(String error) {
                Log.e(Cart_Activity.this.getClass().getSimpleName(), error);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        PASSKEY,  //https://developer.safaricom.co.ke/test_credentials
                        TransactionType.CustomerBuyGoodsOnline,
                        String.valueOf(1),
                        "254769694842",
                        "174379",
                        "254769694842",
                        callbackurl,
                        "001ABC",
                        "Goods Payment"
                );


                daraja.requestMPESAExpress(lnmExpress,
                        new DarajaListener<LNMResult>() {
                            @Override
                            public void onResult(@NonNull LNMResult lnmResult) {
                                try {
                                    /*
                                   JSONObject object= Mpesa.getJSONObjectFromURL(callbackurl);
                                  String ON= object.get("CallbackMetadata").toString();
                                  */
                                    Intent intent=new Intent(getApplicationContext(),OrderConfirmed.class);
                                    intent.putExtra("charge",price);
                                    intent.putExtra("reason",reason);
                                    intent.putExtra("number",number);
                                    intent.putExtra("docs",docs);
                                    intent.putExtra("pass",pass);
                                    intent.putExtra("userDocId",docId);

                                    startActivity(intent);
                                    finish();
                                }catch (Exception e){

                                }

                            }
                            @Override
                            public void onError(String error) {
                                Log.i("the error unatafuta", error);
                                Toast.makeText(getApplicationContext(), "Error processing payment", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }

        });


    }
    /*
    private void sendStk(String BUSINESS_SHORT_CODE,String PASSKEY,int amount,String phone ){

        STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, amount,BUSINESS_SHORT_CODE, phone);
        STKPush push = builder.build();
        Mpesa.getInstance().pay(Cart_Activity.this, push);



    }
    */
}
