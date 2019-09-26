package prueba;

import java.io.*;

public class Postfix {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        Parser parse = new Parser(reader, writer);
        parse.expr();

        writer.close(); // Close and flush output stream writer.

        String output = outputStream.toString();
        System.out.println("Output generated by parser: " + output);
    }
}