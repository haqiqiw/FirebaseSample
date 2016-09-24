package id.haqiqiw.firebasesample;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.text_name)
    TextView textName;

    DatabaseReference mRef;
    @BindView(R.id.input_task)
    EditText inputTask;
    @BindView(R.id.text_task)
    TextView textTask;

    StringBuffer sbTask = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFirebase();
    }

    private void initFirebase() {
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    textName.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRef.child("task").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    sbTask.append("key:"+dataSnapshot.getKey().toString()+
                            ",value:"+dataSnapshot.getValue().toString() + "\n");
                    textTask.setText(sbTask.toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String name = inputName.getText().toString();
        String task = inputTask.getText().toString();

        mRef.child("name").setValue(name);

        DatabaseReference ref = mRef.child("task").push();
        ref.setValue(task);
    }
}


