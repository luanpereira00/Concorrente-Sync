package unisexToiletProblem;

public class Person {
	private Gender gender;
	private Integer timeToUse;
	
	public Person(Gender gender){
		this.gender = gender;
		timeToUse = null;
	}

	public Gender getGender() {
		return gender;
	}

	public Integer getTimeToUse() {
		return timeToUse;
	}

	public void setTimeToUse(Integer time_to_use) {
		this.timeToUse = time_to_use;
	}
}
