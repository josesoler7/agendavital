/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAnadirMomentoController implements Initializable {
     //////////////Variables de la ventana de registro//////////////
        public static final double ANCHO = 596;
	public static final double ALTO= 488;
        private double initX=ANCHO/2;
        private double initY=ALTO/2;    
        //------------------------------------------------------------//
    @FXML
    private Circle circulomin;
    @FXML
    private Line lineamin;
    @FXML
    private Circle circulocerr;
    @FXML
    private Line lineacerrar1;
    @FXML
    private Line lineacerrar2;
        
    @FXML
    private AnchorPane anclaje;
    @FXML
    private ComboBox<?> cbcategoria;
    @FXML
    private Button butregistrarse;
    @FXML
    private TextField t1;
    @FXML
    private TextField t2;
    @FXML
    private TextField t3;
    @FXML
    private TextField t4;
    @FXML
    private TextArea descripcion;
     @FXML
    private TextField titular;
    @FXML
    private DatePicker cal;
    @FXML
    private ColorPicker color;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
                ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLPrincipalController.ventanaNoticia.setIconified(true);
    }

    @FXML
    public void minimizarEncima() throws IOException {
        circulomin.setFill(Color.web("#D7F2E8"));
    }

    @FXML
    public void minimizarSalida() throws IOException {
        circulomin.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void cerrar() throws IOException
    {
        FXMLPrincipalController.ventanaNoticia.close();
    }
    
    @FXML
    public void cerrarEncima() throws IOException
    {
       circulocerr.setFill(Color.web("#D7F2E8"));
    }
    
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
///////////////////////////////////////////////////////////
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cal.setValue(LocalDate.now());
         Callback<DatePicker, DateCell> dayCellFactory =( DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if(item.isAfter(LocalDate.now())) setDisable(true);
            }
         };
         cal.setDayCellFactory(dayCellFactory);
    }    

    @FXML
    private void registra_usuario(ActionEvent event) {
    }

    @FXML
    public void registra_momento() throws ConexionBDIncorrecta{
        String _titular = titular.getText();
        String _descripcion = descripcion.getText();
        ArrayList<String> tags = new ArrayList<>();
        if(!t1.getText().isEmpty()) tags.add(t1.getText());
        if(!t2.getText().isEmpty()) tags.add(t2.getText());
        if(!t3.getText().isEmpty()) tags.add(t3.getText());
        if(!t4.getText().isEmpty()) tags.add(t4.getText());
        String _fecha = dateFormatter.format(cal.getValue());
        String _color = color.getPromptText();
        Momento momento = Momento.insert(_titular, _fecha, _descripcion, _color);
        
    }
    
    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLPrincipalController.ventanaNoticia.setX(me.getScreenX() - initX);
            FXMLPrincipalController.ventanaNoticia.setY(me.getScreenY() - initY);
        });
    }
    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLPrincipalController.ventanaNoticia.getX();
            initY = me.getScreenY() - FXMLPrincipalController.ventanaNoticia.getY();
        });
    }

}
