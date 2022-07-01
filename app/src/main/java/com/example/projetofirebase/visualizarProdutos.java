package com.example.projetofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class visualizarProdutos extends AppCompatActivity {

    private DatabaseReference referencia= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference meusProdutos= referencia.child("produtos");
    private ListView listaProdutos;
    private ArrayList<String> prod;


    
    Query nomesProdutosQuery= meusProdutos.orderByChild("nome");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_produtos);
    
        prod= new ArrayList<>();


        nomesProdutosQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dados : snapshot.getChildren()) {
                    Produtos p = snapshot.getValue(Produtos.class);
                    Produtos pro = dados.getValue(Produtos.class);
                    prod.add(pro.getNome());
                }
                listaProdutos = findViewById(R.id.listaProdutos);
                ArrayAdapter<String> adaptador = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, prod);
                listaProdutos.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro ao listar produtos" + error, Toast.LENGTH_SHORT).show();
            }
        });


    }
}