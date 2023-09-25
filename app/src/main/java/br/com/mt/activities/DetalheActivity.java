package br.com.mt.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import br.com.mt.R;
import br.com.mt.models.MaisVend;
import br.com.mt.models.PromoModel;
import br.com.mt.models.ShowAllModel;

public class DetalheActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addtoCart, buyNow;
    ImageView addItems,removeItems;
    Toolbar toolbar;
    int totalQuantiy = 1;
    int totalPrice = 0;

    MaisVend maisVend = null;
    PromoModel promoModel = null;

    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detalhes");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if(obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detalhe_img);
        quantity = findViewById(R.id.quantity);

        name = findViewById(R.id.detalhe_nome);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detalhe_desc);
        price = findViewById(R.id.detalhe_price);

        addtoCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        if(newProductsModel != null){

            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            name.setText(newProductsModel.getName());

            totalPrice = newProductsModel.getPrice() * totalQuantiy;

        }
        if(popularProductsModel != null){

            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());

            totalPrice = popularProductsModel.getPrice() * totalQuantiy;

        }

        if(showAllModel != null){

            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());

            totalPrice = showAllModel.getPrice() * totalQuantiy;

        }
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalheActivity.this, AddressActivity.class);
                if(newProductsModel != null){
                    intent.putExtra("item", newProductsModel);

                }
                if(popularProductsModel != null){
                    intent.putExtra("item", popularProductsModel);
                }
                if(showAllModel != null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);
            }
        });
        addItems.setOnClickListener(view -> {

            if(totalQuantiy < 10){
                totalQuantiy++;
                quantity.setText(String.valueOf(totalQuantiy));
                if(newProductsModel != null){
                    totalPrice = newProductsModel.getPrice() * totalQuantiy;
                }
                if(popularProductsModel != null){
                    totalPrice = popularProductsModel.getPrice() * totalQuantiy;
                }if(showAllModel != null){
                    totalPrice = showAllModel.getPrice() * totalQuantiy;
                    totalPrice = showAllModel.getPrice() * totalQuantiy;
                }
            }

        });
        removeItems.setOnClickListener(view -> {

            if(totalQuantiy > 1){
                totalQuantiy--;
                quantity.setText(String.valueOf(totalQuantiy));
                totalPrice = showAllModel.getPrice() * totalQuantiy;
            }
        });


        addtoCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addtoCart();


            }

            private void addtoCart() {

                String saveCurrentTime, saveCurrentDate;
                Calendar calForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("totalQuantity", quantity.getText().toString());
                cartMap.put("totalPrice", totalPrice);


                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(DetalheActivity.this, "Adicionado no Carrinho", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

            }

        });
    }
}