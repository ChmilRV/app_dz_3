package edu.itstep.app_dz_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvEmployees;
    TextView tvFirstName;
    TextView tvLastName;
    TextView tvDateOfBirth;
    ImageView imFoto;


    private ArrayList<Employee> employees = new ArrayList<>();

    private int nrmlColor = Color.rgb(0xED, 0xE2, 0x75);
    private int slctColor = Color.rgb(0xE2, 0xA7, 0x6F);
    private int curItem = -1;
    private View curView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        createEmployees();
        setEmployeeListeners();
        createEmployeeAdapter();

//        Bitmap bmMail = getImage("mail.png");
//        Bitmap bmFemail = getImage("femail.png");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_add:
                Toast.makeText(this,
                        "menu add",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_edit:
                Toast.makeText(this,
                        "menu edit",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_delete:
                Toast.makeText(this,
                        "menu delete",
                        Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        lvEmployees = (ListView) this.findViewById(R.id.lvEmployees);


    }

    private void setEmployeeListeners() {
        this.lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                if (MainActivity.this.curItem != -1) {
                    MainActivity.this.curView.setBackgroundColor(MainActivity.this.nrmlColor);
                }
                MainActivity.this.curItem = position;
                MainActivity.this.curView = view;
                MainActivity.this.curView.setBackgroundColor(MainActivity.this.slctColor);
            }
        });
    }

    private void createEmployeeAdapter() {
        ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(this,
                R.layout.employee_item,
                R.id.tvFirstName,
                employees) {
            @Override
            public View getView(int position, View convertview, ViewGroup parent) {
                View view = super.getView(position, convertview, parent);
                Employee employee = this.getItem(position);


                imFoto = view.findViewById(R.id.imFoto);

                if (employee.isGender())
                    imFoto.setImageBitmap(getImage("mail.png"));
                else
                    imFoto.setImageBitmap(getImage("femail.png"));

                tvFirstName = view.findViewById(R.id.tvFirstName);
                tvFirstName.setText(employee.getFirstName());
                tvLastName = view.findViewById(R.id.tvLastName);
                tvLastName.setText(employee.getLastname());
                tvDateOfBirth = view.findViewById(R.id.tvDateOfBirth);
                tvDateOfBirth.setText(employee.getBirthDayString());


                if (position == MainActivity.this.curItem) {
                    view.setBackgroundColor(MainActivity.this.slctColor);
                    MainActivity.this.curView = view;
                } else {
                    view.setBackgroundColor(MainActivity.this.nrmlColor);
                }
                return view;
            }
        };
        this.lvEmployees.setAdapter(adapter);
    }

    private Bitmap getImage(String s) {
        Bitmap bmp = null;
        try {
            AssetManager AM = this.getAssets();
            InputStream IS = AM.open(s);
            bmp = BitmapFactory.decodeStream(IS);
            IS.close();
        } catch (IOException ioe) {
        }
        return bmp;
    }

    private void createEmployees() {
        for (int i = 0; i < 20; i++) {
            this.employees.add(
                    new Employee("Имя_" + (i + 1),
                            "Фамилия_" + (i + 1),
                            (Math.round(Math.random() * 100) < 50) ? true : false,
                            Employee.makeCalendar(
                                    (int) Math.round(Math.random() * 30),
                                    (int) Math.round(Math.random() * 12),
                                    (int) Math.round(Math.random() * 100 + 1900))));
        }
    }


}