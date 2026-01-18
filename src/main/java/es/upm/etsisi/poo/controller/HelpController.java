package es.upm.etsisi.poo.controller;

public class HelpController {

    String[] commands;

    public HelpController(){
         commands = new String[]{
                 "◦ Client/Cash:",
                 "   client add \"<name>\" (<DNI>|<NIF>) <email> <cashId>",
                 "   client remove (<DNI>|<NIF>)",
                 "   client list",
                 "   cash add [<id>] \"<name>\" <email>",
                 "   cash remove <id>",
                 "   cash list",
                 "   cash tickets <id>",

                 "◦ Ticket:",
                 "   ticket new [<id>] <cashId> <userId> -[c|p|s] (default -p option)",
                 "   ticket add <ticketId> <cashId> <prodId> <amount> [--p<txt> --p<txt>]",
                 "   ticket remove <ticketId> <cashId> <prodId>",
                 "   ticket print <ticketId> <cashId>",
                 "   ticket list",

                 "◦ Product:",
                 "   prod add [<id>] \"<name>\" <category> <price> [<maxPers>] || \"<name>\" <category>",
                 "   prod update <id> NAME|CATEGORY|PRICE <value>",
                 "   prod addFood [<id>] \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>",
                 "   prod addMeeting [<id>] \"<name>\" <price> <expiration: yyyy-MM-dd> <max_people>",
                 "   prod list",
                 "   prod remove <id>",

                 "◦ General:",
                 "   echo \"<texto>\"",
                 "   help",
                 "   exit"
         };
    }

    public void help(){
        System.out.println("Commands:");
        for (String cmd : commands) {
            System.out.println(" " + cmd);
        }

        System.out.println("\n" + "Categories: " +
                "MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS" +
                "\nDiscounts if there are ≥2 units in the category: " +
                "MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%"
        );
    }
}
