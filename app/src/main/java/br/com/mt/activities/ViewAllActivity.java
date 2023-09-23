package br.com.mt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.mt.R;
import br.com.mt.adapters.ViewAllAdapter;
import br.com.mt.models.ViewAllModel;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
         recyclerView = findViewById(R.id.view_all_rec);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

         viewAllModelList = new ArrayList<>();
         viewAllAdapter = new ViewAllAdapter(this,viewAllModelList);
         recyclerView.setAdapter(viewAllAdapter);

         if (type != null && type.equalsIgnoreCase("ferro")) {
             firestore.collection("AllProd").whereEqualTo("type", "ferro").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {

                     for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                         ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                         viewAllModelList.add(viewAllModel);
                         viewAllAdapter.notifyDataSetChanged();
                     }

                 }
             });

         }

             if (type != null && type.equalsIgnoreCase("tigre")){
                 firestore.collection("AllProd").whereEqualTo("type", "tigre").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {

                         for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                             ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                             viewAllModelList.add(viewAllModel);
                             viewAllAdapter.notifyDataSetChanged();
                         }

                     }
                 });
         }
    }
}