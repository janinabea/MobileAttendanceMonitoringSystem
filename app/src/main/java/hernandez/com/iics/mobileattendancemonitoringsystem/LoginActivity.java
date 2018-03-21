package hernandez.com.iics.mobileattendancemonitoringsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtUsername;
    EditText edtPassword;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        edtUsername = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);

        dbHelper = new DatabaseHelper(LoginActivity.this);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = dbHelper.checkUserExist(edtUsername.getText().toString(), edtPassword.getText().toString());
                if (isExist){
                    Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                    startActivity(intent);
                }else{
                    edtPassword.setText(null);
                    Toast.makeText(LoginActivity.this, "Login failed. Username or password invalid", Toast.LENGTH_SHORT).show();
                    
                }
            }
        });
    }
}

