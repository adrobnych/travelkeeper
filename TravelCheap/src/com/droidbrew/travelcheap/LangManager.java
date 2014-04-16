package com.droidbrew.travelcheap;

public class LangManager {

	private String locale = "en";
	
	public LangManager() {
	
	}
	
	public LangManager(String locale) {
		this.locale = locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getTypeFromDB(String dbType) {
		String type = dbType;
		
		if(locale.equals("ru")){
			if(type.equals("food"))
				return "Еда";
			if(type.equals("transport"))
				return "Транспорт";
			if(type.equals("accommodation"))
				return "Жилье";
			if(type.equals("shopping"))
				return "Покупка";
			if(type.equals("entertainment"))
				return "Развлечения";
			if(type.equals("other things"))
				return "Другие вещи";
		}
		
		return type;
	}
	
	public String getMsg1() {
		if(locale.equals("ru"))
			return "Последние 3 дня";
		else
			return "Last 3 days";
	}
	
	public String getMsg2() {
		if(locale.equals("ru"))
			return "Последния неделя";
		else
			return "Last week";
	}
	
	public String getMsg3() {
		if(locale.equals("ru"))
			return "Последний месяц";
		else
			return "Last month";
	}
	public String getMsg4() {
		if(locale.equals("ru"))
			return "Суммарный";
		else
			return "Total";
	}
	
}
