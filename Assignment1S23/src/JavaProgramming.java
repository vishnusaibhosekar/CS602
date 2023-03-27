import java.util.Scanner;

public class JavaProgramming {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = scanner.next();

        System.out.print("Enter student id: ");
        int id = scanner.nextInt();
        
        System.out.print("Enter student type (remote/in-person): ");
        String type = scanner.next();

        if (type.equalsIgnoreCase("remote")) {
            System.out.print("Enter midterm score: ");
            double midterm = scanner.nextDouble();

            System.out.print("Enter final score: ");
            double finalScore = scanner.nextDouble();

            System.out.print("Enter assignments score: ");
            double assignments = scanner.nextDouble();

            System.out.print("Enter discussion score: ");
            double discussion = scanner.nextDouble();

            RemoteStudents remoteStudent = new RemoteStudents(id, name, type, midterm, finalScore, assignments, discussion);
            System.out.println("Weighted score: " + String.format("%.2f", remoteStudent.score()));
            System.out.println(remoteStudent);
        } else if (type.equalsIgnoreCase("in-person")) {
            System.out.print("Enter midterm score: ");
            double midterm = scanner.nextDouble();

            System.out.print("Enter final score: ");
            double finalScore = scanner.nextDouble();

            System.out.print("Enter assignments score: ");
            double assignments = scanner.nextDouble();

            InPersonStudents inPersonStudent = new InPersonStudents(id, name, type, midterm, finalScore, assignments);
            System.out.println("Weighted score: " + String.format("%.2f", inPersonStudent.score()));
            System.out.println(inPersonStudent);
        } else {
            System.out.println("Invalid student type. Please enter either 'remote' or 'in-person'.");
        }
        scanner.close();
    }
}