package com.example.carpoolbuddyjustinkim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carpoolbuddyjustinkim.Alumni;
import com.example.carpoolbuddyjustinkim.Parent;
import com.example.carpoolbuddyjustinkim.Student;
import com.example.carpoolbuddyjustinkim.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AuthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    //establishment of important Edittext and Firestore properties
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailField;
    private EditText passwordField;
    private EditText nameField;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        nameField = (EditText)findViewById(R.id.nameDisplay);
        emailField = (EditText)findViewById(R.id.editTextEmail);
        passwordField = (EditText)findViewById(R.id.editTextPassword);

        // Spinner also referring to strings.xml in values - nuance is that it's referring to the types of users//

        Spinner spinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     *
     * @param v represents the clicked event (a Sign in button)
     *          will check for a valid, non-null user through signing the inputted email and password strings
     *          if successful, will activate the updateUI method, which will take the user to the UserProfileActivity page
     */
    public void signIn(View v)
    {

        System.out.println("Log in");
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        System.out.println(String.format("email:%s and password:%s", emailString, passwordString));

        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Log.d("Log In", "Successfully logged in the user");
                    Toast.makeText(AuthActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                    // when the user is within the database, the updateUI method will be called, which will take users to the UserProfileActivity class//
                }
                else
                {
                    Log.w("Sign up", "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                }
            }
        });


    }

    /**
     *
     * @param v represents a clicked event (a Sign up button)
     *          takes inputted email and password strings, as well as a user type, and signs the user up to the Firebase database
     */
    public void signUp(View v)
    {

        System.out.println("Sign Up");
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    mUser = task.getResult().getUser();
                    Spinner mySpinner = (Spinner)findViewById(R.id.spinner3);
                    String text = mySpinner.getSelectedItem().toString();
                    String nameString = nameField.getText().toString();

                    // user information needs to be inputted into the Firestore database//
                    // information specific to the type of user is also included within the importation of data//
                    if(text.equals("Student"))
                    {
                        Student studentObject = new Student(mUser.getUid(), nameString, mUser.getEmail(), "Student", 13, "2023");
                        firestore.collection("users").document("students").collection("student").document(nameString).set(studentObject);

                    }

                    if(text.equals("Teacher"))
                    {
                        Teacher teacherObject = new Teacher(mUser.getUid(), nameString , mUser.getEmail(), "Teacher",13, "2023");
                        firestore.collection("users").document("teachers").collection("teacher").document(nameString).set(teacherObject);

                    }

                    if(text.equals("Alumni"))
                    {
                        Alumni alumniObject = new Alumni(mUser.getUid(), nameString , mUser.getEmail(), "Alumni",13, "2023");
                        firestore.collection("users").document("alumni").collection("alumni member").document(nameString).set(alumniObject);

                    }
                    if(text.equals("Parent"))
                    {
                        Parent parentObject = new Parent(mUser.getUid(), nameString, mUser.getEmail(), "Parent", 13);
                        firestore.collection("users").document("parents").collection("parent").document(nameString).set(parentObject);
                    }
                    Log.d("Sign up", "Successfully signed up the user");
                    updateUI(mUser);
                    Toast.makeText(AuthActivity.this, "Successfully signed up the user!", Toast.LENGTH_SHORT).show();

                }
                else
                    {
                    Log.w("Sign up", "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                    Toast.makeText(AuthActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     * @param mUser is the current User
     *              method will verify whether the current User is null or valid
     *              if valid, the method will take the current User to the UserProfileActivity class
     */

    public void updateUI(FirebaseUser mUser)
    {
        if(mUser != null)
        {
            System.out.println("Called successfully");
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);

            // go to UserProfileActivity class
        }

        if(mUser == null)
        {
            System.out.println("Why you mad");
            Toast.makeText(AuthActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}