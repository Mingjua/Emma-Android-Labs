package algonquin.cst2335.emmasandroidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import algonquin.cst2335.emmasandroidlabs.databinding.ActivityMainBinding;

/**
 * @author Mingjuan Hua
 * @version  1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This holds the text at the centre of the screen.
     */
    private TextView tv = null;

    /**
     * This holds the text that the user input password.
     */
    private EditText et = null;

    /**
     * This will check if the user password has all the necessary requirements when the user click it.
     */
    private Button btn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
//        setContentView(R.layout.activity_main);
//        tv = findViewById(R.id.textView);
//        et = findViewById(R.id.editTextPassword);
//        btn = findViewById(R.id.button);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater());
        //loads an XML file on the page
        setContentView(  binding.getRoot()  );

        tv = binding.textView;
        btn = binding.button;
        et = binding.editTextPassword;


        btn.setOnClickListener(clk->{
            String password = et.getText().toString();
            boolean rs = checkPasswordComplexity (password);

            if (rs){
                tv.setText("Your password meets the requirment! ");
            } else {
                tv.setText("You shall not pass ");
            }

        });
    }

    /**
     * This function is to check if the String pw has all the necessary requirements.
     * Through a for loop to literate over each character in the String and use the Character
     * class' static functions isDigit( char c ), isUpperCase(char c), isLowerCase(char c)
     * to check if a character matches any of those conditions.
     *
     * @param pw The String object that we are checking
     * @return  Return true if the password is complex enough, else return false
     */
    public boolean checkPasswordComplexity (String pw) {
        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecial = false;


        for ( int i = 0; i< pw.length(); i++){
            char c = pw.charAt(i);
            if ( Character.isDigit(c)){
                foundNumber = true;
            } else if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if(Character.isLowerCase(c)){
                foundLowerCase = true;
            } else if(isSpecialCharacter(c)){
                foundSpecial = true;
            }
        }

        if (!foundUpperCase){
            Toast.makeText(getApplicationContext(), "Missing an upper case letter",Toast.LENGTH_LONG ).show();
            return false;
        } else if ( !foundLowerCase) {
            Toast.makeText(getApplicationContext(), "Missing a lower case letter",Toast.LENGTH_LONG ).show();
            return false;
        } else if ( !foundNumber) {
            Toast.makeText(getApplicationContext(), "Missing a number",Toast.LENGTH_LONG ).show();
            return false;
        } else if ( !foundSpecial) {
            Toast.makeText(getApplicationContext(), "Missing a special letter ( #, $, %, ^, &, *, !, @, or ?)",Toast.LENGTH_LONG ).show();
            return false;
        } else {
            return true;
        }

    }

    /**
     * Checks if the given character is a special character.
     *
     * @param c the character to check
     * @return Return true if has a special character, false otherwise
     */
    private boolean isSpecialCharacter(char c){
        switch (c){
            case '#': case '$': case '%': case '^': case '&':
            case '*': case '!': case '@': case '?':
                return true;
            default:
                return false;
        }

    }

}