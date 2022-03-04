package br.infnet.bemtvi.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignInDialog:LoginDialogFragmentOpen() {
    override fun confirmBtnAction(email: String, password: String) {

        with(activityViewModel) {
            val mySignInAccountListener = { task: Task<AuthResult> ->
                if (task.isSuccessful && mAuth != null) {
                    mUser =  mAuth!!.currentUser
                    Toast.makeText(requireActivity(),"bem vindo de volta",
                        Toast.LENGTH_LONG+4242).show()
                    activityViewModel.isLoggedIn.postValue(true)
                    dismiss()
                } else {
                    Log.d("ERRO LOGIN/CREATE", "${task.exception!!.message}")
                    Toast.makeText(
                        requireContext(), "Falha na Autenticação",
                        Toast.LENGTH_SHORT
                    ).show()
                    mUser = null
                }
                //updateUI()
            }
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(requireActivity(),mySignInAccountListener)
            //teste
            activityViewModel.isLoggedIn.postValue(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDialogTitle("Entre na sua conta")
        super.onViewCreated(view, savedInstanceState)
    }
}