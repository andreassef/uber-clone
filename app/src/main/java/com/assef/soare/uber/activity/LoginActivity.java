package com.assef.soare.uber.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.assef.soare.uber.R;
import com.assef.soare.uber.config.ConfiguracaoFirebase;
import com.assef.soare.uber.helper.UsuarioFirebase;
import com.assef.soare.uber.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
    }

    public void validarLoginUsuario(View view){
        //Recuperar textos dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if(!textoEmail.isEmpty()){//verifica email
            if(!textoSenha.isEmpty()){//verifica senha
                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);
            }else{
                Toast.makeText(LoginActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
        }
    }

    public void logarUsuario(Usuario usuario){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //verificar o tipo de usuário logado
                    //"Motorista" / "Passageiro"
                    UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);
                }else {
                    String exececao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        exececao = "Usuário não está cadastrado.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exececao = "E-mail e senha não correspondem a um cadastro";
                    }catch (Exception e){
                        exececao = "Erro ao cadastrar usuário";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, exececao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
