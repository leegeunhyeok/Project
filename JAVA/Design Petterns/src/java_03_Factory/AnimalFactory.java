package java_03_Factory;

public class AnimalFactory {
	
	//Factory Method//
	public static Animal create(String animalName){
		if(animalName == null){
			throw new IllegalArgumentException("NULL");
		}
		
		if(animalName.equals("소")){
			return new Cow();
		} else if(animalName.equals("고양이")){
			return new Cat();
		} else if(animalName.equals("강아지")){
			return new Dog();
		} else {
			return null;
		}
	}
}
