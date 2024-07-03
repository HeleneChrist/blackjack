import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class Spieler {

    private static Stack<Card> hand;
    private static int money;
    private static int bet;
    public static int port;
    public static String ip;
    private static String name;
    private static String croupierIP;
    private static int croupierPort;
    private static boolean split = false;
    private static boolean isWaiting = false;

    private static void play(String move){
        if (isWaiting){
            System.out.println("You have to wait for the croupier to respond.");
            return;
        }
        if (hand.isEmpty()){
            sendLines(croupierIP, croupierPort, move + " " + name + " " + "0" + " " + "0");
        }
        sendLines(croupierIP, croupierPort, move + " " + name + " " + hand.peek().getDeck()+ " " + hand.peek().toString());
        isWaiting = true;

    }

    private static void bet(int bet){
        if (bet > money){
            System.out.println("You don't have enough money.");
            return;
        }
        if (bet < 0){
            System.out.println("You can't bet a negative amount.");
            return;
        }
        if (bet == 0){
            System.out.println("You can't bet 0.");
            return;
        }
        if(bet > money/2){
            System.out.println("Are you sure you want to bet more than half of your money? (yes/no)");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input = br.readLine();
                if (input.equals("no")){
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
        sendLines(croupierIP, croupierPort, "bet " + name + " " + bet);
        Spieler.bet = Spieler.bet + bet;
    }

    public static boolean isIP(String ip) { // Checks if String is valid IPv4 address
        String[] parts = ip.split("\\."); // Split by dot
        if (parts.length != 4) { return false; } // Must be 4 chunks
        for (String p : parts) { // Check if numbers are valid
            try {
                int number = Integer.parseInt(p);
                if (number < 0 || number > 255) { return false; }
            } catch (NumberFormatException e) { return false; }
        }
        return true;
    }

    public static boolean isPort(String port) {
        try {
            int number = Integer.parseInt(port);
            if (number < 0 || number > 65535) { return false; }
        } catch (NumberFormatException e) { return false; }
        return true;
    }

    public static void main(String[] args){
        if (args.length != 3) {
            System.out.println("Arguments: \"<ip adress> <port number> <player name>\"");
        }
        if (!isIP(args[0])) {
            System.out.println("Invalid IP address");
        } else {
            ip = args[0];
        }
        if (!isPort(args[1])) {
            System.out.println("Invalid port number");
        } else {
            port = Integer.parseInt(args[1]);
        }
        name = args[2];
        money = 50;
        hand = new Stack<>();
        bet = 0;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (!(input = br.readLine()).equals("exit")) {
                new Thread(() -> receiveLines(port)).start();
                String[] parts = input.split(" ");
                if (parts[0].equalsIgnoreCase("registerPlayer") && parts.length == 3) {
                    if (isIP(parts[1]) && isPort(parts[2])) {
                        croupierIP = parts[1];
                        croupierPort = Integer.parseInt(parts[2]);
                        sendLines(croupierIP, croupierPort, "registerPlayer " + ip + " " + port + " " + name);
                    }
                } else if (parts[0].equalsIgnoreCase("hit")) {
                    play("hit");
                } else if (parts[0].equalsIgnoreCase("stand")) {
                    play("stand");
                } else if (parts[0].equalsIgnoreCase("doubleDown")) {
                    play("doubleDown");
                } else if (parts[0].equalsIgnoreCase("split")) {
                    if (!split){
                        split = true;
                        play("split");
                    }
                    else {
                        System.out.println("You can't split again.");
                    }
                } else if (parts[0].equalsIgnoreCase("surrender")) {
                    play("surrender");

                } else if (parts[0].equalsIgnoreCase("bet")) {
                    bet(Integer.parseInt(parts[1]));
                } else if (parts[0].equalsIgnoreCase("hand")) {
                    System.out.println(hand.toString());
                } else if (parts[0].equalsIgnoreCase("money")) {
                    System.out.println("You have: " + money);
                    System.out.println("You have bet: " + bet);
                } else {
                    System.out.println("Invalid input.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void sendLines(String friend, int friends_port, String message) {
        try (DatagramSocket s = new DatagramSocket()) { // closes automatically
            InetAddress ip = InetAddress.getByName(friend);
            byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket p = new DatagramPacket(buffer, buffer.length, ip, friends_port);
            s.send(p);
            System.out.println("Message sent.");
        } catch (IOException e) {
            System.err.println("Unable to send message to \"" + friend + "\".");
        }
    }

    private static void receiveLines(int port){
        try (DatagramSocket s = new DatagramSocket(port)) {
            byte[] buffer = new byte[1024];
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            s.receive(p);
            isWaiting = false;
            String received = new String(p.getData(), 0, p.getLength(), StandardCharsets.UTF_8);
            System.out.println(received);
            if (received.contains("action accepted")){
                receiveLines(port);
            }
            else if (received.contains("gameover")){
               hand = new Stack<>();
               split = false;
               bet = 0;
               sendLines(croupierIP, croupierPort, "gameover " + name);
            }
            else if (received.contains("prize")){
                money = money + Integer.parseInt(received.split(" ")[1]);
                bet = 0;
                hand = new Stack<>();
                split = false;
                sendLines(croupierIP, croupierPort, "prize accepted " + name);
            }
            else if (received.contains(name)){
                hand.push(Card.fromJSON(received));
                System.out.println("You received a card: " + hand.peek().toString());
                sendLines(croupierIP, croupierPort, "payer " + name + "received " + hand.peek().getDeck() + " " + hand.peek().toString());
            }
        } catch (IOException e) {
            System.err.println("Unable to receive message.");
        }
    }

}
