/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ramón
 */
public class UtilidadesLogin {

    public static boolean nickBienIntroducido(String _nick) throws SQLException, ConexionBDIncorrecta {
        return UtilidadesRegistro.nickYaExiste(_nick);
    }

    public static boolean contrasenaBienIntroducida(String _nick, String _constrasena) throws SQLException, ConexionBDIncorrecta {

        ResultSet rs = null;
        try (Connection conexion = ConfigBD.conectar()) {
            String consultaContrasena = String.format("SELECT contrasena from usuarios"
                    + " WHERE nick = %s;", ConfigBD.String2Sql(_nick, false));
            rs = conexion.createStatement().executeQuery(consultaContrasena);
            rs.next();
            String contrasenaEncontrada = rs.getString("contrasena");
            String contrasenaCifrada = UtilidadesRegistro.getStringMessageDigest(_constrasena);
            return contrasenaCifrada.equals(contrasenaEncontrada);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }

}
