package tibet.paljor.assignment2_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    EditText newName;
    EditText newPrice;
    EditText newDesc;
    ProductDBHelpher dbHelpher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        newName = (EditText) findViewById(R.id.name);
        newPrice = (EditText)findViewById(R.id.price);
        newDesc = (EditText)findViewById(R.id.desciption);
        dbHelpher = new ProductDBHelpher(this, null, null, 1);
    }

    public void addProduct(View view){
        String name = newName.getText().toString();
        String price = newPrice.getText().toString();
        String description = newDesc.getText().toString();
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

        dbHelpher.addProduct(new Product(name, description, Integer.parseInt(price)));
        setResult(1);
        finish();
    }

    public void canceled(View view){
        setResult(0);
        finish();
    }
}
