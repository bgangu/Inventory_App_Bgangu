package com.example.bgangu.inventoryappbgangu.data;

import android.provider.BaseColumns;

public class InventoryContract {

    public InventoryContract() {
    }

    public static final class InventoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "inventory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "Product_Name";
        public static final String COLUMN_PRODUCT_PRICE = "Price";
        public static final String COLUMN_PRODUCT_QUANTITY = "Quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER_NAME = "Supplier_Name";
        public static final String COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER = "Supplier_Phone_Number";

    }

}