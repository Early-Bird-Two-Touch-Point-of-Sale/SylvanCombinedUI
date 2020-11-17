package com.example.sylvancombinedui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    //String Toppings;
    final int MENU_RESULT = 77;
    final String TAG = "MenuActivity";
    double foodPrice = 0.0;
    final DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //region BURGER_BUTTONS
        final Button hamburgerButton = findViewById(R.id.hamburgerButton);
        final Button cheeseburgerButton = findViewById(R.id.cheeseburgerButton);
        final Button bigfootBurgerButton = findViewById(R.id.bigfootBurgerButton);
        final Button baconCheeseBurgerButton = findViewById(R.id.baconCheeseBurgerButton);
        final Button doubleHamburgerButton = findViewById(R.id.doubleHamburgerButton);
        final Button doubleCheeseburgerButton = findViewById(R.id.doubleCheeseburgerButton);
        final Button doubleBaconCheeseBurgerButton = findViewById(R.id.doubleBaconCheeseBurgerButton);
        final Button bbqBaconCheeseBurgerButton = findViewById(R.id.bbqBaconCheeseBurgerButton);
        final Button chickenBurgerButton = findViewById(R.id.chickenBurgerButton);
        final Button fishBurgerButton = findViewById(R.id.fishBurgerButton);
        final Button veggieBurgerButton = findViewById(R.id.veggieBurgerButton);
        //endregion

        //region HOT_DOG_BUTTONS
        final Button beefDogButton = findViewById(R.id.allBeefDogButton);
        final Button polishDogButton = findViewById(R.id.polishDogButton);
        //endregion

        //region CHILI_BUTTONS
        final Button chiliBowlButton = findViewById(R.id.chiliBowlButton);
        final Button chiliFryButton = findViewById(R.id.chiliFryButton);
        final Button chiliDogButton = findViewById(R.id.chiliDogButton);
        final Button chiliBurgerButton = findViewById(R.id.chiliBurgerButton);
        //endregion

        //region BURGER_CLICKLISTENERS
        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(hamburgerButton.getText());
            }
        });

        cheeseburgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(cheeseburgerButton.getText());
            }
        });

        bigfootBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(bigfootBurgerButton.getText());
            }
        });

        baconCheeseBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(baconCheeseBurgerButton.getText());
            }
        });

        doubleHamburgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(doubleHamburgerButton.getText());
            }
        });

        doubleCheeseburgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(doubleCheeseburgerButton.getText());
            }
        });

        doubleBaconCheeseBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(doubleBaconCheeseBurgerButton.getText());
            }
        });

        bbqBaconCheeseBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(bbqBaconCheeseBurgerButton.getText());
            }
        });

        chickenBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(chickenBurgerButton.getText());
            }
        });

        fishBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(fishBurgerButton.getText());
            }
        });

        veggieBurgerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBurgerDialog(veggieBurgerButton.getText());
            }
        });
        //endregion

        //region HOT_DOG_CLICKLISTENERS
        beefDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHotDogDialog(beefDogButton.getText());
            }
        });

        polishDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHotDogDialog(polishDogButton.getText());
            }
        });
        //endregion

        //region CHILI_CLICKLISTENERS
        chiliBowlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChiliDialog(chiliBowlButton.getText());
            }
        });

        chiliFryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChiliDialog(chiliFryButton.getText());
            }
        });

        chiliDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChiliDialog(chiliDogButton.getText());
            }
        });

        chiliBurgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChiliDialog(chiliBurgerButton.getText());
            }
        });
        //endregion
    }

    public void openBurgerDialog(final CharSequence buttonTitle) {
        final ArrayList<String> holds = new ArrayList<String>();
        final ArrayList<String> optionalToppings = new ArrayList<String>();
        View burgerCheckBoxView = View.inflate(this, R.layout.burger_checkbox, null);


        //region DEFAULT_CHECKBOXES
        CheckBox bunCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.shreddedCheeseCheckbox);
        CheckBox pattyCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.pattyCheckbox);
        CheckBox cheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.cheeseCheckbox);
        CheckBox baconCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.baconCheckbox);
        CheckBox ketchupCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.ketchupCheckbox);
        CheckBox mustardCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.mustardCheckbox);
        CheckBox mayoCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.mayoCheckbox);
        CheckBox lettuceCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.lettuceCheckbox);
        CheckBox pickleCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.pickleCheckbox);
        CheckBox tomatoCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.tomatoCheckbox);
        CheckBox onionCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.onionCheckbox);
        CheckBox extraPattyCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.extraPattyCheckbox);
        CheckBox extraCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.extraCheeseCheckbox);
        CheckBox veggiePattyCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.veggiePattyCheckbox);
        CheckBox footBunCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.footBunCheckBox);
        CheckBox bigfootBaconCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.bigfootBaconCheckBox);
        CheckBox chickenPattyCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.chickenPattyCheckbox);
        CheckBox tartarSauceCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.tartarSauceCheckbox);
        CheckBox alaskanPollockCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.AlaskanPollockCheckBox);
        CheckBox BBQSauceCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.bbqSauceCheckBox);
        //endregion

        //region OPTIONAL_CHECKBOXES
        CheckBox jalapenosCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.jalapenosCheckbox);
        CheckBox mushroomsCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.mushroomCheckbox);
        CheckBox grilledOnionsCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.grilledOnionsCheckbox);
        CheckBox frySauceCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.frySauceCheckbox);
        CheckBox swissCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.swissCheeseChiliCheckbox);
        CheckBox pepperJackCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.pepperJackCheeseChiliCheckbox);
        CheckBox pizzaCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.pizzaCheeseCheckbox);
        //endregion

        final EditText burgerOther = (EditText) burgerCheckBoxView.findViewById(R.id.burgerEditText);

        if (buttonTitle.toString() == getResources().getString(R.string.hamburger_name)){
            foodPrice = 5.99;

            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);

            //set invalid ingredients to false
            BBQSauceCheckBox.setEnabled(false);
            baconCheckBox.setEnabled(false);
            cheeseCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.cheeseburger_name)){
            foodPrice = 6.29;
            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);

            //set invalid ingredients to false
            baconCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            BBQSauceCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.bacon_cheese_burger_name)) {
            foodPrice = 7.09;
            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            baconCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);

            //set invalid ingredients to false
            BBQSauceCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.double_hamburger_name)){
            foodPrice = 8.29;
            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);
            extraPattyCheckBox.setChecked(true);

            //set invalid ingredients to false
            BBQSauceCheckBox.setEnabled(false);
            baconCheckBox.setEnabled(false);
            cheeseCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.double_cheeseburger_name)){
            foodPrice = 8.99;
            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);
            extraPattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            extraCheeseCheckBox.setChecked(true);

            //set invalid ingredients to false
            BBQSauceCheckBox.setEnabled(false);
            baconCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.double_bacon_cheese_burger_name)) {
            foodPrice = 9.79;
            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            baconCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);
            extraCheeseCheckBox.setChecked(true);
            extraPattyCheckBox.setChecked(true);

            //set invalid ingredients to false
            BBQSauceCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.bigfoot_burger_name)){
            foodPrice = 10.99;
            footBunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            extraPattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            extraCheeseCheckBox.setChecked(true);
            bigfootBaconCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);

            //set invalid ingredients to false
            BBQSauceCheckBox.setEnabled(false);
            bunCheckBox.setEnabled(false);
            baconCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.bbq_bacon_cheese_burger_name)){
            foodPrice = 7.09;
            bunCheckBox.setChecked(true);
            pattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            baconCheckBox.setChecked(true);
            BBQSauceCheckBox.setChecked(true);

            //invalid
            mayoCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            onionCheckBox.setChecked(true);
            lettuceCheckBox.setEnabled(false);
            tomatoCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            mustardCheckBox.setEnabled(false);
            ketchupCheckBox.setEnabled(false);
            pickleCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if(buttonTitle.toString() == getResources().getString(R.string.chicken_burger_name)){
            foodPrice = 5.99;
            bunCheckBox.setChecked(true);
            chickenPattyCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);

            //invalid ingredients
            BBQSauceCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            onionCheckBox.setChecked(true);
            baconCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            pattyCheckBox.setEnabled(false);
            cheeseCheckBox.setEnabled(false);
            mustardCheckBox.setEnabled(false);
            ketchupCheckBox.setEnabled(false);
            pickleCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        if(buttonTitle.toString() == getResources().getString(R.string.fish_burger_name)){
            foodPrice = 6.49;
            bunCheckBox.setChecked(true);
            alaskanPollockCheckBox.setChecked(true);
            tartarSauceCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);

            //invalid
            BBQSauceCheckBox.setEnabled(false);
            mayoCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            onionCheckBox.setChecked(true);
            baconCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            veggiePattyCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            pattyCheckBox.setEnabled(false);
            cheeseCheckBox.setEnabled(false);
            mustardCheckBox.setEnabled(false);
            ketchupCheckBox.setEnabled(false);
            pickleCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
        }

        if(buttonTitle.toString() == getResources().getString(R.string.veggie_burger_name)){
            foodPrice = 6.49;
            bunCheckBox.setChecked(true);
            veggiePattyCheckBox.setChecked(true);
            cheeseCheckBox.setChecked(true);
            ketchupCheckBox.setChecked(true);
            mustardCheckBox.setChecked(true);
            mayoCheckBox.setChecked(true);
            lettuceCheckBox.setChecked(true);
            pickleCheckBox.setChecked(true);
            tomatoCheckBox.setChecked(true);
            onionCheckBox.setChecked(true);

            //invalid
            BBQSauceCheckBox.setEnabled(false);
            pattyCheckBox.setEnabled(false);
            footBunCheckBox.setEnabled(false);
            baconCheckBox.setEnabled(false);
            bigfootBaconCheckBox.setEnabled(false);
            extraCheeseCheckBox.setEnabled(false);
            extraPattyCheckBox.setEnabled(false);
            pattyCheckBox.setEnabled(false);
            chickenPattyCheckBox.setEnabled(false);
            tartarSauceCheckBox.setEnabled(false);
            alaskanPollockCheckBox.setEnabled(false);
        }

        //region SET_DEFAULT_CHECKBOXES
        //set default ingredients so as to set holds correctly
        setDefaultCheckBoxListener(bunCheckBox, "Bun", holds);
        setDefaultCheckBoxListener(pattyCheckBox, "Patty", holds);
        setDefaultCheckBoxListener(cheeseCheckBox, "Cheese", holds);
        setDefaultCheckBoxListener(baconCheckBox, "Bacon", holds);
        setDefaultCheckBoxListener(ketchupCheckBox, "Ketchup", holds);
        setDefaultCheckBoxListener(mustardCheckBox, "Mustard", holds);
        setDefaultCheckBoxListener(mayoCheckBox, "Mayo", holds);
        setDefaultCheckBoxListener(lettuceCheckBox, "Lettuce", holds);
        setDefaultCheckBoxListener(pickleCheckBox, "Pickle", holds);
        setDefaultCheckBoxListener(tomatoCheckBox, "Tomato", holds);
        setDefaultCheckBoxListener(onionCheckBox, "Onion", holds);
        setDefaultCheckBoxListener(extraPattyCheckBox, "Extra Patty", holds);
        setDefaultCheckBoxListener(extraCheeseCheckBox, "Extra Cheese", holds);
        setDefaultCheckBoxListener(veggiePattyCheckBox, "Veggie Patty", holds);
        setDefaultCheckBoxListener(footBunCheckBox, "Foot Bun", holds);
        setDefaultCheckBoxListener(bigfootBaconCheckBox, "Bacon (3 slices)", holds);
        //endregion

        //region SET_OPTIONAL_CHECKBOXES
        // set optional ingredients with checkbox listeners
        setOptionalCheckBoxListener(jalapenosCheckBox, "Jalapenos", optionalToppings);
        setOptionalCheckBoxListener(mushroomsCheckBox, "Mushrooms", optionalToppings);
        setOptionalCheckBoxListener(grilledOnionsCheckBox, "Grilled Onions", optionalToppings);
        setOptionalCheckBoxListener(frySauceCheckBox, "Fry Sauce", optionalToppings);
        setOptionalCheckBoxListener(swissCheeseCheckBox, "Swiss Cheese", optionalToppings);
        setOptionalCheckBoxListener(pepperJackCheeseCheckBox, "Pepper Jack", optionalToppings);
        setOptionalCheckBoxListener(pizzaCheeseCheckBox, "Pizza Cheese", optionalToppings);
        //endregion

        AlertDialog.Builder burgerDialog = new AlertDialog.Builder(this);
        burgerDialog.setTitle(buttonTitle + " ($" + foodPrice + ")");
        burgerDialog.setView(burgerCheckBoxView);
        burgerDialog.setCancelable(false);
        burgerDialog.setMessage("Please choose your ingredients:")
                .setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (optionalToppings.isEmpty() && holds.isEmpty() && burgerOther.getText().toString().isEmpty()) {
                            //Toast.makeText(MenuActivity.this, "Ordered a " + buttonTitle, Toast.LENGTH_SHORT).show();
                            try {
                                menuResult(buttonTitle.toString(), "None", "None",
                                        "None", foodPrice);
                            }
                            catch(Exception e){
                                Log.e(TAG + " Error", e.getMessage(), e);
                            }
                        }
                        else
                        {
                            //Toast.makeText(MenuActivity.this, "Ordered a " + buttonTitle + " with: " + optionalToppings.toString()
                            //        + "\n" + "Holds: " + holds.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                menuResult(buttonTitle.toString(), optionalToppings.toString(), holds.toString(),
                                        burgerOther.getText().toString(), foodPrice);
                            }
                            catch (Exception e){
                                Log.e(TAG + " Error", e.getMessage(), e);
                            }
                        }
                    }
                });
        burgerDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MenuActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                foodPrice = 0;
            }
        });
        burgerDialog.show();
    }

    public void openHotDogDialog(final CharSequence buttonTitle) {
        final ArrayList<String> holds = new ArrayList<String>();
        //final ArrayList<String> optionalToppings = new ArrayList<String>();
        View hotDogCheckBoxView = View.inflate(this, R.layout.hotdog_checkbox, null);

        CheckBox mustardDogBox = (CheckBox) hotDogCheckBoxView.findViewById(R.id.mustardHotDogBox);
        CheckBox mayoDogBox = (CheckBox) hotDogCheckBoxView.findViewById(R.id.mayoHotDogBox);
        CheckBox ketchupDogBox = (CheckBox) hotDogCheckBoxView.findViewById(R.id.ketchupHotDogBox);
        CheckBox relishDogBox = (CheckBox) hotDogCheckBoxView.findViewById(R.id.relishHotDogBox);
        CheckBox onionDogBox = (CheckBox) hotDogCheckBoxView.findViewById(R.id.onionHotDogBox);

        final EditText hotDogOther = (EditText) hotDogCheckBoxView.findViewById(R.id.hotDogEditText);

       if (buttonTitle == getResources().getString(R.string.beef_dog_name) ||
               buttonTitle == getResources().getString(R.string.polish_dog_name)){
           foodPrice = 4.69;
            mustardDogBox.setChecked(true);
            mayoDogBox.setChecked(true);
            ketchupDogBox.setChecked(true);
            relishDogBox.setChecked(true);
            onionDogBox.setChecked(true);
        }

        setDefaultCheckBoxListener(mustardDogBox, "Mustard", holds);
        setDefaultCheckBoxListener(mayoDogBox, "Mayo", holds);
        setDefaultCheckBoxListener(ketchupDogBox, "Ketchup", holds);
        setDefaultCheckBoxListener(relishDogBox, "Relish", holds);
        setDefaultCheckBoxListener(onionDogBox, "Onion", holds);

        AlertDialog.Builder hotDogDialog = new AlertDialog.Builder(this);
        hotDogDialog.setTitle(buttonTitle + " ($" + foodPrice + ")");
        hotDogDialog.setView(hotDogCheckBoxView);
        hotDogDialog.setCancelable(false);
        hotDogDialog.setMessage("Please choose your ingredients:")
                .setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (holds.isEmpty() && hotDogOther.getText().toString().isEmpty()) {
                            try{
                                menuResult(buttonTitle.toString(), "N/A", "None",
                                        "None", foodPrice);
                            }
                            catch (Exception e){
                                Log.e(TAG + " Error", e.getMessage(), e);
                            }
                        }
                        else
                        {
                            try {
                                menuResult(buttonTitle.toString(), "N/A", holds.toString(),
                                        hotDogOther.getText().toString(), foodPrice);
                            }
                            catch (Exception e){
                                Log.e(TAG + " Error", e.getMessage(), e);
                            }
                        }
                    }
                });
        hotDogDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MenuActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        hotDogDialog.show();
    }

    public void openChiliDialog(final CharSequence buttonTitle) {
        final ArrayList<String> holds = new ArrayList<String>();
        final ArrayList<String> optionalToppings = new ArrayList<String>();
        View chiliCheckBoxView = View.inflate(this, R.layout.chili_checkbox, null);

        //region IF_FOODPRICE
        if (buttonTitle.toString() == getResources().getString(R.string.chili_bowl_name)){
            foodPrice = 3.39;
        }
        else if (buttonTitle.toString() == getResources().getString(R.string.chili_fry_name)){
            foodPrice = 4.99;
        }
        else if (buttonTitle.toString() == getResources().getString(R.string.chili_dog_name)){
            foodPrice = 5.99;
        }
        else if (buttonTitle.toString() == getResources().getString(R.string.chili_burger_name)){
            foodPrice = 6.29;
        }
        else {
            foodPrice = 0.0;
        }
        //endregion

        //defaults
        CheckBox shreddedCheeseCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.shreddedCheeseCheckbox);
        CheckBox shreddedOnionCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.shreddedOnionCheckbox);

        shreddedCheeseCheckBox.setChecked(true);
        shreddedOnionCheckBox.setChecked(true);
        setDefaultCheckBoxListener(shreddedCheeseCheckBox, shreddedCheeseCheckBox.getText().toString(), holds);
        setDefaultCheckBoxListener(shreddedOnionCheckBox, shreddedOnionCheckBox.getText().toString(), holds);

        //region OPTIONAL_CHECKBOXES
        CheckBox jalapenosCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.jalapenoChiliCheckbox);
        CheckBox mushroomCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.mushroomCheckbox);
        CheckBox americanCheeseCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.americanCheeseCheckbox);
        CheckBox baconCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.baconChiliCheckbox);
        CheckBox swissCheeseCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.swissCheeseChiliCheckbox);
        CheckBox pepperJackCheckBox = (CheckBox) chiliCheckBoxView.findViewById(R.id.pepperJackCheeseChiliCheckbox);

        setOptionalCheckBoxListener(jalapenosCheckBox, jalapenosCheckBox.getText().toString(), optionalToppings);
        setOptionalCheckBoxListener(mushroomCheckBox, mushroomCheckBox.getText().toString(), optionalToppings);
        setOptionalCheckBoxListener(americanCheeseCheckBox, americanCheeseCheckBox.getText().toString(), optionalToppings);
        setOptionalCheckBoxListener(baconCheckBox, baconCheckBox.getText().toString(), optionalToppings);
        setOptionalCheckBoxListener(swissCheeseCheckBox, "Swiss Cheese", optionalToppings);
            setOptionalCheckBoxListener(pepperJackCheckBox, "Pepper Jack", optionalToppings);
        //endregion

        final EditText chiliEditText = (EditText) chiliCheckBoxView.findViewById(R.id.chiliEditText);
        AlertDialog.Builder chiliDialog = new AlertDialog.Builder(this);
        chiliDialog.setTitle(buttonTitle + " ($" + foodPrice + ")");
        chiliDialog.setView(chiliCheckBoxView);
        chiliDialog.setCancelable(false);
        chiliDialog.setMessage("Please choose your ingredients:")
                .setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (optionalToppings.isEmpty() && holds.isEmpty() && chiliEditText.getText().toString().isEmpty()) {
                            try {
                                menuResult(buttonTitle.toString(), "None", "None",
                                        "None", foodPrice);
                            }
                            catch(Exception e){
                                Log.e(TAG + " Error", e.getMessage(), e);
                            }
                        }
                        else
                        {
                            try {
                                menuResult(buttonTitle.toString(), optionalToppings.toString(), holds.toString(),
                                        chiliEditText.getText().toString(), foodPrice);
                            }
                            catch (Exception e){
                                Log.e(TAG + " Error", e.getMessage(), e);
                            }
                        }
                    }
                });
        chiliDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MenuActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        chiliDialog.show();
    }

    void setDefaultCheckBoxListener(CheckBox checkbox, final String ingredient, final ArrayList<String> arrayList){
        //add default items to hold list if unchecked
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    arrayList.remove(ingredient);
                }
                else if (!isChecked){
                    arrayList.add(ingredient);
                }
            }
        });
    }

    void setOptionalCheckBoxListener(CheckBox checkbox, final String ingredient, final ArrayList<String> arrayList){
        //add optional items to arrayList for order, if they are checked
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ingredient == "Swiss Cheese" || ingredient == "Pepper Jack" ||
                    ingredient == "Pizza Cheese"){
                        arrayList.add(ingredient);
                        foodPrice += .75;
                    } else {
                        arrayList.add(ingredient);
                        foodPrice += .5;
                    }
                }
                else if (!isChecked){
                    if (ingredient == "Swiss Cheese" || ingredient == "Pepper Jack" ||
                            ingredient == "Pizza Cheese"){
                        arrayList.remove(ingredient);
                        foodPrice -= .75;
                    } else{
                        arrayList.remove(ingredient);
                        foodPrice -= .5;
                    }
                }
            }
        });
    }

    public void menuResult(String foodTitle, String optToppings, String holds,
                           String editNotes, Double price){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Food Item", foodTitle);
        resultIntent.putExtra("Optional Toppings", optToppings);
        resultIntent.putExtra("Holds", holds);
        resultIntent.putExtra("EditText", editNotes);//);
        resultIntent.putExtra("Price", "$" + price);
        setResult(MENU_RESULT, resultIntent);
        finish();
    }
}
