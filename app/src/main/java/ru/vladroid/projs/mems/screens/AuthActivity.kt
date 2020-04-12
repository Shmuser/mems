package ru.vladroid.projs.mems.screens

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.network.AuthInfo
import ru.vladroid.projs.mems.presenters.AuthPresenter
import ru.vladroid.projs.mems.presenters.AuthPresenterImpl
import ru.vladroid.projs.mems.utils.AppConstants
import ru.vladroid.projs.mems.views.AuthView
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

class AuthActivity : AppCompatActivity(), AuthView {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var passwordBox: TextFieldBoxes
    private lateinit var loginBox: TextFieldBoxes
    private lateinit var passwordEditText: ExtendedEditText
    private lateinit var loginEditText: ExtendedEditText
    private lateinit var helperText: String
    private lateinit var emptyFieldErrorText: String
    private lateinit var progressBar: ProgressBar
    private lateinit var authButton: Button
    private lateinit var authPresenter: AuthPresenter
    private lateinit var authButtonText: CharSequence
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemeDarkStatusBar)
        setContentView(R.layout.activity_auth)

        authPresenter = AuthPresenterImpl(this)
        authPresenter.attachView(this)
        constraintLayout = findViewById(R.id.constraint_layout)
        passwordBox = findViewById(R.id.password_field_boxes)
        loginBox = findViewById(R.id.login_field_boxes)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginEditText = findViewById(R.id.login_edit_text)
        progressBar = findViewById(R.id.progress_bar)
        helperText = resources.getString(R.string.password_helper_text, AppConstants.passwordLength)
        emptyFieldErrorText = resources.getString(R.string.empty_field_error)
        authButton = findViewById(R.id.auth_button)

        authButton.setOnClickListener {
            if (!progressBar.isVisible)
                authButtonClick()
        }

        configurePasswordInput()
    }

    override fun onDestroy() {
        authPresenter.detachView()
        super.onDestroy()
    }

    private fun configurePasswordInput() {
        passwordBox.endIconImageButton.setColorFilter(
            ContextCompat.getColor(
                this,
                R.color.colorAccent
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
        passwordBox.endIconImageButton.alpha = 1.0f

        passwordBox.endIconImageButton.setOnClickListener {
            (it as ImageView).let {
                if (it.tag == "hide") {
                    it.setImageResource(R.drawable.ic_visibility_24dp)
                    passwordEditText.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    it.tag = "show"
                } else {
                    it.setImageResource(R.drawable.ic_visibility_off_24dp)
                    passwordEditText.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    it.tag = "hide"
                }
                passwordEditText.setSelection(passwordEditText.text.length)
            }
        }

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length != AppConstants.passwordLength) {
                    setPasswordHelperText(helperText)
                } else {
                    setPasswordHelperText(" ")
                }
            }
        })
        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && passwordEditText.text.length != 8) {
                if (!passwordBox.isOnError)
                    setPasswordHelperText(helperText)
            } else {
                setPasswordHelperText(" ")
            }
        }
    }

    private fun isInputCorrect(): Boolean {
        var isCorrect = true
        if (passwordEditText.text.isEmpty()) {
            isCorrect = false
            passwordBox.setError(emptyFieldErrorText, false)
        }
        if (loginEditText.text.isEmpty()) {
            isCorrect = false
            loginBox.setError(emptyFieldErrorText, false)
        }

        return isCorrect
    }

    private fun authButtonClick() {
        if (!isInputCorrect())
            return
        val authInfo = AuthInfo(loginEditText.text.toString(), passwordEditText.text.toString())
        authPresenter.onAuthButtonClick(authInfo)
    }

    private fun setPasswordHelperText(text: String) {
        if (passwordBox.helperText != text)
            passwordBox.helperText = text
    }

    private fun getSnackbar(view: View): Snackbar {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, R.string.auth_error_message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.colorError))
                .setTextColor(ContextCompat.getColor(this, R.color.colorText))
        }
        return snackbar!!
    }

    override fun onWrongAuthInfoError() {
        getSnackbar(constraintLayout).show()
    }

    override fun onAuthSuccess() {
        val memesIntent = Intent(this, MemesMainActivity::class.java)
        startActivity(memesIntent)
    }

    override fun showAuthLoading() {
        authButtonText = authButton.text
        authButton.text = ""
        progressBar.visibility = View.VISIBLE
    }

    override fun hideAuthLoading() {
        authButton.text = authButtonText
        progressBar.visibility = View.INVISIBLE
    }
}