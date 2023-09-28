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
import androidx.appcompat.app.AppCompatActivity;

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
import br.com.mt.models.NewProductsModel;
import br.com.mt.models.ShowAllModel;

public class DetalheActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView name, description, price, quantity;
    Button addtoCart, buyNow;
    ImageView addItems, removeItems;
    int totalQuantity = 1;
    int totalPrice = 0;

    NewProductsModel newProductsModel = null;
    MaisVend maisVend = null;
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detalhes");

        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof MaisVend) {
            maisVend = (MaisVend) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detalhe_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detalhe_nome);
        description = findViewById(R.id.detalhe_desc);
        price = findViewById(R.id.detalhe_price);
        addtoCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);


        if (newProductsModel != null || maisVend != null || showAllModel != null) {
            if (newProductsModel != null) {
                Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
                name.setText(newProductsModel.getName());
                description.setText(newProductsModel.getDescription());
                price.setText(String.valueOf(newProductsModel.getPrice()));
            } else if (maisVend != null) {
                Glide.with(getApplicationContext()).load(maisVend.getImg_url()).into(detailedImg);
                name.setText(maisVend.getName());
                description.setText(maisVend.getDescriptions());
                price.setText(String.valueOf(maisVend.getPrice()));
            } else if (showAllModel != null) {
                Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
                name.setText(showAllModel.getName());
                description.setText(showAllModel.getDescription());
                price.setText(String.valueOf(showAllModel.getPrice()));
            }

            updateTotalPrice();


            addItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (totalQuantity < 10) {
                        totalQuantity++;
                        quantity.setText(String.valueOf(totalQuantity));
                        updateTotalPrice();
                    }
                }
            });

            removeItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (totalQuantity > 1) {
                        totalQuantity--;
                        quantity.setText(String.valueOf(totalQuantity));
                        updateTotalPrice();
                    }
                }
            });

            addtoCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCart();
                }
            });
        }
    }

    private void updateTotalPrice() {
        if (newProductsModel != null) {
            totalPrice = newProductsModel.getPrice() * totalQuantity;
        } else if (maisVend != null) {
            totalPrice = maisVend.getPrice() * totalQuantity;
        } else if (showAllModel != null) {
            totalPrice = showAllModel.getPrice() * totalQuantity;
        }
    }

    private void addToCart() {
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
        cartMap.put("totalQuantity", String.valueOf(totalQuantity));
        cartMap.put("totalPrice", String.valueOf(totalPrice));

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetalheActivity.this, "Adicionado no Carrinho", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
