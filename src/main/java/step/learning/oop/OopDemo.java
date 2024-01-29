package step.learning.oop;

import java.util.ArrayList;
import java.util.List;

public class OopDemo {
    public void run() {
        System.out.println("OOP");
        List<Literature> catalog = new ArrayList<>();
        try {
            catalog.add(new Book("D. Knuth", "Art of Programming"));
            catalog.add(new Newspaper("Daily Mail", "2024-01-29"));
        }
        catch (Exception ex) {
            System.err.println( "Literature add error: " + ex.getMessage() ) ;
            return ;
        }
        for( Literature literature : catalog ) {
            System.out.println( literature.getCard() );
        }
    }
}
/*
ООП: парадигма, згідно з якою програма - це сукупність об'єктів,
які взаємодіють один з одним
- інкапсуляція
- поліморфізм
- абстракція/спадкування

Приклад: бібліотека (ТЗ: оцифрувати бібліотеку)
- виділяємо сутності: книга, газета, журнал
- моделювання: книга(автор, назва), газета(дата, назва), журнал(номер, назва)
- абстрагування (групування, generalization):
                 Література(назва)     -- абстракція (групуючий термін)
                /       |        \
    книга(автор)   газета(дата)   журнал(номер)
- поведінка (які методи мають бути)
    getCard() - карточка для каталога
- поліморфізм - поєднання різних сутностей у єдину колекцію
 */
