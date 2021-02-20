package com.ubs.teste.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

	@SerializedName("product")
	@Expose
	private String product;
	@SerializedName("quantity")
	@Expose
	private Integer quantity;
	@SerializedName("price")
	@Expose
	private String price;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("industry")
	@Expose
	private String industry;
	@SerializedName("origin")
	@Expose
	private String origin;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

}