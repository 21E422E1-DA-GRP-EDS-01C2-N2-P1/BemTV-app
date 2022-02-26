package br.infnet.bemtvi.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.bemtvi.data.LoginDataSource
import br.infnet.bemtvi.data.LoginRepository

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String
    //... other data fields that may be accessible to the UI
)
data class LoginFormState2(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
data class LoginResult2(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
class LoginViewModelFactory3 : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}