package tibet.paljor.assignment2_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Nova on 2017-11-12.
 */

public class ProductDBHelpher extends SQLiteOpenHelper {
    private static  final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "Products.db";
    public static String TABLE_NAME = "products";
    public  static  String COLUMN_ID = "_id";
    public  static  String COLUMN_PRODUCTNAME = "productname";
    public  static  String COLUMN_PRODUCTDESC = "productdescription";
    public  static  String COLUMN_PRODUCTPRICE = "productprice";

    public ProductDBHelpher(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT, " +
                COLUMN_PRODUCTDESC + " TEXT, " +
                COLUMN_PRODUCTPRICE + " REAL " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public void addProduct(Product product){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCTNAME, product.getName());
        contentValues.put(COLUMN_PRODUCTDESC, product.getDescription());
        contentValues.put(COLUMN_PRODUCTPRICE, product.getPrice());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void deleteProduct(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + name + "\";");
    }

    public String DBtoString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex("productname"))!= null){
                dbString += cursor.getString(cursor.getColumnIndex("productname"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }
    public ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE 1";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            String productId = cursor.getString(0);
            String prodcutName = cursor.getString(1);
            String productDesc = cursor.getString(2);
            float productPrice = cursor.getFloat(3);

            Product x = new Product(prodcutName, productDesc, productPrice);
            products.add(x);
            cursor.moveToNext();
        }

        return products;
    }
}
