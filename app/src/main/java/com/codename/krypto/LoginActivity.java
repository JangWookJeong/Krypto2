package com.codename.krypto;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    public static final String KRYPTO_LOG = "KRYPTO_LOG:";
    public static final int UPDATE_JOIN_MEMBERCOUNT = 101;////////////회원가입할때 회원수 카운트
    public static final int UPDATE_MEMBER_LOGINCOUNT = 102;////////////로그인할때 회원수 카운트
    public static final int UPDATE_TOTAL_MEMBERCOUNT = 103;////////////로그인할때 회원수 카운트
    SignInButton googleSignButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;
    private Button LoginActivity_Login_Try;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText LoginActivity_Login,LoginActivity_Password;
    private FirebaseDatabase database;
    private int count = 0;
    ProgressDialog LoginDialog;
    FirebaseUser user;
    KryptoDAO kryptoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestPermission();

        CreateGoogleComponent();
        googleSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        LoginActivity_Login_Try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*회원가입페이지만들고 createUser 회원가입처리해야함 */
                createUser(LoginActivity_Login.getText().toString(),LoginActivity_Password.getText().toString());
            }
        });

    }///////////onCreate

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
    }///////////////requestPermission

    private void CreateGoogleComponent() {

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        database = FirebaseDatabase.getInstance();
        googleSignButton = findViewById(R.id.Google_login);
        LoginActivity_Login = findViewById(R.id.LoginActivity_Login);
        LoginActivity_Password = findViewById(R.id.LoginActivity_Password);
        LoginActivity_Login_Try = findViewById(R.id.LoginActivity_Login_Try);
        LoginDialog = new ProgressDialog(LoginActivity.this);
        LoginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        kryptoDAO = new KryptoDAO();


        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    startActivity(new Intent(getApplicationContext(),MainMyGroupHome.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "로그아웃이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }/////////////////////////createGoogleComponent


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {////////////////////구글회원인증후 데이터베이스에 저장
        Log.d(KRYPTO_LOG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(KRYPTO_LOG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           // database.getReference().child("관리자").child("회원관리").child("회원정보").removeValue();
                            KryptoDTO dto = new KryptoDTO();
                            dto.setUsername(" ");
                            dto.setBirthDay(" ");
                            dto.setProfile_Url(" ");
                            dto.setNickName(" ");
                           /* kryptoDAO.MemberListCheck(user.getUid(),dto);*/
                            LoginDialog.show();
                            ///////////////회원수 초기화를 위한 메서드
                            startActivity(new Intent(getApplicationContext(),MainMyGroupHome.class));
                            LoginDialog.dismiss();
                            finish();

                        } else {
                            Log.w(KRYPTO_LOG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }////////////firebaseAuthWithGoogle

    @Override
    public void onStart() {
        super.onStart();
        try {
            mAuth.addAuthStateListener(mAuthListener);
            FirebaseUser currentUser = mAuth.getCurrentUser();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {////////////////구글 유저를 확인하고 확인여부 전송
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }///////OnActivityResult

    private void createUser(final String email, final String password){////////////////유저가 이메일로 로그인할때 데이터베이스에 회원가입시킨다
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            goLogin(email,password);
                        }else{

                        }
                    }
                });
    }////////////////////createUser;

    private void goLogin(String email,String password){////////유저가 이메일로로그인할때 회원여부점검 하고 로그인시작
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
