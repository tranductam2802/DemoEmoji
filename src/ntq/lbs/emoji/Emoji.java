package ntq.lbs.emoji;

public class Emoji{
	private String key;
	private String value;
	
	public Emoji(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public String getValue(){
		return this.value;
	}
}