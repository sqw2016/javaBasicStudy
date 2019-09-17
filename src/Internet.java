import java.io.*;
import java.lang.Thread;
import java.net.*;

/**
 * 网络编程：
 *      1、基本概念
 *          1、服务器：提供信息的计算机或程序。
 *          2、客户机：指请求信息的计算机或程序。
 *          3、网络：用于连接服务器与客户机，实现两者通信。
 *          4、局域网（Local Area NetWork, LAN）：一群通过一定形式连接起来的计算机。
 *          5、广域网（Wide Area NetWork, WAN）：由LAN延伸到更大范围。
 *          6、Internet就是由无数的LAN和WAN组成的。
 *          7、网络协议：规定了计算机之间的物理、机械（网线与网卡的连接规定）、电气（有效的电平范围）、等特征以及
 *                       计算机之间的相互寻址规则、数据发生冲突的解决、长的数据如何分段传送与接收等。
 *          8、IP地址：Internet网络为主机分配的Internet地址。
 *          9、IPv4：用4个字节表示的IP地址，取每个字节的十进制数，并用圆点分隔，就是IPv4。
 *          10、IPv6：用16个字节表示的IP地址，规则和IPv4类似。
 *          11、端口：一般而言，一台计算机只有单一的连到网络的物理连接（Physical Connection），所有的数据都是通过它
 *                    连接对内、对外送达特定的计算机。网络中的端口并不是真正的物理端口，是一个假象的连接装置。端口
 *                    被规定为一个0-65535之间的整数，客户机通过不同的端口来确定连接到服务器的哪项服务上，http一般
 *                    使用80端口，FTP一般使用21端口。
 *          12、套接字（Socket）：用于将应用程序与端口连接，也是一个假想的连接装置。
 *      2、常用的网络协议
 *          1、IP协议：Internet Protocol的简称，是一种网络协议。Internet网络采用的协议是TCP/IP协议栈，全称
 *                     Transmission Control Protocol/Internet Protocol。TCP/IP模式是一种层次结构，共分4层，分别为
 *                     应用层、传输层、互联网层和网络层。各层实现特定的功能，提供特定的服务和访问接口，具有相对的
 *                     独立性。TCP/IP协议栈中包含两个高级协议TCP和UDP。
 *          2、TCP协议：传输控制协议（Transmission Control Protocol）,是一种以固接连线为基础的协议，提供两台计算机
 *                      间可靠的数据传送。能够保证数据确实送达，而且抵达的数据的排列顺序和送出时的顺序相同。HTTP、
 *                      FTP和Telnet等都需要使用可靠的通信频道。但TCP协议在认证上存在额外的耗费，可能使传输速度减慢。
 *          3、UDP协议：用户数据报协议（User Datagram Protocol），无连接通信协议，不保证可靠数据的传输，能够向若干个
 *                      目标发送数据，接收发自若干个源的数据。UDP是以独立发送数据包的方式进行的。适合一些对数据准确
 *                      性要求不高，但对传输速度和实效性要求非常高的场景，如网络聊天室、在线影片等。但有的防火墙和
 *                      路由器会设置成不允许UDP数据包传输，所以在与到UDP连接问题时，先确定所在网络是否允许UDP协议。
 *
 *      3、TCP编程：是指利用Socket类编写通信程序。分为服务器程序和客户机程序。
 *          1、服务器与客户端交互过程：
 *              1、服务器创建一个ServerSocket（服务器套接字），调用accept()等待客户端来连接；
 *              2、客户端创建一个Socket，请求与服务器建立连接；
 *              3、服务器接收到客户端的连接器请求，同时创建一个新的Socket与客户端建立连接。服务器继续等待新的请求。
 *          2、操作类
 *              1、InetAddress：与IP地址相关的类，可以获取IP地址，主机地址等信息
 *                  1、getByName(String name)：获取与Host相对应的InetAddress对象。
 *                  2、getHostAddress()：获取主机的Ip地址。
 *                  3、getHostName()：获取主机名。
 *                  4、getLocalHost()：返回主机的InetAddress对象。
 *              2、ServerSocket：服务器套接字，主要功能是等待来自网络上的“请求”，服务器套接字一次可以与一个套接字
 *                               连接，如果多台客户端同时提出连接请求，服务器套接字会将请求连接的客户端存入队列中，
 *                               逐个处理。若请求连接数大于最大容纳数，多出的连接请求被拒绝，队列默认大小为50。
 *                  1、构造函数：都会抛出IOException
 *                      1、ServerSocket()：创建非绑定套接字；
 *                      2、ServerSocket(int port)：创建绑定到特定端口的服务器套接字；
 *                      3、ServerSocket(int port, int backlog)：创建指定backlog和绑定到特定端口的服务器套接字；
 *                      4、ServerSocket(int port, int backlog, InetAddress bindAddress)：使用指定端口、侦听backlog
 *                          和要绑定到的本地IP地址创建服务器，适用于计算机上有多块网卡和多个IP地址的情况。
 *                  2、常用方法：
 *                      1、accept：等待客户端的连接，若连接，则返回一个与客户端Socket相连接的Socket对象，服务器Socket
 *                          对象使用getOutputStream()获得输出流将指向客户端Socket对象使用getInputStream()方法获得的
 *                          输入流，同样客户端Socket通过getInputStream()获得客户端Socket使用getOutputStream获得的输出流。
 *                          accept()会阻塞线程的继续执行，知道接收到客户端的连接。如果客户端没有申请连接，accept也没有
 *                          阻塞，肯定是程序出了问题，通常是使用了一个被其他程序占用的端口号，ServerSocket没有绑定成功。
 *                      2、isBound：判断ServerSocket的绑定状态；
 *                      3、getInetAddress：返回此服务器套接字的InetAddress对象；
 *                      4、isClosed：返回服务器套接字的关闭状态；
 *                      5、close：关闭服务器套接字；
 *                      6、bind(SocketAddress endPoint)：将ServerSocket绑定到特定的地址(IP地址和端口)；
 *                      7、getInetAddress()：返回ServerSocket绑定的端口号。
 *      4、UDP编程
 *          1、UDP通信的基本模式：
 *              1、将数据打包成数据包，然后将数据包发往目的地；
 *              2、接收数据包，然后查看数据包；
 *          2、UDP程序的步骤：
 *              1、发送数据包：
 *                  1、使用DatagramSocket()创建一个数据包套接字；
 *                  2、使用DatagramPacket(byte[] buf, int offset, int length, InetAddress address, int port)创建要
 *                      发送的数据包；
 *                  3、使用DatagramSocket类的send()方法发送数据包；
 *              2、接收数据包：
 *                  1、DatagramSocket(int port)创建数据包套接字，绑定到指定的端口；
 *                  2、DatagramPacket(byte[] buf, int length)创建字节数组接收数据包；
 *                  3、DatagramPacket类的receive()方法接收UDP包。 receive()接收数据时，正常情况下将阻塞，一直等到
 *                      网络上有数据传来，如果没有数据发送过来，receive也没有阻塞，肯定程序有问题，多数是使用了一个
 *                      被其他程序占用的端口。
 *          3、相关类的介绍
 *              1、DatagramPacket：表示数据包
 *                  1、构造函数
 *                      1、DatagramPacket(byte[] buf, int length)：创建一个指定了内存空间和大小的数据包；
 *                      2、DatagramPacket(byte[] buf, int length, InetAddress address, int port)：创建一个指定内存空间
 *                          和大小，以及目标地址和端口的数据包。适用于发送数据包的情况。
 *              2、DatagramSocket：发送和接收数据包的套接字
 *                  1、构造函数
 *                      1、DatagramSocket()：构造数据报套接字，并将其绑定到主机上任何可用的端口；
 *                      2、DatagramSocket(int port)：构造绑定到指定端口的数据报套接字；
 *                      3、DatagramSocket(int port, InetAddress address)：构造绑定到指定端口和IP的数据报套接字，
 *                          适用主机有多块网卡和多个IP的情况。
 *      
 */

class MyTcp {
    final static String CRLF = "\r\n";
    private ServerSocket serverSocket;
    private Socket socket;
    BufferedReader reader;
    PrintWriter printWriter;
    void getServer() {
        try {
            serverSocket = new ServerSocket(8999);
            while (true) {
                System.out.println("等待客户端的连接：");
                socket = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                String inLine;
                while((inLine = reader.readLine()) != null) {
                    if (inLine.isEmpty()) {
                        break;
                    } else {
                        System.out.println("客户端：" + inLine);
                    }
                }

                /* 必须设置返回提的文件格式和编码 */
                printWriter.println("HTTP/1.0 200 OK");
                printWriter.println("Content-Type: text/html;charset=utf-8");
                printWriter.println(); // 不能少

                printWriter.write("服务器已接收到请求");
                printWriter.flush();
                printWriter.close();
                reader.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getClientMessage() {
        try {
            while (reader.readLine() != null) {
                System.out.println("客户端：" + reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyUdp extends Thread{
    String weather = "节目预报：八点有大型晚会，请收听";
    int port = 9999;
    InetAddress address = null;
    MulticastSocket socket = null; // 声明多点广播套接字
    MyUdp() { // 无参构造器
        try {
            address = InetAddress.getByName("224.255.10.0"); // 指定地址
            socket = new MulticastSocket(port); // 实例化多点广播套接字。
            socket.setTimeToLive(1); // 指定发送范围是本地网络
            /* 加入广播组，要广播或接受广播的主机地址必须加入到一个组内，地址范围
             * 224.0.0.0-224.255.255.255,这类地址不代表某个特定主机的位置，加入到同一个组的
             * 主机可以在某个端口上广播信息，也可以在某个端口上接收信息。
              * */
            socket.joinGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            DatagramPacket packet = null;
            byte data[] = weather.getBytes(); // 获取广播内容字节数组
            packet = new DatagramPacket(data, data.length, address, port); // 将数据打包
            System.out.println(new String(data));
            try {
                socket.send(packet); // 发送数据
                sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class Internet {
    public static void main(String[] args){
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            String localName = ip.getHostName();
            String localIp = ip.getHostAddress();
            System.out.println("本机名：" + localName);
            System.out.println("本机IP地址：" + localIp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // new MyTcp().getServer();
        new MyUdp().start();
    }
}
