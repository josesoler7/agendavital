/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ramón
 */
public class UtilidadesRegistro {

    /**
     * *
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     *
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private static String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /**
     * *
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje
     * usando MD5.
     *
     * @param contrasena contrasena a encriptar
     * @return mensaje encriptado
     */
    public static String getStringMessageDigest(String contrasena) {
        byte[] digest = null;
        byte[] buffer = contrasena.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
    }

    public static boolean nickYaExiste(String _nick) throws SQLException {
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = ConfigBD.conectar();
            String consultaNick = String.format("SELECT (nick) from usuarios WHERE nick = %s;", ConfigBD.String2Sql(_nick, false));
            rs = conexion.createStatement().executeQuery(consultaNick);
            rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
        return (rs.getRow() == 1);
    }

    public static boolean nickLongitudValida(String _nick) {
        return _nick.length() >= 5;
    }

    public static int anyoBisiesto(int _anyo) {
        return (_anyo % 4 == 0 && (_anyo % 400 == 0 || _anyo % 100 != 0)) ? 1 : 0;
    }

    public static boolean contrasenaLongitudValida(String _contrasena) {
        return _contrasena.length() >= 7;
    }

    public static boolean contrasenaHayMayuscula(String _contrasena) {
        for (int i = 0; i < _contrasena.length(); i++) {
            if (Character.isUpperCase(_contrasena.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean contrasenaHayNumeros(String _contrasena) {
        for (int i = 0; i < _contrasena.length(); i++) {
            if (Character.isDigit(_contrasena.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean contrasenaHayCaracteresRaros(String _contrasena) {
        for (int i = 0; i < _contrasena.length(); i++) {
            int ascii = (int) (_contrasena.charAt(i));
            if (ascii > 126 || ascii < 32) {
                return true;
            }
        }
        return false;
    }

    public static boolean IntroduccionContrasena(String _contrasena, String _contrasena2) {
        return _contrasena.equals(_contrasena2);
    }

    public static boolean FechaValida(String _fecha) {
        int _anyo = Integer.parseInt(_fecha.substring(6, 10));
        int[] diasmes = {31, 28 + anyoBisiesto(_anyo), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int _mes = Integer.parseInt(_fecha.substring(3, 5));
        int _dia = Integer.parseInt(_fecha.substring(0, 2));
        return _dia <= diasmes[_mes + 1];
    }
}
