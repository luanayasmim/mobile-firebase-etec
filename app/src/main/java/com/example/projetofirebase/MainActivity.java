package com.example.projetofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference minhaReferencia = FirebaseDatabase.getInstance().getReference(  "usuários");
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference produtos = referencia.child("produtos");

    //Valores da tela
    private EditText nome;
    private  EditText preco;
    private Button cadastrar;
    private Button visualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference produtos = minhaReferencia.child("produtos");
        usuario.signOut();

        //Valores da tela
        nome = findViewById(R.id.editNome);
        preco = findViewById(R.id.editPreco);
        cadastrar = findViewById(R.id.buttonAdicionar);
        visualizar = findViewById(R.id.buttonVisualizar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produtos p = new Produtos();

                DatabaseReference produto = referencia.child("produtos");
                p.setNome(String.valueOf(nome.getText()));
                p.setPreco(Double.parseDouble(String.valueOf(preco.getText())));
                    produto.push().setValue(p);

                Toast.makeText(getApplicationContext(), "O produto" + nome.getText()+" foi cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            }
        });

        visualizar.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, visualizarProdutos.class);
                startActivity(intent);
            }
        });

        usuario.createUserWithEmailAndPassword("mrlobo.guara@gmail.com","a12344567").addOnCompleteListener(
                MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "login efetuado com sucesso!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"ERRO AO FAZER LOGIN",Toast.LENGTH_LONG).show();
                        }
                        if(usuario.getCurrentUser() != null){
                            Toast.makeText(getApplicationContext(),"usuário logado", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "NÂO HÁ UM USUÁRIO LOGADO", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        //Tela de login
        usuario.signInWithEmailAndPassword("mrlobo.guara@gmail.com", "a1234567").addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "ERRO AO FAZER LOGIN!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


        Produtos p = new Produtos();
        p.setNome("coca-cola");
        p.setPreco(12.50);

        produtos.child("001").setValue(p);


        produtos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}