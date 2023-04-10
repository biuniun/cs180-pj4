package users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.sql.Date;

public class Message {
	private static final String MESS_PATH = "file" + File.separator + "message";
	private Seller seller;
	private Customer customer;
	private String message;
	private long time;
	private boolean sellerVis;
	private boolean customerVis;

	public Message(Seller seller, Customer customer, String message, boolean sellerVis,
			boolean customerVis) {
		this.seller = seller;
		this.customer = customer;
		this.message = message;
		this.time = Timestamp.from(Instant.now()).getTime();
		this.sellerVis = sellerVis;
		this.customerVis = customerVis;
	}

	public Seller getSeller() {
		return this.seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTime() {
		return this.time;
	}

	public boolean isSellerVis() {
		return this.sellerVis;
	}

	public boolean getSellerVis() {
		return this.sellerVis;
	}

	public void setSellerVis(boolean sellerVis) {
		this.sellerVis = sellerVis;
	}

	public boolean isCustomerVis() {
		return this.customerVis;
	}

	public boolean getCustomerVis() {
		return this.customerVis;
	}

	public void setCustomerVis(boolean customerVis) {
		this.customerVis = customerVis;
	}

	public void writeToRecord() {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(MESS_PATH)))) {
			pw.println(this.toString());
		} catch (FileNotFoundException e) {
			System.out.println("Error writing the message, contact administrator!");
		}
	}

	@Override
	public String toString() {
		return getSeller() + ";" 
				+ getCustomer() + ";" 
				+ getMessage() + ";" 
				+ getTime() + ";" 
				+ isSellerVis() + ";"
				+ isCustomerVis() + "\n";
	}

}
