package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

  public static void main(String[] args) {
    DataInputStream in;
    DataOutputStream out;

    ServerSocket server = null; // серверный соккет
    Socket socket = null; // Пользовательский соккет
    final int PORT = 8189; // Серверный порт

    try {
      server = new ServerSocket(PORT); // Создаем объект сервер
      System.out.println("Server started");
      socket = server.accept(); // Блокирующий оператор. Который заставляет сервер ждать клиента типо потока чтоли также серверный порт выделит по этой команде соккет серверный и передаст в пользовательский
      System.out.println("Client connected");


      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      
      while (true) {
        String str = in.readUTF();

        if(str.equals("/end")) {
          break;
        }

        System.out.println("Client" + str);
        out.writeUTF("echo: " + str);
      }
      
      
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {

        server.close(); // Обязательно закрываем сервер в любом случае
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }




}
