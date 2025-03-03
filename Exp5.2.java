Java program that serializes and deserializes a Student object. It saves the Student object to a file and then reads it back, displaying the student details.
The program handles exceptions like FileNotFoundException, IOException, and ClassNotFoundException.

Steps:
1. Create a Student class with id, name, and GPA.
2. Serialize the Student object: Convert the object to a byte stream and save it to a file.
3. Deserialize the Student object: Read the byte stream from the file and convert it back into an object.
4. Exception handling: Handle possible exceptions such as FileNotFoundException, IOException, and ClassNotFoundException.


----Implementation
---Student Class: The Student class implements the Serializable interface, allowing it to be serialized. It has three fields: id, name, and gpa.
---serializeStudent(): This method serializes a Student object to a file using ObjectOutputStream. The object is written to a file named student.ser.
---deserializeStudent(): This method deserializes the Student object from the file using ObjectInputStream. If successful, it returns the deserialized Student object.
---Exception Handling: The program handles FileNotFoundException, IOException, and ClassNotFoundException during the serialization and deserialization processes.



Test Cases:

Test Case 1: Serialize and Deserialize a valid student object.
  
Input: Student(1, "John Doe", 3.75)
Expected Output:
Student object has been serialized and saved to file.
Student object has been deserialized.
Deserialized Student Details:
Student ID: 1, Name: John Doe, GPA: 3.75
  
Test Case 2: Try to deserialize from a non-existent file.
Expected Output:
Error: File not found.
  
Test Case 3: Handle invalid class during deserialization.
Input: Manually modify the class file to simulate a ClassNotFoundException.
Expected Output:
Error: Class not found.



  import java.io.*;

// Student class implementing Serializable interface
class Student implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for serialization
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", GPA: " + gpa;
    }
}

public class StudentSerialization {

    public static void serializeStudent(Student student) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            oos.writeObject(student);
            System.out.println("Student object has been serialized and saved to file.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
    }

    public static Student deserializeStudent() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.ser"))) {
            Student student = (Student) ois.readObject();
            System.out.println("Student object has been deserialized.");
            return student;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return null;
        } catch (IOException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class not found.");
            return null;
        }
    }

    public static void main(String[] args) {
        // Test Case 1: Serialize and Deserialize a valid student object
        Student student = new Student(1, "John Doe", 3.75);
        serializeStudent(student);
        Student deserializedStudent = deserializeStudent();
        if (deserializedStudent != null) {
            System.out.println("Deserialized Student Details:");
            System.out.println(deserializedStudent);
        }

        // Test Case 2: Try to deserialize from a non-existent file
        System.out.println("\nAttempting to deserialize from a non-existent file:");
        new File("student.ser").delete(); // Delete the file to simulate the error
        deserializeStudent();

        // Test Case 3: Handle invalid class during deserialization
        // This test case requires manual modification of the Student class.
        // Uncomment the following line to simulate ClassNotFoundException.
        System.out.println("\nAttempting to deserialize with an invalid class:");
        deserializeStudent();
    }
}
