package br.com.mt.ui.Carrinho;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.mt.R;
import br.com.mt.adapters.MyCartAdapter;
import br.com.mt.models.MyCartModel;

public class CarrinhoFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;

    public CarrinhoFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_carrinho, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        return root;
    }
}