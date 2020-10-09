package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements Initializable {


  @FXML
  public TextArea textArea;
  @FXML
  public TextField textField;

  final String IP_ADDRESS = "LocalHost";
  final int PORT = 8189;
  Socket socket;
  DataInputStream in;
  DataOutputStream out;

  public void sendMsg(ActionEvent actionEvent) {
    try {
      if(textField.getText().trim().length() == 0) {
        return;
      }
      out.writeUTF(textField.getText());
      textField.clear();
      textField.requestFocus();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      socket = new Socket(IP_ADDRESS, PORT);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());

      new Thread(()->{
        try {
          while (true) {
            String str = in.readUTF();

            if(str.equals("/end")) {
              break;
            }

            textArea.appendText(str);

          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            socket.close();
            in.close();
            out.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }


      }).start();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
