package com.fsd.controller;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.*;
import java.text.*;
import com.fsd.model.*;
import com.fsd.ap.fx.*;

public class AccountController extends Controller<Account> {
	/* Step 1: Define the FXML properties
		- Define the text property typeTxt
		- Define the text property balanceTxt
		- Define the text property amountTf
	*/
	@FXML private Text typeTxt;
	@FXML private Text balanceTxt;
	@FXML private TextField amountTf;

	

	/* Step 2: Define the getAccount() function
		- The function should return Account model
		- The function is public and final
	*/
	public final Account getAccount() { return model; }


	/* Step 3: Update the initialize() method
		- bind typeTxt bidirectional to account property type
		- bind balance to account property balance 
	*/
	@FXML private void initialize() {
		typeTxt.textProperty().bindBidirectional(getAccount().typeProperty());
		balanceTxt.textProperty().bind(getAccount().balanceProperty().asString("$%.2f"));
	}
	
	/* Step 4: Define amount getter/setter
		- Define a private function to capture amountTf string and convert it to double then return it
		- Define a private procedure to set amountTf string value as a decimal formatter at 2-decimal points
	*/

	private double getAmount() {
		return Double.parseDouble(amountTf.getText());
	}

	private void setAmount(double value) {
		amountTf.setText(new DecimalFormat("###,##0.00").format(value));
	}

	/* Step 5: Define the FXML handleDeposit(ActionEvent) method
		- Try to make a deposit using the current account
		- Catch any exception and show it on the error.fxml
		- finally reset the amountTf to 0
	*/

	@FXML private void handleDeposit(ActionEvent event) throws IOException {
		try {
			getAccount().deposit(getAmount());
		}
		catch (Exception e) {
			// If an exception occurred, show an error window.
			// NOTE: the following line might *also* throw an IOException if
			// /view/error.fxml is not found (location not set) or if the
			// FXML file itself contains errors. But in that case, we can't
			// show the error anyway, so we just throw IOException.
			ViewLoader.showStage(e, "/com/fsd/view/error.fxml", "Error", new Stage());
		}
		finally {
			// Always reset amount, even in the case of an error.
			setAmount(0);
		}
	}

	/* Step 6: Define the FXML handleWithdraw(ActionEvent) method
		- Try to make a withdraw using the current account
		- Catch any exception and show it on the error.fxml
		- finally reset the amountTf to 0
	*/	

	@FXML private void handleWithdraw(ActionEvent event) throws IOException {
		try {
			getAccount().withdraw(getAmount());
			setAmount(0);
		}
		catch (Exception e) {
			// If an exception occurred, show an error window.
			// NOTE: the following line might *also* throw an IOException if
			// /view/error.fxml is not found (location not set) or if the
			// FXML file itself contains errors. But in that case, we can't
			// show the error anyway, so we just throw IOException.
			ViewLoader.showStage(e, "/com/fsd/view/error.fxml", "Error", new Stage());
		}
		finally {
			// Always reset amount, even in the case of an error.
			setAmount(0);
		}
	}
	
}
