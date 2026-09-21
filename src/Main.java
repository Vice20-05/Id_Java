
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws  Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nenter option:\t");
            System.out.println("\n1. Read data from file");
            System.out.println("\n2. Read data from console");
            System.out.println("\nYour option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> readFile("people.txt");
                case 2 -> readConsole(scanner);
                default -> System.out.println("\033[31mInvalid choice\033[0m");
            }
        }
    }

    public static void readFile(String fileName) throws IOException {
        Path file = findFile(fileName);
        if (file == null) {
            System.out.println("\033[31mFile '" + fileName + "' not found.\033[0m");
            System.out.println("\033[31mMake sure people.txt is in the project folder.\033[0m");
            return;
        }
        for (String line : Files.readAllLines(file)) {
            processPerson(line);
        }
    }

    // looks for the file next to where you run it (current folder or parent folder)
    public static Path findFile(String fileName) {
        if (Files.exists(Path.of(fileName))) {
            return Path.of(fileName);
        }
        Path parent = Path.of("..", fileName);
        if (Files.exists(parent)) {
            return parent;
        }
        return null;
    }
    
    public static void readConsole(Scanner scanner){
        System.out.println("\nEnter name , age ,id (type'stop'to finish)");
        while(true){
            String line=scanner.nextLine();
            if(line.equalsIgnoreCase("stop")){
                break;
            }
            processPerson(line);
        }
    }


     // splits one row, validates it, builds a Person and shows the result
    public static void processPerson(String line){
        String[] parts=line.split(",");
        if(parts.length !=3){
            System.out.println("\033[31mInvalid Row (must be name,age,id):\033[0m"+line);
            return;
        }
        String name=parts[0].trim();
        String id=parts[2].trim();

        //validate age
        int age;
        try { 
            age=Integer.parseInt(parts[1].trim());
        }catch (NumberFormatException e){
            System.out.println("\033[31m"+name+" :Invalid age '"+parts[1].trim()+"'\033[0m");
            return;
        }
        if (age<0||age>120){
            System.out.println("\033[31m"+name+" :age out of range ("+age+")\033[0m");
            return;
        }

        //validate ID: must be only digits, 6 to 8 character long

        if(!id.matches("\\d{6,8}")){
            System.out.println("\033[31m"+name+": Invalid ID "+id+" Must be 6 to 8 digit long\033[0m");
            return;
        }
         
          // build a Person object using setters
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setId(id);

        // read the values back with getters
        if (person.getAge() < 18) {
            System.out.println(person.getName() + " is UNDERAGE (" + person.getAge() + "), ID: " + person.getId());
        } else {
            System.out.println(person.getName() + " is an adult (" + person.getAge() + "), ID: " + person.getId());
        }

    }
}
