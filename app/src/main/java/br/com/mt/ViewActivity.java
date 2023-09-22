package br.com.mt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import br.com.mt.adapters.ViewAllAdapter;
import br.com.mt.models.ViewAllModel;

public class ViewActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);

        Log.d("ViewActivity", "onCreate: Adapter set up");

        if (type != null && (type.equalsIgnoreCase("utensilios") || type.equalsIgnoreCase("solventes"))) {
            Query query = firestore.collection("AllMais").whereEqualTo("type", type);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    int startPos = viewAllModelList.size();
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                    }
                    int itemCount = viewAllModelList.size() - startPos;
                    viewAllAdapter.notifyItemRangeInserted(startPos, itemCount);
                    Log.d("ViewActivity", "Firestore data loaded successfully for '" + type + "'");
                } else {
                    Log.e("ViewActivity", "Error getting Firestore data for '" + type + "': " + task.getException());
                }
            });
        }
    }
}
