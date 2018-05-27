import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * create by Cliven on 2018-05-27 22:15
 * @author Cliven
 */
public class UDPServer {

    public static void main(String[] args) throws IOException {
        String responseMsg = "Hello I am udp server";
        byte[] buf = new byte[1024];
        // 监听3000端口
        DatagramSocket datagramSocket = new DatagramSocket(3000);
        // 打包收到消息的
        DatagramPacket receivedDatagramPacket = new DatagramPacket(buf, 1024);
        System.out.println("Server up,waiting from client msg...");
        while (true){
            // 从监听端口接收数据，该方法会导致线程阻塞
            datagramSocket.receive(receivedDatagramPacket);
            System.out.println("Received msg:");
            StringBuilder receivedMsgBuilder = new StringBuilder();
            receivedMsgBuilder.append(new String (receivedDatagramPacket.getData(),0, receivedDatagramPacket.getLength()))
                    .append(" message from -> ")
                    .append(receivedDatagramPacket.getAddress().getHostAddress())
                    .append(":")
                    .append(receivedDatagramPacket.getPort());

            System.out.println(">>>> " + receivedMsgBuilder.toString());

            // 响应数据到客户端的3000端口
            DatagramPacket responsePacket = new DatagramPacket(responseMsg.getBytes(), responseMsg.length(), receivedDatagramPacket.getAddress(), 9000);
            datagramSocket.send(responsePacket);
            //由于receivedDatagramPacket在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            //所以这里要将dp_receive的内部消息长度重新置为1024
            receivedDatagramPacket.setLength(1024);
        }
        //datagramSocket.close();
    }
}
