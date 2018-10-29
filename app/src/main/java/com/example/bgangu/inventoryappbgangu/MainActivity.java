package com.example.bgangu.inventoryappbgangu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bgangu.inventoryappbgangu.data.InventoryDbHelper;
import com.example.bgangu.inventoryappbgangu.data.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity {

    InventoryDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new InventoryDbHelper(this);
        db = mDbHelper.getReadableDatabase();

        Button mAddProductButton = (Button) findViewById(R.id.add_product_button);
        mAddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProduct();
                fetchFromInventory();
            }
        });

        Button mDeleteInventoryData = (Button) findViewById(R.id.delete_inventory_data_button);
        mDeleteInventoryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInventoryData();
                fetchFromInventory();
            }
        });
    }

    public void insertProduct() {

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Google Pixel 3");
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, 75000);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, 5);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Flipkart");
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER, "9080706050");

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.v("MainActivity", "Error in adding the row" + newRowId);
        } else {
            Log.v("MainActivity", "New row added " + newRowId);
        }
    }

    public void fetchFromInventory() {

        TextView mDisplayProductInfo = (TextView) findViewById(R.id.display_product_text_view);

        String displayProductInfo = InventoryEntry.COLUMN_PRODUCT_NAME + "    "
                + InventoryEntry.COLUMN_PRODUCT_PRICE + "  "
                + InventoryEntry.COLUMN_PRODUCT_QUANTITY + "  "
                + InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME + "  "
                + InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + "\n";
        mDisplayProductInfo.setText(displayProductInfo);

        String projection[] = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
        };

        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int productIdColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
            int productQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int productSupplierNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int productSupplierPhoneNumberColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);

            while (cursor.moveToNext()) {

                int currentProductId = cursor.getInt(productIdColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentProductPrice = cursor.getInt(productPriceColumnIndex);
                int currentProductQuantity = cursor.getInt(productQuantityColumnIndex);
                String currentProductSupplierName = cursor.getString(productSupplierNameColumnIndex);
                String currentProductSupplierPhoneNumber = cursor.getString(productSupplierPhoneNumberColumnIndex);

                mDisplayProductInfo.append(currentProductName + "     "
                        + currentProductPrice + "       "
                        + currentProductQuantity + "         "
                        + currentProductSupplierName + "                "
                        + currentProductSupplierPhoneNumber + "\n");
            }
        } finally {
            cursor.close();
        }

    }

    public void deleteInventoryData() {
        db.delete(InventoryEntry.TABLE_NAME, null, null);

        Cursor cursor = db.query(InventoryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (!cursor.moveToNext()) {
            Log.v("MainActivity", "Table is empty");
        } else {
            Log.v("MainActivity", "Not all the table contents were removed");
        }

    }

}

