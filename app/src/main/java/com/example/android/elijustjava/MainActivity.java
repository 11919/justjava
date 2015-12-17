package com.example.android.elijustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    private String String_priceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        if (quantity == 100) {
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField = (EditText)findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream= whippedCreamCheckBox.isChecked();


        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate= ChocolateCheckBox.isChecked();

        CheckBox cinnamonCheckbox = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hascinnamon= cinnamonCheckbox.isChecked();


        CheckBox CaramelCheckBox = (CheckBox) findViewById(R.id.caramel_checkbox);
        boolean hasCaramel= CaramelCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCaramel, hascinnamon);

        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate, hasCaramel, hascinnamon);

        // Use an intent to launch an email app.
// Send the order summary in the email body.
        Uri emailuri= Uri.parse("mailto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO,emailuri);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }



    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedcream, boolean addChocolate, boolean addcinnamon, boolean addcaramel) {
        int basePrice = 5;

        if (addWhippedcream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        if (addcinnamon) {
            basePrice = basePrice + 1;
        }

        if (addcaramel) {
            basePrice = basePrice + 3;
        }

        return quantity * basePrice;

    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addcinnamon, boolean addcaramel) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += getString(R.string.order_summary_cinnamon, addcinnamon);
        priceMessage += getString(R.string.order_summary_caramel, addcaramel);
        priceMessage += "\n " + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n " +getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);

    }

}
