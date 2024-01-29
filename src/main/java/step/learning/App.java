package step.learning;

import step.learning.oop.OopDemo;

public class App
{
    public static void main( String[] args )
    {
        // System.out.println( "Hello World!" );
        // new Basics().run();
        // new FileIo().run();
        new OopDemo().run();
    }
}
/*
Java:
 - JRE (JVM) - середовище виконання
 - JDK - засоби розробника (~компілятор)
 - IDE - Intellij Idea (Apache NetBeans, Eclipse, ...)

= Мова транслюючого типу (видає проміжний код - файли .class)
= GL (покоління) 4, парадигма - ООП, типізація - статична
= Висока зворотня сумісність
= Прив'язка до файлової системи:
 - файли класів повинні називатись так само, як і самі класи (+.java)
  -> один файл - один клас (public)
 - пакети (package) - називають як папки, точка у назвах - підпапки
= Casing
 - Types: CapitalCamelCase
 - methods, vars: lowerCamelCase
 - const: PASCAL_CASE
 - packages: snake_case
 */
