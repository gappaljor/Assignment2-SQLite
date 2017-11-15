package tibet.paljor.assignment2_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;

public class BrowseProducts extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    private static List<Product> productList;
    private static int pointer = 0;
    private  static ListIterator<Product> iterator;
    private static int producIndex = 0;
    private static boolean flag = true;
    TextView productName;
    TextView cad;
    TextView bit;
    TextView description;
    ProductDBHelpher dbHelpher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_products);

        productName = (TextView)findViewById(R.id.productName);
        cad = (TextView)findViewById(R.id.cadValue);
        description = (TextView)findViewById(R.id.description);
        bit = (TextView)findViewById(R.id.bitValue);
        dbHelpher = new ProductDBHelpher(this, null,null, 1);
        productList = dbHelpher.getProducts();
        iterator = productList.listIterator();

        try {
            showNext(this.findViewById(android.R.id.content));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected void onStart(){
        super.onStart();
        productList = dbHelpher.getProducts();

    }


    public void addProduct(View view){
        Intent intent = new Intent(this, AddProduct.class);
        startActivityForResult(intent, REQUESTCODE);
    }

    public void onActivityResult(int reqCode, int resCode, Intent intent){
        super.onActivityResult(reqCode,resCode,intent);
        if(reqCode == REQUESTCODE && resCode == 1){
            iterator = productList.listIterator();
        }
    }

    public void showNext(View view) throws MalformedURLException {
        Product next;
        if (!flag){
            next = iterator.next();
            flag = true;
        }

        if(iterator.hasNext()){
            next = iterator.next();
            productName.setText(next.getName());
            cad.setText(Double.toString(next.getPrice()));
            description.setText(next.getDescription());
            bit.setText(convertToBitCoin(next.getPrice()));
        }
        else{
            Toast.makeText(this,"Click Prev!", Toast.LENGTH_SHORT).show();
        }


    }
    public void showPrev(View view) throws MalformedURLException {
        Product prev;
        if (flag){
            prev = iterator.previous();
            flag = false;
        }
        else{

        }
        if(iterator.hasPrevious()){
            prev = iterator.previous();
            productName.setText(prev.getName());
            cad.setText(Double.toString(prev.getPrice()));
            description.setText(prev.getDescription());
            bit.setText(convertToBitCoin(prev.getPrice()));
        }
        else{
            Toast.makeText(this, "Click Next!", Toast.LENGTH_SHORT).show();
        }
    }

    public String convertToBitCoin(double cad) throws MalformedURLException {
        String line = "";
        String allLines="";
        try {
            URL url = new URL("https://blockchain.info/currency=CAD&value="+cad);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            int result = conn.getResponseCode();

            if (result == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) != null) {
                    allLines += line + "\n";
                }
                is.close();
                conn.disconnect();
            }
            else
                Toast.makeText(this, "no bitCoin", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "BitCoin="+line, Toast.LENGTH_SHORT).show();
        return allLines;

        //return String.valueOf(cad);
    }
}
