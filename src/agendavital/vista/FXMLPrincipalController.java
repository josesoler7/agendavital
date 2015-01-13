package agendavital.vista;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;

/**
 * @author Enrique
 */
public class FXMLPrincipalController implements Initializable {

    //Se declaran los atributos de FXMLPrincipal, con el nombre del #id que tienen en JavaFX
    public static Stage ventanaRegistro;
    public static Stage ventanaMes;
    public static Stage ventanaNoticia;
    public static String fechaSeleccionada = null;

    @FXML
    private Menu menuInicio;
    @FXML
    private Button myButton;
    @FXML
    private Text textPrueba;
    @FXML
    private DatePicker cal;
     final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cal.setValue(LocalDate.now());

        Callback<DatePicker, DateCell> dayCellFactory =( DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                ArrayList<Pair<LocalDate, Noticia>> fechas = null;
                try {
                    fechas = Noticia.getNoticiasFecha();
                } catch (ConexionBDIncorrecta ex) {
                    Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Pair<LocalDate, Noticia> fecha : fechas) {
                    if (item.equals(fecha.getKey())) {
                        switch (fecha.getValue().getCategoria()) {
                            case "Noticias Internacionales":
                                setStyle("-fx-background-color: #A8F9FF");
                                break;
                            case "Noticias ESIanas":
                                setStyle("-fx-background-color: red");
                                break;
                            default:
                                setStyle("-fx-background-color: blue");
                                break;
                        }
                    }
                }
             }
        };
          
        

                StringConverter converter = new StringConverter<LocalDate>() {
                   

                    @Override
                    public String toString(LocalDate date) {
                        if (date != null) {
                            return dateFormatter.format(date);
                        } else {
                            return "";
                        }
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            LocalDate date = LocalDate.parse(string, dateFormatter);

                            if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusYears(1))) {
                                return cal.getValue();
                            } else {
                                return date;
                            }
                        } else {
                            return null;
                        }
                    }
                };

                cal.setDayCellFactory(dayCellFactory);
                cal.setConverter(converter);
                cal.setPromptText("dd-MM-yyyy");
            }

            @FXML
            public void pulsado() throws IOException {
                Parent root = null;
                fechaSeleccionada = dateFormatter.format(cal.getValue());
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Registro Completo");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNoticia.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaNoticia = new Scene(root);
                FXMLNoticiaController controller = loader.getController();
                ventanaNoticia.setScene(escenaNoticia);
                ventanaNoticia.initStyle(StageStyle.UNDECORATED);
                ventanaNoticia.show();
            }

            //Metodo para modificar acciones o estilos del menuInicio cuando pasas por encima con el ratón
            public void menuInicioEntered() throws IOException {
                menuInicio.setStyle("-fx-background-color:#43818c;");
            }

            public void textPrueba() throws IOException {
                textPrueba.setStyle("-fx-font-family: Prueba");
            }

            public void pacoEntered() throws IOException {
                myButton.setStyle(
                        "-fx-background-color:#43818c; -fx--moz-border-radius:15px; -fx-border:3px solid #181a18;"
                );
            }

            public void pacoExited() throws IOException {
                myButton.setStyle(
                        "-fx-background-color:white; -fx--moz-border-radius:15px; -fx-border:3px solid #181a18;"
                );
            }

            public void pacoClicked() throws IOException {
                myButton.setStyle(
                        "-fx-background-color:black; -fx--moz-border-radius:15px; -fx-border:3px solid #181a18;"
                );
            }

            public void paco() throws IOException {
                myButton.setStyle(
                        "-fx-background-color:black; -fx--moz-border-radius:15px; -fx-border:3px solid #181a18;"
                );
            }

            public void pacoDragged() throws IOException {
                myButton.setStyle(
                        "-fx-background-color:red; -fx--moz-border-radius:15px; -fx-border:3px solid #181a18;"
                );
            }
        }
