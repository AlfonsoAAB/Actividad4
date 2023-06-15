import java.io.*;
import java.util.*;

public class AddressBook {
    private Map<String, String> contacts;
    private String fileName;

    public AddressBook(String fileName) {
        contacts = new HashMap<>();
        this.fileName = fileName;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String number = parts[0].trim();
                    String name = parts[1].trim();
                    contacts.put(number, name);
                }
            }
            System.out.println("Contactos cargados desde el archivo.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos desde el archivo.");
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String number = entry.getKey();
                String name = entry.getValue();
                writer.write(number + " : " + name);
                writer.newLine();
            }
            System.out.println("Cambios guardados en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al guardar los cambios en el archivo.");
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String number = entry.getKey();
            String name = entry.getValue();
            System.out.println(number + " : " + name);
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
        System.out.println("Contacto creado: " + number + " : " + name);
    }

    public void delete(String number) {
        if (contacts.containsKey(number)) {
            String name = contacts.remove(number);
            System.out.println("Contacto eliminado: " + number + " : " + name);
        } else {
            System.out.println("No se encontró el contacto con el número " + number);
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contacts.txt");
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAgenda Telefónica");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Ingrese la opción deseada: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingrese el número telefónico: ");
                    String newNumber = scanner.nextLine();
                    System.out.print("Ingrese el nombre del contacto: ");
                    String newName = scanner.nextLine();
                    addressBook.create(newNumber, newName);
                    break;
                case 3:
                    System.out.print("Ingrese el número telefónico del contacto a eliminar: ");
                    String deleteNumber = scanner.nextLine();
                    addressBook.delete(deleteNumber);
                    break;
                case 4:
                    addressBook.save();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida. Inténtelo de nuevo.");
                    break;
            }
        }
    }
}
