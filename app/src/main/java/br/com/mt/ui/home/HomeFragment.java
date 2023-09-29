package br.com.mt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.mt.R;
import br.com.mt.adapters.HomeAdapter;
import br.com.mt.adapters.MaisAdapters;
import br.com.mt.adapters.PromoAdapter;
import br.com.mt.models.HomeCategory;
import br.com.mt.models.MaisVend;
import br.com.mt.models.PromoModel;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView maisVend,homeCatRec,promoRec;
    FirebaseFirestore db;

    //mais vendidos
    List<MaisVend> maisVendList;
    MaisAdapters maisAdapters;

    //categoria
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    //Promoção
    List<PromoModel> promoModelList;
    PromoAdapter promoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        maisVend = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.pop_rec_outros);
        promoRec = root.findViewById(R.id.pop_rec_promo);
        scrollView = root.findViewById(R.id.scroll);
        progressBar = root.findViewById(R.id.progressbar1);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //mais vendidos
        maisVend.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        maisVendList = new ArrayList<>();
        maisAdapters = new MaisAdapters(getActivity(),maisVendList);
        maisVend.setAdapter(maisAdapters);

        db.collection("MaisVendidos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                MaisVend maisVend = document.toObject(MaisVend.class);
                                maisVendList.add(maisVend);
                                maisAdapters.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //categoria
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Promoção
        promoRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        promoModelList = new ArrayList<>();
        promoAdapter = new PromoAdapter(getActivity(),promoModelList);
        promoRec.setAdapter(promoAdapter);

        db.collection("EmPromocao")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PromoModel promoModel = document.toObject(PromoModel.class);
                                promoModelList.add(promoModel);
                                promoAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}
