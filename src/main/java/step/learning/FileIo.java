package step.learning;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileIo {
    public void run() {
        // Робота з файлами
        // традиційно поділяється на дві групи:
        // - створення, пошук, копіювання (робота з файловою системою)
        // fsDemo() ;
        // listDemo() ;
        // - збереження та відновлення даних
        // ioDemo() ;
        // readStreamDemo() ;
        gsonDemo() ;
    }

    private void gsonDemo() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "Felix");
        json.addProperty("age", "12");
        System.out.println( json.toString() ) ;
        JsonObject j2 = JsonParser                // Парсер - з строки/файлу - у Json
                .parseString(json.toString())     // res -> JsonElement
                .getAsJsonObject();               // -> to JsonObject
        System.out.println( j2.toString() ) ;
    }
    /*
    Д.З. JSON телефонний каталог
    при старті програма виводить наявні записи (ПІБ - телефон)
    та пропонує додати новий запис (з консолі)
    після введення дані додаються до файлу і наступний запуск
    покаже їх у складі інших записів.
    (використовувати формат JSON для збереження даних)
     */

    private void readStreamDemo() {
        try( InputStream fileStream = Files.newInputStream( Paths.get("file.txt")) ) {
            System.out.println( readStreamToEnd( fileStream ) ) ;
        }
        catch( IOException ex ) {
            System.err.println( ex.getMessage() );
        }
    }

    // вправа - зчитати потік як String
    private String readStreamToEnd( InputStream inputStream ) throws IOException {
        // варіант 1 (гірший)
        /*
        int sym ;
        StringBuilder res = new StringBuilder();
        while( ( sym = inputStream.read() ) != -1 ) {
            // read -> byte + розширення до int для виняткових значень
            // ?? -1(byte) =?= -1(int)
            // ні, це різні сутності: -1(byte) = 11111111  -1(int) = 111(32)111
            // -1(byte) розширене до int -> 000(24)00011111111
            res.append((char)sym);
        }
        return res.toString() ;
         */

        // варіант 2 (кращий)
        final byte[] buffer = new byte[32 * 1024];  // 32k
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream() ;
        int len ;
        while( ( len = inputStream.read( buffer ) ) > -1 ) {
            byteBuilder.write( buffer, 0, len ) ;
        }
        return byteBuilder.toString() ;
    }
    private void ioDemo() {
        // Основу роботи з файлами (як сховищами даних) складає потік (stream)
        // try-with-resource - поєднання try та using(C#)
        try (OutputStream writeStream = new FileOutputStream("file.txt")) {
            writeStream.write(
                    "Hello, World!".getBytes( StandardCharsets.UTF_8 )
            );
            // writeStream.close();
        }
        catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }
        // Stream - базові можливості, обмежені одним символом (байтом) або їх масивом
        // є велика кількість розширень Stream, які спрощують роботу з основними типами даних
        try ( FileWriter writer = new FileWriter( "file2.txt" ) ) {
            writer.write("Hello, World!");
        }
        catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }
        try( DataOutputStream dos = new DataOutputStream(
                Files.newOutputStream( Paths.get("file3.txt") )  // більш сучасна версія роботи з файлами
        ) ) {
            dos.writeDouble( 0.1 ) ;
        }
        catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        // ----------------------------------------------
        // читання - так само, є InputStream з базовими можливостями
        // FileReader, DataReaderStream - під типи даних
        // BufferedInputStream, BufferedReader - з проміжним буфером (окрім системного)
        try( Scanner scanner = new Scanner( new FileInputStream( "file.txt" ) ) ) {
            while( scanner.hasNext() ) {
                System.out.print( scanner.next() ) ;  // ! .next() читає слово (не рядок), роздільні символи
            }                                         // (пробіли) ігноруються.
            System.out.println();
        }
        catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }
        Scanner kbScanner = new Scanner( System.in ) ;   // keyboard scanner
        System.out.print( "Your name: " ) ;
        boolean name = kbScanner.nextBoolean() ;
        System.out.println( "Hello, " + name ) ;
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
