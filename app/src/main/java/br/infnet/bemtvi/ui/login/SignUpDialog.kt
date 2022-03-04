package br.infnet.bemtvi.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignUpDialog : LoginDialogFragmentOpen() {


    override fun confirmBtnAction(email: String, password: String) {

        with(activityViewModel) {
            val myCreateAccountListener = { task: Task<AuthResult> ->
                if (task.isSuccessful && mAuth != null) {
                    mUser = mAuth!!.currentUser
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


            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(requireActivity(), myCreateAccountListener)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDialogTitle("Crie sua conta")
        super.onViewCreated(view, savedInstanceState)
    }

}