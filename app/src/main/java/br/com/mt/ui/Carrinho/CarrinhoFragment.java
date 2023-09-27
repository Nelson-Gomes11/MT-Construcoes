package br.com.mt.ui.Carrinho;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.mt.R;
import br.com.mt.adapters.MyCartAdapter;
import br.com.mt.models.MyCartModel;

public class CarrinhoFragment extends Fragment {

    int overAllTotalAmount;
    TextView overAllAmount;
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        overAllAmount = view.findViewById(R.id.textView3);
        recyclerView = view.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        FirebaseUser user = auth.getCurrentUser();
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                                cartModelList.add(myCartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        return view;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            overAllAmount.setText("Valor Total : R$ " + totalBill);
        }
    };
}
