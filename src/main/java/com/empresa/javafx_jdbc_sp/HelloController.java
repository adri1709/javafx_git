package com.empresa.javafx_jdbc_sp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField nombre;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField facturacion;

    @FXML
    protected void onHelloButtonClick() {
        //welcomeText.setText("Welcome to JavaFX Application!");
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = java.sql.DriverManager.getConnection(url, user, password);
            //String insertar = "INSERT INTO clientesfx (nombre, ciudad, facturacion) VALUES (?, ? ,?)";
            String insertarsp= "CALL sp_add_clientesfx(?, ?, ?)";
            CallableStatement cs = conexion.prepareCall(insertarsp);
            //PreparedStatement ps = conexion.prepareStatement(insertarsp);
            cs.setString(1, nombre.getText());
            cs.setString(2, ciudad.getText());
            cs.setDouble(3, Double.parseDouble(facturacion.getText()));

            int filas = cs.executeUpdate();
            System.out.println("Registros guardados: " + filas);
            welcomeText.setText("Cliente guardado");

            nombre.clear();
            ciudad.clear();
            facturacion.clear();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}