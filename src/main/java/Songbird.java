public class Songbird {
    public static void main(String[] args) {
        greet();
    }

    private static void greet() {
        String logo = """
                   _____                   __    _          __
                  / ___/____  ____  ____ _/ /_  (_)________/ /
                  \\__ \\/ __ \\/ __ \\/ __ `/ __ \\/ / ___/ __  /
                 ___/ / /_/ / / / / /_/ / /_/ / / /  / /_/ /
                /____/\\____/_/ /_/\\__, /_.___/_/_/   \\__,_/
                                 /____/""";
        System.out.println(logo);
        System.out.println("Songbird(TM) AI by VariTech Heavy Industries, (C) 3025. All rights reserved.");
        System.out.println("--------------------");
        System.out.println("S> How can I help?");
        System.out.println();
        System.out.println("S> Goodbye.");

    }
}
