package ru.stqa.pft.sandbox;

import java.lang.Math;

public class Point {        //Задание 2.1
  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point p2) {   //Задание 2.4. Высчитываем расстояние от текущей точки к другой точке
    double distance = Math.sqrt(Math.pow((p2.x - this.x),2) + Math.pow((p2.y - this.y),2));
    return distance;
  }

  public String toString() {
    String point = ("(" + this.x + ", " + this.y + ")");
    return point;
  }
}
