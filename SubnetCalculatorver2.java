import java.util.Scanner;

public class SubnetCalculatorver2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("IP 주소를 입력하세요 (예: 192.168.0.1): ");
        String ipAddress = scanner.nextLine();

        System.out.print("서브넷 마스크를 입력하세요 (예: 255.255.255.0): ");
        String subnetMask = scanner.nextLine();

        String networkAddress = calculateNetworkAddress(ipAddress, subnetMask);
        String broadcastAddress = calculateBroadcastAddress(ipAddress, subnetMask);
        int numberOfHosts = calculateNumberOfHosts(subnetMask);

        System.out.println("네트워크 주소: " + networkAddress);
        System.out.println("브로드캐스트 주소: " + broadcastAddress);
        System.out.println("호스트 수: " + numberOfHosts);
    }

    private static String calculateNetworkAddress(String ipAddress, String subnetMask) {
        String[] ipParts = ipAddress.split("\\.");
        String[] maskParts = subnetMask.split("\\.");

        int[] networkParts = new int[4];
        for (int i = 0; i < 4; i++) {
            networkParts[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(maskParts[i]);
        }

        return String.join(".", 
                String.valueOf(networkParts[0]),
                String.valueOf(networkParts[1]),
                String.valueOf(networkParts[2]),
                String.valueOf(networkParts[3]));
    }

    private static String calculateBroadcastAddress(String ipAddress, String subnetMask) {
        String[] ipParts = ipAddress.split("\\.");
        String[] maskParts = subnetMask.split("\\.");

        int[] broadcastParts = new int[4];
        for (int i = 0; i < 4; i++) {
            broadcastParts[i] = (Integer.parseInt(ipParts[i]) & Integer.parseInt(maskParts[i]))
                    | (~Integer.parseInt(maskParts[i]) & 0xFF);
        }

        return String.join(".",
                String.valueOf(broadcastParts[0]),
                String.valueOf(broadcastParts[1]),
                String.valueOf(broadcastParts[2]),
                String.valueOf(broadcastParts[3]));
    }

    private static int calculateNumberOfHosts(String subnetMask) {
        String[] maskParts = subnetMask.split("\\.");
        int numberOfZeros = 0;

        for (String part : maskParts) {
            int mask = Integer.parseInt(part);
            while (mask > 0) {
                if ((mask & 1) == 0) {
                    numberOfZeros++;
                }
                mask >>= 1;
            }
        }

        return (int) Math.pow(2, numberOfZeros) - 2;
    }
}
