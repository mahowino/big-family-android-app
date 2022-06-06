package com.example.bigfamilyv20.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.bigfamilyv20.Entities.User;
import com.example.bigfamilyv20.Entities.bulk_array;
import com.example.bigfamilyv20.Fragments.basket;
import com.example.bigfamilyv20.Fragments.mainStore;
import com.example.bigfamilyv20.Fragments.EmptyBasketFragment;
import com.example.bigfamilyv20.Fragments.EmptyStoreFragment;
//import com.example.bigfamilyv20.Fragments.basket;
import com.example.bigfamilyv20.R;
import com.example.bigfamilyv20.Utils.Database_helper;
import com.example.bigfamilyv20.Utils.LoadingUtils;
import com.example.bigfamilyv20.Utils.shoppingCartUtilis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
private static ImageView menu;
private static final String collectionref="Users";
private static FirebaseAuth mauth=FirebaseAuth.getInstance();

private static LoadingUtils utils;
  private static List<String> documentIds;
    private static List<String> p_names;
    private static List<String> p_description;
    private static List<String> p_price;
    private static List<Long> p_prodId;
    private static List<Integer> p_amount;
    private static List<String>BdocumentIds,Bnames,Bdescription,Bprice;
    private static List<Long>BprodId;
    private static List<Integer>Bamount;
    private static  Boolean isStoreEmpty,isthere;
    private static Fragment selectedFragment;
    private static FloatingActionButton add;
    private static Database_helper helper;
    private static shoppingCartUtilis cartUtilis;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             selectedFragment=null;
            switch (item.getItemId()) {

                case R.id.navigation_home:

                    //initializing variables
                    p_prodId=new ArrayList<>();
                    p_names=new ArrayList<>();
                    p_description=new ArrayList<>();
                    p_price=new ArrayList<>();
                    p_amount=new ArrayList<>();
                    documentIds=new ArrayList<>();

                    //get the items
                    FirebaseFirestore dab = FirebaseFirestore.getInstance();
                    CollectionReference reference = dab.collection(collectionref);
                    reference.document(User.getId()).collection("Store").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                //items exist
                                //initializing variables
                                p_prodId.clear();
                                p_names.clear();
                                p_description.clear();
                                p_price.clear();
                                p_amount.clear();
                                documentIds.clear();
                                isStoreEmpty=true;
                                Log.i("tst", "onComplete: we are in oncreate");
                                for (DocumentSnapshot snapshot : task.getResult()) {

                                    //populating the arrayList
                                    isStoreEmpty=false;

                                    documentIds.add(snapshot.getId());
                                    String NAME = snapshot.get("p_name").toString();
                                    String DESC = snapshot.get("p_description").toString();
                                    String PRICE = snapshot.get("P_price").toString();
                                    int amount=snapshot.getLong("p_amount").intValue();
                                    Long PROD_ID = snapshot.getLong("P_ID");

                                    p_prodId.add(PROD_ID);
                                    p_names.add(NAME);
                                    p_price.add(PRICE);
                                    p_description.add(DESC);
                                    p_amount.add(amount);
                                    Log.i("MainPage", "onComplete: we are able to ge the data");

                                    //list.add(x,new ProductCard(NAME,DESC,PRICE));
                                    // Log.d("size of list",String.valueOf(list.size()));


                                }
                            }
                            if(isStoreEmpty==false){

                                //store contains items

                                selectedFragment=new mainStore(p_prodId,p_names,p_price,p_description,p_amount,documentIds);
                                Log.i("MainPage", "onComplete: store contains Items");

                            }
                            else if (isStoreEmpty==true){

                                //store is empty
                                selectedFragment=new EmptyStoreFragment();
                                Log.i("MainPage", "onComplete: store is empty");

                            }
                            else {

                                //error
                                selectedFragment=new EmptyStoreFragment();
                                Log.i("MainPage", "onComplete: Error display empty Store");

                            }

                            //inflate the actual fragment
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //failure To get data
                            selectedFragment=new EmptyBasketFragment();
                            Toast.makeText(MainPage.this,"Failure to get data",Toast.LENGTH_SHORT).show();

                        }
                    });

                   break;

                case R.id.viewBasket:

                    //if list is empty inflate EmptyBasketFragment
                    //else
                    shoppingCartUtilis util1=new shoppingCartUtilis(getApplicationContext());
                    SQLiteDatabase db= util1.getReadableDatabase();

                    if(util1.getDatabaseCount(db)==0){

                        //basket is empty
                        selectedFragment=new EmptyBasketFragment();
                        Log.i("MainPage", "onComplete: basket is empty");

                    }
                    else{
                        //inflate basket items
                        selectedFragment=new basket();
                        Log.i("MainPage", "onComplete: basket has goods");

                    }
                   // selectedFragment=new EmptyBasketFragment();

                    //inflate the fragment selected
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    break;

            }

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating the supportActionBar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_page, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

                case R.id.profile:

                    //inflate the profile page
                    Intent profile=new Intent(getApplicationContext(),ViewProfile.class);
                    startActivity(profile);
                    // archive(item);

                     return true;

                case R.id.Withdraw:

                    //inflate the activity
                    Intent withdraw=new Intent(getApplicationContext(),withdraw_items.class);
                    startActivity(withdraw);

                    return true;

                case R.id.settings:
                    //inflate the activity
                    Intent settings=new Intent(getApplicationContext(),settings.class);
                    startActivity(settings);

                     return true;


                case R.id.bulk_Up:


                    //initialization of the variables to be used
                    isthere=false;
                    BprodId=new ArrayList<>();
                    Bnames=new ArrayList<>();
                    Bdescription=new ArrayList<>();
                    Bprice=new ArrayList<>();
                    Bamount=new ArrayList<>();
                    BdocumentIds=new ArrayList<>();

                    //starting loading icon
                    utils.startLoadingDialog();

                    //initialization and declaration of firebase variable
                    FirebaseFirestore dab = FirebaseFirestore
                            .getInstance();
                    CollectionReference reference = dab
                            .collection(collectionref);

                    //getting the data in bulkStore
                    reference
                            .document(User.getId())
                            .collection("BulkStore")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){

                                for (DocumentSnapshot snapshot:task.getResult()){
                                    //items exist in the store
                                    isthere=true;

                                    BdocumentIds.add(snapshot.getId());
                                    String NAME = snapshot.get("b_name").toString();
                                    String DESC = snapshot.get("b_description").toString();
                                    String PRICE = snapshot.get("b_price").toString();
                                    int amount=snapshot.getLong("b_amount").intValue();
                                    Long PROD_ID = snapshot.getLong("b_productId");

                                    //populating the lists
                                    BprodId.add(PROD_ID);
                                    Bnames.add(NAME);
                                    Bprice.add(PRICE);
                                    Bdescription.add(DESC);
                                    Bamount.add(amount);

                                }

                                //dismiss the loading dialog
                                utils.dismissDialog();

                                if(isthere==true){

                                    //items exist in bulkStore
                                    bulk_array array=new bulk_array();
                                    array.setBnames(Bnames);
                                    array.setBprodId(BprodId);
                                    array.setBprice(Bprice);
                                    array.setBdescription( Bdescription);
                                    array.setBamount(Bamount);
                                    array.setBdocumentIds(BdocumentIds);

                                    //inflate the activity
                                    Intent bulk_up=new Intent(getApplicationContext(),bulk.class);
                                    startActivity(bulk_up);

                                }
                                else {

                                    //inflate empty activity
                                    Intent bulk=new Intent(getApplicationContext(),no_bulk_items.class);
                                    startActivity(bulk);

                                }

                            }

                            else{
                                //error , dismiss the dialog
                                utils.dismissDialog();
                            }

                        }
                    });
                     return true;

                case R.id.about_app:

                    // archive(item);

                    return true;

                case R.id.licences:

                     //delete(item);

                    return true;
                case R.id.log_out:


                    new AlertDialog.Builder(MainPage.this)
                        .setTitle("Confirm")
                        .setMessage("Do You Want to log out")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //logout dismissed
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            //sign out user
                            mauth.signOut();
                            //inflate new activity after logout
                            Intent intent=new Intent(getApplicationContext(),Login_Start_Activity.class);
                            startActivity(intent);
                            finish();

                             }
                        }).show();

                     return true;

                default:

                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
       // menu=(ImageView)findViewById(R.id.Menu_caller);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        utils=new LoadingUtils(this);

        //start loading screen
        utils.startLoadingDialog();

        //initialize variables
        p_prodId=new ArrayList<>();
        p_names=new ArrayList<>();
        p_description=new ArrayList<>();
        p_price=new ArrayList<>();
        p_amount=new ArrayList<>();
        documentIds=new ArrayList<>();

        //clear databases
        helper=new Database_helper(this);
        helper.clearTable();
        cartUtilis=new shoppingCartUtilis(this);
        cartUtilis.clearTable();

        //get the data

        FirebaseFirestore dab = FirebaseFirestore.getInstance();
        CollectionReference reference = dab.collection(collectionref);

        reference
                .document(User.getId())
                .collection("Store")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    //data exists
                     isStoreEmpty=true;

                    Log.i("MainPage", "onCreate: Data exists and is gotten ");
                    for (DocumentSnapshot snapshot : task.getResult()) {

                        isStoreEmpty=false;

                        documentIds.add(snapshot.getId());
                        String NAME = snapshot.get("p_name").toString();
                        String DESC = snapshot.get("p_description").toString();
                        String PRICE = snapshot.get("P_price").toString();
                        int amount=snapshot.getLong("p_amount").intValue();
                        Long PROD_ID = snapshot.getLong("P_ID");

                        //populate the lists
                        p_prodId.add(PROD_ID);
                        p_names.add(NAME);
                        p_price.add(PRICE);
                        p_description.add(DESC);
                        p_amount.add(amount);

                        Log.i("MainPage", "onComplete: we are able to ge the data");
                        //list.add(x,new ProductCard(NAME,DESC,PRICE));
                        // Log.d("size of list",String.valueOf(list.size()));

                    }
                }
                if (isStoreEmpty == true) {

                    //inflate the fragments
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EmptyStoreFragment()).commit();
                    utils.dismissDialog();

                    Log.i("tst", "onCreate: Container inflated with store items");
                }
                else{
                    //inflate the fragments
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new mainStore(p_prodId,p_names,p_price,p_description,p_amount,documentIds)).commit();
                   utils.dismissDialog();

                    Log.i("tst", "onCreate: Container inflated with empty store items");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}
