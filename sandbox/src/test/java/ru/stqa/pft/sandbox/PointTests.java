package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.lang.Math;



public class PointTests {

  @Test
  public void distanceTest() {
    Point a = new Point(1, 2);
    Point b = new Point (3, 2);
    Point c = new Point (2, 1);
    Point d = new Point (1, -1);

    Assert.assertEquals(a.distance(b), 2.00); // стандартная проверка по горизонтали

    Assert.assertNotEquals(a.distance(b), 2); // убеждаемся, что метод distance
                                              // возвращает строго double-значение

    Assert.assertEquals(a.distance(d),3.00); // проверяем работу с отрицательными
                                             // координатами, станд. по вертикали

    Assert.assertEquals(a.distance(c), c.distance(a)); // расстояния двух точек относительно
                                                       // друг друга равны

    Assert.assertEquals(a.distance(c), Math.sqrt(2));  // станд. проверка двух точек на диагонали,
                                                       // работа с не-int-значениями
  }
}
