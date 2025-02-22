import java.io.*;
import java.util.*;

class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

class Book implements Serializable {
    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void displayBookInfo() {
        System.out.println("Title: " + title + ", Author: " + author + ", Price: $" + price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return title.equalsIgnoreCase(book.title);
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}

class Library {
    private ArrayList<Book> books;
    private static final String FILE_NAME = "books.txt";

    public Library() {
        books = new ArrayList<>();
        loadBooksFromFile();
    }

    public synchronized void addBook(Book book) {
        try {
            Thread.sleep(500);
            if (books.contains(book)) {
                System.out.println("This book already exists in the library.");
            } else {
                books.add(book);
                saveBooksToFile();
                System.out.println("Book added: " + book.getTitle());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error adding book.");
        }
    }

    public synchronized void removeBook(String title) throws BookNotFoundException {
        try {
            Thread.sleep(500);
            Iterator<Book> iterator = books.iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getTitle().equalsIgnoreCase(title)) {
                    iterator.remove();
                    saveBooksToFile();
                    System.out.println("Book removed: " + title);
                    return;
                }
            }
            throw new BookNotFoundException("Book not found: " + title);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error removing book.");
        }
    }

    public synchronized void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                book.displayBookInfo();
            }
        }
    }

    private void saveBooksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving books to file.");
        }
    }

    private void loadBooksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            books = (ArrayList<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading books from file.");
        }
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addBookToLibrary(Library library, Book book) {
        library.addBook(book);
    }

    public void removeBookFromLibrary(Library library, String bookTitle) {
        try {
            library.removeBook(bookTitle);
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewBooks(Library library) {
        library.listBooks();
    }
}

public class BookManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        User user = new User(userName);

        Thread menuThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("\n--- Book Management System ---");
                    System.out.println("1. Add a book");
                    System.out.println("2. Remove a book");
                    System.out.println("3. View all books");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");

                    int choice;
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                    } else {
                        System.out.println("Invalid input! Please enter a number.");
                        scanner.nextLine();
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            System.out.print("Enter book title: ");
                            String title = scanner.nextLine();
                            System.out.print("Enter book author: ");
                            String author = scanner.nextLine();
                            System.out.print("Enter book price: ");
                            double price;

                            if (scanner.hasNextDouble()) {
                                price = scanner.nextDouble();
                                scanner.nextLine();
                            } else {
                                System.out.println("Invalid price format. Please try again.");
                                scanner.nextLine();
                                break;
                            }

                            Book book = new Book(title, author, price);
                            user.addBookToLibrary(library, book);
                            break;

                        case 2:
                            System.out.print("Enter the title of the book to remove: ");
                            String bookTitle = scanner.nextLine();
                            user.removeBookFromLibrary(library, bookTitle);
                            break;

                        case 3:
                            System.out.println("\nBooks in Library:");
                            user.viewBooks(library);
                            break;

                        case 4:
                            System.out.println("Exiting the program. Goodbye, " + user.getName() + "!");
                            scanner.close();
                            return;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine();
                }
            }
        });

        menuThread.start();
    }
}
