package es.upm.etsisi.poo.user;

public class UserValidator{

    public static boolean validName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean validEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean validCashId(String cashId) {
        return ((cashId != null && (cashId.matches("^UW\\d{7}$"))));
    }

    public static boolean validDNI(String dni) {
        if (dni == null) return false;
        dni = dni.toUpperCase();
        if (!dni.matches("\\d{8}[A-Z]")) {
            return false;
        }
        String[] letters = {
                "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X",
                "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"
        };
        int num = Integer.parseInt(dni.substring(0, 8));
        String correctLetter = letters[num % 23];
        String DNIletter = dni.substring(8);

        return DNIletter.equals(correctLetter);
    }

    public static boolean validNIE(String nie) {
        if (nie == null) return false;
        nie = nie.toUpperCase();
        if (!nie.matches("^[XYZ][0-9]{7}[A-Z]$")) {
            return false;
        }
        char first = nie.charAt(0);
        String number = switch (first) {
            case 'X' -> "0";
            case 'Y' -> "1";
            case 'Z' -> "2";
            default  -> "";
        };
        number += nie.substring(1, 8);
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int num = Integer.parseInt(number);
        char correctLetter = letters.charAt(num % 23);
        return correctLetter == nie.charAt(8);
    }

    public static boolean validNIF(String nif) {
        if (nif == null || nif.length() != 9 || !nif.matches("^[ABCDEFGHJNPQRSUVW][0-9]{7}[0-9A-J]$")) {
            return false;
        }else{
            return true;
        }
    }

}
