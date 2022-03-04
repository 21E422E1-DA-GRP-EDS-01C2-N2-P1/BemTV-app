package br.infnet.bemtvi.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.bemtvi.R
import br.infnet.bemtvi.data.LoginDataSource
import br.infnet.bemtvi.data.LoginRepository
import br.infnet.bemtvi.data.Result
import br.infnet.bemtvi.services.SearchImageService
import br.infnet.bemtvi.services.SearchImageServiceListener
import br.infnet.bemtvi.services.SearchedImageURL

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String
    //... other data fields that may be accessible to the UI
)
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginDialogViewModel::class.java)) {
            return LoginDialogViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LoginDialogViewModel(private val loginRepository: LoginRepository) : ViewModel(),
    SearchImageServiceListener {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val searchService = SearchImageService()

    init {
        searchService.setListener(this)
    }
    val loginImage = MutableLiveData<String>()

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    override fun whenGetImageFinished(imageURL: SearchedImageURL?) {
        println("IMAGEM CHEGOU ${imageURL?.big}")
        loginImage.postValue(imageURL?.big)
    }
    fun searchImage(title:String){
        searchService.getImage(title)
    }

    override fun whenHttpError(erro: String) {
        // TODO("Not yet implemented")
    }
}