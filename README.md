# Book Management System 📚  

## Overview  
The **Book Management System** is a simple Java-based console application that allows users to manage a collection of books in a library. It provides functionalities such as adding books, removing books, and listing all books available in the library. The system also handles exceptions for cases where books are not found and ensures thread synchronization for concurrent operations.

## Features  
✅ **Add Books** – Users can add books to the library, ensuring duplicates are not allowed.  
✅ **Remove Books** – Users can remove books by title, with error handling for non-existent books.  
✅ **View Books** – Displays all books currently available in the library.  
✅ **User Interaction** – Each user can manage the library using an interactive menu system.  
✅ **Thread Synchronization** – Ensures safe concurrent access to the library.  

## Technologies Used  
- **Java** – Core programming language  
- **Object-Oriented Programming (OOP)** – Encapsulation, inheritance, and polymorphism  
- **Exception Handling** – Custom `BookNotFoundException` for missing books  
- **Thread Synchronization** – `synchronized` methods to prevent concurrency issues  
- **Java Collections** – Uses `ArrayList` for book storage  

## How to Run  
1. **Clone the Repository**  
   ```bash
   git clone https://github.com/your-username/book-management-system.git
   cd book-management-system
   ```
2. **Compile the Code**  
   ```bash
   javac BookManagementSystem.java
   ```
3. **Run the Program**  
   ```bash
   java BookManagementSystem
   ```
4. **Follow the Menu Instructions**  
   - Enter your name.  
   - Choose an option:  
     - **1** to add a book  
     - **2** to remove a book  
     - **3** to view all books  
     - **4** to exit  

## Example Usage  
**OUTPUT**
```
Enter your name: Alice

--- Book Management System ---
1. Add a book
2. Remove a book
3. View all books
4. Exit
Enter your choice: 1

Enter book title: Java Basics
Enter book author: John Doe
Enter book price: 19.99
Book added: Java Basics
```
