package lab2; // Group of related classes

import javax.imageio.ImageIO; // for reading and writing images
import javax.swing.*; // for GUI components like JFrame and JLabel
import java.awt.*; // for user interfaces and graphics
import java.io.IOException; // Handles input/output exceptions
import java.io.InputStream; // For reading byte data
import java.net.URI; // Represents a Uniform Resource Identifier
import java.net.http.HttpClient; // For sending HTTP requests
import java.net.http.HttpRequest; // handles an HTTP request
import java.net.http.HttpResponse; // handles an HTTP response

public class AvatarGenerator { // AvatarGenerator class in the 'lab2' package. This class generates and displays random avatars

    public static void main(String[] args) { // this is the entry point for running the program

        try {

            var avatarStream = AvatarGenerator.getRandomAvatarStream(); // Call to getRandomAvatarStream to fetch a random avatar
            // avatarString returns an InputStream, object reference type
            AvatarGenerator.showAvatar(avatarStream); // Call to showAvatar with the fetched InputStream to display the avatar
        } catch (IOException | InterruptedException e) { // Catch IOException or InterruptedException
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    public static InputStream getRandomAvatarStream() throws IOException, InterruptedException {
        // generates a random avatar stream. Throws IOException and InterruptedException

        String[] styles = { "adventurer", "adventurer-neutral", "avataaars", "big-ears", "big-ears-neutral", "big-smile"
                , "bottts", "croodles", "croodles-neutral", "fun-emoji", "icons", "identicon", "initials", "lorelei",
                "micah", "miniavs", "open-peeps", "personas", "pixel-art", "pixel-art-neutral" }; // styles is an array of strings
        // Randomly selects one one avatar style from the array "styles"
        var style = styles[(int)(Math.random() * styles.length)]; // "style" is a String

        // Generates the random seed for the avatar HTTP request
        var seed = (int)(Math.random() * 10000); // 'seed' is a primitive type, integer value

        // Create an HTTP request URI to fetch a random avatar with a seed
        var uri = URI.create("https://api.dicebear.com/9.x/%s/png?seed=%d".formatted(style, seed));
        // uri returns a java.net.URI type, reference type

        // Create an HttpRequest object with the generated URI
        var request = HttpRequest.newBuilder(uri).build();
        // request returns a java.net.http.HttpRequest type, reference type

        // send the HTTP request and get the avatar image as InputStream
        try (var client = HttpClient.newHttpClient()) { // create a new HttpClient instance to send the HTTP request
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());// sends the request and receives the response as an InputStream
            //repose returns a java.net.http.HttpResponse<java.io.InputStream>, reference type
            return response.body(); // returns the response body with InputStream containing the avatar image
        }
    }

    public static void showAvatar(InputStream imageStream) { // 'imageStream' is an InputStream parameter, so it is a reference type
        JFrame frame = new JFrame("PNG Viewer"); // Creates JFrame object with a title "PNG Viewer"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sets the default close operation for the frame (exit the application)
        frame.setResizable(false); // Makes the frame non-resizable
        frame.setSize(200, 200); // Sets the window size to 200x200 pixels
        frame.getContentPane().setBackground(Color.BLACK); // Sets the background color of thw window to black

        try { // try block for exception/error handling
            // Loads the image from the InputStream
            Image image = ImageIO.read(imageStream); // ImageIO.read reads the image and returns an Image object (java.awt.Image)

            // Create a JLabel to display the loaded image
            JLabel imageLabel = new JLabel(new ImageIcon(image)); // Creates a new JLabel with the ImageIcon wrapping the Image
            frame.add(imageLabel, BorderLayout.CENTER); // Adds imageLabel to the center of window

        } catch (IOException e) { // catch any IOException that occurs during image loading
            e.printStackTrace(); // Print the stack trace for debugging
        }

        frame.setVisible(true); // this makes the frame visible for the user
    }
}
