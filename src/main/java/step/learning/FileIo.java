package step.learning;

import java.io.File;

public class FileIo {
    public void run() {
        // Робота з файлами
        // традиційно поділяється на дві групи:
        // - створення, пошук, копіювання (робота з файловою системою)
        fsDemo() ;
        listDemo() ;
        // - збереження та відновлення даних
    }

    private void listDemo() {
        // одержання переліку файлів у директорії
        String filename = "." + File.separator ;  // ".\" - current directory
        File f = new File( filename ) ;
        // f.list() - перелік імен файлів у директорії (String[])
        String[] list = f.list() ;
        if( list == null ) {
            System.err.println("Access denied");
        }
        else {
            for (String name : list) {
                System.out.println(name);
            }
        }
        // f.listFiles() - перелік файлових об'єктів (File[])
        File[] files = f.listFiles() ;
        if( files == null ) {
            System.err.println("Access denied");
        }
        else {
            for( File file : files ) {
                System.out.println( file.getName() ) ;
            }
        }
        /*
        Д.З. Реалізувати відображення файлів поточної директорії
        за зразком команди ls / dir
        Mode                 LastWriteTime     Length   Name
        ----                 -------------     ------   ----
        d-----        24.01.2024     18:51              .idea
        d-----        22.01.2024     17:51              src
        d-----        22.01.2024     18:08              target
        -a----        22.01.2024     17:50        490   .gitignore
        -a----        22.01.2024     17:50        490   .gitignore
        -a----        22.01.2024     17:51        780   pom.xml

        ------ (d-directory, a-archive, h-hidden, r/R - canRead, w/W - canWrite, x/X - canExec)
        -a-rwX - архівний файл з дозволом на читання/запис, але заборонений для виконання

        Викласти у репозиторії, додати скріншоти результатів виконання
         */
    }

    private void fsDemo() {
        String filename = "." + File.separator ;  // ".\" - current directory
        File f = new File( filename ) ;
        // ! створення об'єкту НЕ створює/відкриває файл (ніяк не впливає на файлову систему)
        // для впливу на ФС викликаються методи даного об'єкту
        if( f.exists() ) {
            System.out.printf( "Path '%s' exists\n", filename ) ;
        }
        else {
            System.out.printf( "Path '%s' does not exist\n", filename  ) ;
        }
        // !!! у файловій системі є різні об'єкти, за які відповідає File:
        // це директорії, файли, сімлінки (де вони існують) тощо
        // f.exists() визначає існування незалежно від виду об'єкта
        if( f.isDirectory() ) {
            System.out.printf( "Path '%s' exists as directory\n", filename ) ;
        }
        if( f.isFile() ) {
            System.out.printf( "Path '%s' exists as file\n", filename ) ;
        }
        filename = "." + File.separator + "subdir" ;
        File subDir = new File(filename) ;
        if( subDir.exists() ) {
            System.out.printf( "Path '%s' exists \n", filename ) ;
            if( subDir.delete() ) {
                System.out.printf( "Path '%s' deleted \n", filename ) ;
            }
            else {
                System.out.printf( "Path '%s' deletion error \n", filename ) ;
            }
        }
        else {
            if(subDir.mkdir())   // створення директорії
                System.out.printf( "Path '%s' created \n", filename ) ;
            else
                System.out.printf( "Path '%s' creation error \n", filename ) ;
        }
    }
}
