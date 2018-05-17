package unisexToiletProblem;

public class Person {
	private Gender gender;
	private Integer time_to_use;
	
	public Person(Gender gender){
		this.gender = gender;
		time_to_use = 0;
	}

	public Gender getGender() {
		return gender;
	}

	public Integer getTime_to_use() {
		return time_to_use;
	}

	public void setTime_to_use(Integer time_to_use) {
		this.time_to_use = time_to_use;
	}
}
