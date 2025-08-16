// Welcome to CodeRunner!
// Java code example

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Arrays and loops
        int[] numbers = {1, 2, 3, 4, 5};
        System.out.print("Numbers: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Method call
        greetUser("Developer");
    }

    public static void greetUser(String name) {
        System.out.println("Hello, " + name + "! Welcome to CodeRunner!");
    }
}
        