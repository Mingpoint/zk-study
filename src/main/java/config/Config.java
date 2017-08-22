package config;

import java.io.Serializable;

public class Config implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String name;
	private String password;
	public Config() {
	}
	public Config(String url, String name, String password) {
		super();
		this.url = url;
		this.name = name;
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Config [url=" + url + ", name=" + name + ", password="
				+ password + "]";
	}
}
