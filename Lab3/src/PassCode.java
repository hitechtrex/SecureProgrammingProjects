//package week1.xml_inject.sample;

public class PassCode {
	private String Key;
	private String SecurityCode;
	private String Pin;
	
	
	public String getKey() {
		return Key;
	}




	public void setKey(String key) {
		Key = key;
	}




	public String getSecurityCode() {
		return SecurityCode;
	}




	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}




	public String getPin() {
		return Pin;
	}




	public void setPin(String pin) {
		Pin = pin;
	}




	public String toString(){
		return "Pass Code Info:\n\tKey : " + Key + "\n\tSecurity Code : " + SecurityCode +
				"\n\tPin : " + Pin;
	}
}
