package com.example.sylvancombinedui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    //String Toppings;
    final int MENU_RESULT = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
    }

    public void openBurgerDialog(final CharSequence buttonTitle) {
        final ArrayList<String> holds = new ArrayList<String>();
        final ArrayList<String> optionalToppings = new ArrayList<String>();
        View burgerCheckBoxView = View.inflate(this, R.layout.burger_checkbox, null);


        //region DEFAULT_CHECKBOXES
        CheckBox bunCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.bunCheckbox);
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
        //endregion

        //region OPTIONAL_CHECKBOXES
        CheckBox jalapenosCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.jalapenosCheckbox);
        CheckBox mushroomsCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.mushroomCheckbox);
        CheckBox grilledOnionsCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.grilledOnionsCheckbox);
        CheckBox frySauceCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.frySauceCheckbox);
        CheckBox swissCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.swissCheeseCheckbox);
        CheckBox pepperJackCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.pepperJackCheeseCheckbox);
        CheckBox pizzaCheeseCheckBox = (CheckBox) burgerCheckBoxView.findViewById(R.id.pizzaCheeseCheckbox);
        //endregion

        if (buttonTitle.toString() == getResources().getString(R.string.hamburger_name)){
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
            extraCheeseCheckBox.setEnabled(false);
        }

        if (buttonTitle.toString() == getResources().getString(R.string.cheeseburger_name)){
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
        }

        if (buttonTitle.toString() == getResources().getString(R.string.bacon_cheese_burger_name)) {
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
        }

        if (buttonTitle.toString() == getResources().getString(R.string.bigfoot_burger_name)){
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
        }

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

        // set optional ingredients with checkbox listeners
        setOptionalCheckBoxListener(jalapenosCheckBox, "Jalapenos", optionalToppings);
        setOptionalCheckBoxListener(mushroomsCheckBox, "Mushrooms", optionalToppings);
        setOptionalCheckBoxListener(grilledOnionsCheckBox, "Grilled Onions", optionalToppings);
        setOptionalCheckBoxListener(frySauceCheckBox, "Fry Sauce", optionalToppings);
        setOptionalCheckBoxListener(swissCheeseCheckBox, "Swiss Cheese", optionalToppings);
        setOptionalCheckBoxListener(pepperJackCheeseCheckBox, "Pepper Jack", optionalToppings);
        setOptionalCheckBoxListener(pizzaCheeseCheckBox, "Pizza Cheese", optionalToppings);

        AlertDialog.Builder burgerDialog = new AlertDialog.Builder(this);
        burgerDialog.setTitle(buttonTitle);
        burgerDialog.setView(burgerCheckBoxView);
        burgerDialog.setCancelable(false);
        burgerDialog.setMessage("Please choose your ingredients:")
                .setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (optionalToppings.isEmpty() && holds.isEmpty()) {
                            //Toast.makeText(MenuActivity.this, "Ordered a " + buttonTitle, Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Food Item", buttonTitle);
                            resultIntent.putExtra("Optional Toppings", "None");
                            resultIntent.putExtra("Holds", "None");
                            setResult(MENU_RESULT, resultIntent);
                            finish();
                        }
                        else
                        {
                            //Toast.makeText(MenuActivity.this, "Ordered a " + buttonTitle + " with: " + optionalToppings.toString()
                            //        + "\n" + "Holds: " + holds.toString(), Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Food Item", buttonTitle);
                            resultIntent.putExtra("Optional Toppings", optionalToppings.toString());
                            resultIntent.putExtra("Holds", holds.toString());
                            setResult(MENU_RESULT, resultIntent);
                            finish();
                        }
                    }
                });
        burgerDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MenuActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        burgerDialog.show();
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
                    arrayList.add(ingredient);
                }
                else if (!isChecked){
                    arrayList.remove(ingredient);
                }
            }
        });
    }

}
