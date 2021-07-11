package com.ifsp.pdm.emanoela.listadetarefas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.ifsp.pdm.emanoela.listadetarefas.AutenticacaoFirebase
import com.ifsp.pdm.emanoela.listadetarefas.R
import com.ifsp.pdm.emanoela.listadetarefas.databinding.ActivityAutenticacaoBinding

class AutenticacaoActivity : AppCompatActivity() {
    private lateinit var activityAutenticacaoBinding: ActivityAutenticacaoBinding
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAutenticacaoBinding = ActivityAutenticacaoBinding.inflate(layoutInflater)
        setContentView(activityAutenticacaoBinding.root)

        AutenticacaoFirebase.googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        AutenticacaoFirebase.googleSignInClient =
            GoogleSignIn.getClient(this, AutenticacaoFirebase.googleSignInOptions!!)

        AutenticacaoFirebase.googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

        if (AutenticacaoFirebase.googleSignInAccount != null) {
            entrar()
        }

        activityAutenticacaoBinding.entrarGoogleBt.setOnClickListener {
            val googleSignInIntent = AutenticacaoFirebase.googleSignInClient?.signInIntent
            googleSignInLauncher.launch(googleSignInIntent)
        }

        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    AutenticacaoFirebase.googleSignInAccount =
                        task.getResult(ApiException::class.java)

                    val credential: AuthCredential = GoogleAuthProvider.getCredential(
                        AutenticacaoFirebase.googleSignInAccount?.idToken,
                        null
                    )

                    AutenticacaoFirebase.firebaseAuth.signInWithCredential(credential)
                        .addOnSuccessListener { entrar() }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Problema com autenticação Google",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Problema com autenticação Google", Toast.LENGTH_SHORT)
                        .show()

                }
            }
    }

    fun onClick(view: View) {
        when (view) {
            activityAutenticacaoBinding.cadastrarBt -> {
                startActivity(Intent(this, CadastrarActivity::class.java))
            }

            activityAutenticacaoBinding.entrarBt -> {
                val email: String = activityAutenticacaoBinding.emailEt.text.toString()
                val senha: String = activityAutenticacaoBinding.senhaEt.text.toString()
                AutenticacaoFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha)
                    .addOnSuccessListener {
                        entrar()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

            activityAutenticacaoBinding.recuperarSenhaBt -> {
                startActivity(Intent(this, RecuperarSenhaActivity::class.java))

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (AutenticacaoFirebase.firebaseAuth.currentUser != null) {
            Toast.makeText(this, "Usuário já logado", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun entrar() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}