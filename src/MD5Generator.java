import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
    public static String
    getMD5(String input) {
        try {
            // Obtener una instancia del algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convertir la cadena a un arreglo de bytes
            byte[] messageDigest = md.digest(input.getBytes());

            // Convertir el arreglo de bytes a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));

            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException
                e) {
            e.printStackTrace();
        }
        return "";
    }

    /*public static void main(String[] args)
    {
        String password = "mysecretpassword";
        String md5 = getMD5(password);
        System.out.println("MD5 hash: " + md5);
    }*/
}