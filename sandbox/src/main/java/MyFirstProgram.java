public class MyFirstProgram {

	public static double distance(Point p1, Point p2) {         //Задание 2.2
		double distance = Math.sqrt(Math.pow((p2.x - p1.x),2) + Math.pow((p2.y - p1.y),2));
		return distance;
	}

	public static void main(String[] args) {
		System.out.println("Hello World!"); //Задание 1

		Point alpha = new Point(9, 8);  //Задание 2.3
		Point beta = new Point(6, 4);

		System.out.println("Расстояние между точками " + alpha.toString() + " и " + beta.toString() + " составляет " + distance(alpha, beta));
		System.out.println("Расстояние от точки " + alpha.toString() + " к точке " + beta.toString() + " составляет " + alpha.distance(beta)); //Задание 2.4
	}
}