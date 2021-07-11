package com.ifsp.pdm.emanoela.listadetarefas

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

object AutenticacaoFirebase {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var googleSignInOptions: GoogleSignInOptions? = null
    var googleSignInClient: GoogleSignInClient? = null
    var googleSignInAccount: GoogleSignInAccount? = null
}