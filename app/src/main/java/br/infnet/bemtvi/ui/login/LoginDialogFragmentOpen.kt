package br.infnet.bemtvi.ui.login

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import br.infnet.bemtvi.R
import br.infnet.bemtvi.ui.MainActivityViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginDialogFragmentOpen.newInstance] factory method to
 * create an instance of this fragment.
 */
open class LoginDialogFragmentOpen : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val activityViewModel: MainActivityViewModel by activityViewModels()
    lateinit var emailField: EditText
    lateinit var passwordField: EditText
    lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_dialog_fragment_open, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailField=view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        passwordField=view.findViewById<EditText>(R.id.editTextTextPassword)
        confirmButton=view.findViewById<Button>(R.id.confirm_dialog_btn)
        confirmButton.setOnClickListener {
            val emailtxt = emailField.text.toString()
            val passwordtxt = passwordField.text.toString()
            confirmBtnAction(emailtxt,passwordtxt)
            dismiss()
        }
    }
    open fun confirmBtnAction(email:String,password:String){
        //implementada por outras classes
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginDialogFragmentOpen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginDialogFragmentOpen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}