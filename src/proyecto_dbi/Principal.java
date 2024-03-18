/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto_dbi;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import javax.swing.JFrame;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joeds
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() throws ClassNotFoundException, SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);

        JP_PropiedadesVendidas.setVisible(false);
        JP_PropiedadesEnMercadoVendedordor.setVisible(false);
        JP_PropiedadesEnMercadoComprador.setVisible(false);
        JP_CrearPropiedad.setVisible(false);
        JP_CrearUsuario1.setVisible(false);
        JP_EliminarPropiedad.setVisible(false);
        JP_EliminarUsuario1.setVisible(false);
        JP_ModPropiedad.setVisible(false);
        JP_ModUsuario1.setVisible(false);
        cargarImagenes();

    }

    public Connection conectarMySQL() throws ClassNotFoundException, SQLException {
        Connection con = null;
        String sURL = "jdbc:mysql://54.165.109.252:3306/bienes_raices_db"; // URL PARA CONEXION
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // DRIVER
            con = (Connection) DriverManager.getConnection(sURL, "remoto", "Joed123_"); // instancia de conexion
            return con;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexion, vuelva a intentarlo!"); // mensaje de error
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());

        }
    }

    public void cargarVista1() {
        try {
            Connection con = conectarMySQL(); // instancia de conexion 

            String sql = "SELECT * FROM ventas_por_agente"; // query mandado a la base de datos para llamar a la vista creada correspondiente 
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("No. de Identidad del Agente"); // encabezados de las tablas
            model.addColumn("Cantidad de Ventas"); // encabezados de las tablas 
            while (rs.next()) {
                String noIdentidadAgente = rs.getString("noIdentidad_Agente"); // agarra campo desde la base
                int cantidadVentas = rs.getInt("cantidad_ventas"); // agarra campo desde la base 
                model.addRow(new Object[]{noIdentidadAgente, cantidadVentas}); // setea la informacion obtenida de la base en las filas de las Jtablas 
            }
            JT_Vista1.setModel(model); // setea el modelo a la Jtabla especifica

            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        }
    }

    public void cargarVista2() throws ClassNotFoundException, SQLException {
        Connection con = conectarMySQL();
        String sql = "SELECT * FROM ventas_por_vendedor";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No. de Identidad del Vendedor");
        model.addColumn("Cantidad de Ventas");

        while (rs.next()) {
            String noIdentidadVendedor = rs.getString("noIdentidad_Vendedor");
            int cantidadVentas = rs.getInt("cantidad_ventas");

            // Añadir fila al modelo
            model.addRow(new Object[]{noIdentidadVendedor, cantidadVentas});
        }

        // Asignar el modelo a la tabla JT_Vista2
        JT_Vista2.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    } // Hace lo mismo que el metodo cargarVista1()

    public void cargarVista3() {
        try {
            Connection con = conectarMySQL();

            String sql = "SELECT * FROM compras_por_comprador";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("No. de Identidad del Comprador");
            model.addColumn("Cantidad de Compras");
            while (rs.next()) {
                String noIdentidadAgente = rs.getString("noIdentidad_Comprador");
                int cantidadVentas = rs.getInt("cantidad_compras");
                model.addRow(new Object[]{noIdentidadAgente, cantidadVentas});
            }
            JT_Vista3.setModel(model);

            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        }
    }// Hace lo mismo que el metodo cargarVista1()

    public void cargarVista4() throws ClassNotFoundException, SQLException {
        // Conexión a la base de datos
        Connection con = conectarMySQL();
        // Consulta SQL para seleccionar datos de la vista
        String sql = "SELECT * FROM ventas_por_ubicacion";

        // Crear un objeto Statement
        Statement stmt = con.createStatement();

        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet rs = stmt.executeQuery(sql);

        // Crear el DefaultTableModel y definir columnas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ciudad");
        model.addColumn("Cantidad de Ventas");

        // Procesar los resultados y añadir filas al modelo
        while (rs.next()) {
            // Obtener los valores de cada columna
            String ciudad = rs.getString("ciudad");
            int cantidadVentas = rs.getInt("cantidad_ventas");

            // Añadir fila al modelo
            model.addRow(new Object[]{ciudad, cantidadVentas});
        }

        // Asignar el modelo a la tabla JT_Vista3
        JT_Vista4.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    }// Hace lo mismo que el metodo cargarVista1()

    public void cargarVista5() throws ClassNotFoundException, SQLException {
        // Conexión a la base de datos
        Connection con = conectarMySQL();

        // Consulta SQL para seleccionar datos de la vista
        String sql = "SELECT * FROM ventas_por_precio";

        // Crear un objeto Statement
        Statement stmt = con.createStatement();

        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet rs = stmt.executeQuery(sql);

        // Crear el DefaultTableModel y definir columnas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Precio");
        model.addColumn("Cantidad de Ventas");

        // Procesar los resultados y añadir filas al modelo
        while (rs.next()) {
            // Obtener los valores de cada columna
            double precio = rs.getDouble("precio");
            int cantidadVentas = rs.getInt("cantidad_ventas");

            // Añadir fila al modelo
            model.addRow(new Object[]{precio, cantidadVentas});
        }

        // Asignar el modelo a la tabla JT_Vista4
        JT_Vista5.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    }// Hace lo mismo que el metodo cargarVista1()

    public void cargarVista6() throws ClassNotFoundException, SQLException {

        // Conexión a la base de datos
        Connection con = conectarMySQL();

        // Consulta SQL para seleccionar datos de la vista
        String sql = "SELECT * FROM ventas_por_caracteristicas";

        // Crear un objeto Statement
        Statement stmt = con.createStatement();

        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet rs = stmt.executeQuery(sql);

        // Crear el DefaultTableModel y definir columnas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Características");
        model.addColumn("Cantidad de Ventas");

        // Procesar los resultados y añadir filas al modelo
        while (rs.next()) {
            // Obtener los valores de cada columna
            String caracteristicas = rs.getString("caracteristicas");
            int cantidadVentas = rs.getInt("cantidad_ventas");

            // Añadir fila al modelo
            model.addRow(new Object[]{caracteristicas, cantidadVentas});
        }

        // Asignar el modelo a la tabla JT_Vista5
        JT_Vista6.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    }// Hace lo mismo que el metodo cargarVista1()

    public void cargarVista7() throws ClassNotFoundException, SQLException {
        // Conexión a la base de datos
        Connection con = conectarMySQL();

        // Consulta SQL para seleccionar datos de la vista
        String sql = "SELECT * FROM agente_max_ventas";

        // Crear un objeto Statement
        Statement stmt = con.createStatement();

        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet rs = stmt.executeQuery(sql);

        // Crear el DefaultTableModel y definir columnas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No. de Identidad del Agente");
        model.addColumn("Cantidad de Ventas");

        // Procesar los resultados y añadir filas al modelo
        while (rs.next()) {
            // Obtener los valores de cada columna
            String noIdentidadAgente = rs.getString("noIdentidad_Agente");
            int cantidadVentas = rs.getInt("cantidad_ventas");

            // Añadir fila al modelo
            model.addRow(new Object[]{noIdentidadAgente, cantidadVentas});
        }

        // Asignar el modelo a la tabla JT_Vista6
        JT_Vista7.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    }// Hace lo mismo que el metodo cargarVista1()

    public void cargarVista8() throws ClassNotFoundException, SQLException {
        // Conexión a la base de datos
        Connection con = conectarMySQL();

        // Consulta SQL para seleccionar datos de la vista
        String sql = "SELECT * FROM promedio_por_agente";

        // Crear un objeto Statement
        Statement stmt = con.createStatement();

        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet rs = stmt.executeQuery(sql);

        // Crear el DefaultTableModel y definir columnas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No. de Identidad del Agente");
        model.addColumn("Precio Venta Promedio");
        model.addColumn("Tiempo Promedio en Mercado");

        // Procesar los resultados y añadir filas al modelo
        while (rs.next()) {
            // Obtener los valores de cada columna
            String noIdentidadAgente = rs.getString("noIdentidad_Agente");
            double precioVentaPromedio = rs.getDouble("precio_venta_promedio");
            double tiempoPromedioEnMercado = rs.getDouble("tiempo_promedio_en_mercado");

            // Añadir fila al modelo
            model.addRow(new Object[]{noIdentidadAgente, precioVentaPromedio, tiempoPromedioEnMercado});
        }

        // Asignar el modelo a la tabla JT_Vista7
        JT_Vista8.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    }// Hace lo mismo que el metodo cargarVista1()

    public void cargarBitacora() throws ClassNotFoundException, SQLException {
        // Conexion  a la base de datos
        Connection con = conectarMySQL();

        // Consulta SQL para seleccionar todos los registros de la tabla bitacora
        String sql = "SELECT * FROM bitacora";

        // Crear un objeto Statement
        Statement stmt = con.createStatement();

        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet rs = stmt.executeQuery(sql);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID BITACORA");
        model.addColumn("Tabla");
        model.addColumn("Acción");
        model.addColumn("Fecha");

        while (rs.next()) {
            // Obtener los valores de cada columna
            int id = rs.getInt("idbitacora");
            String tabla = rs.getString("tabla");
            String accion = rs.getString("accion");
            java.sql.Timestamp fecha = rs.getTimestamp("fecha");

            // Añadir fila al modelo
            model.addRow(new Object[]{id, tabla, accion, fecha});
        }

        // Asignar el modelo a la tabla JT_Bitacora
        JT_Bitacora.setModel(model);

        // Cerrar la conexión y liberar recursos
        rs.close();
        stmt.close();
        con.close();
    }

    public String getHash(final String input) throws NoSuchAlgorithmException {

        // Crear un objeto MessageDigest para SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // Calcular el hash para la cadena de entrada
        byte[] hashBytes = digest.digest(input.getBytes());
        // Convertir el arreglo de bytes del hash a una representación hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        // Imprimir el hash SHA-256 en formato hexadecimal
        return hexString.toString();
    }


    public String[] getLogin(final String user, final String pass) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        String[] responseLogin = new String[4]; // agarra tamaño del array para agregar el noIdentidad y el rol
        responseLogin[0] = "-1";
        Connection con = conectarMySQL(); 
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT id, rol FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?"; // agarra los usuarios y contrasneas
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, getHash(pass));
            rs = stmt.executeQuery();
            while (rs.next()) {
                responseLogin[1] = rs.getString("rol");
                responseLogin[0] = "1";
                this.rolUsuarioSesion = rs.getString("rol");
                int idUsuario = rs.getInt("id");
                this.noIdentidadUsuarioSesion = getNoIdentidadUsuario(idUsuario, rolUsuarioSesion); // agarra los No de identidades para identificar al tipo de persona y como se mostraran las pantallas 
                responseLogin[2] = Integer.toString(this.noIdentidadUsuarioSesion);
                responseLogin[3] = this.rolUsuarioSesion;
            }
        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
            responseLogin[0] = "Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage();
            responseLogin[1] = "-1";
            responseLogin[2] = "-1";
            responseLogin[3] = "";
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return responseLogin;
    }

    public void cargarPropiedades(String rol, String estado) throws ClassNotFoundException, SQLException {
        int noIdentidadUsuario = this.noIdentidadUsuarioSesion;
        Connection con = conectarMySQL();
        String sql = "";
        DefaultTableModel model = new DefaultTableModel();
        switch (rol) { // switch case para mostrar sus respectivas propiedades ya sean: vendidas, asignadas o compradas dependiendo del usuario 
            case "agente":
                if (estado.equals("Vendidas")) {
                    sql = "SELECT * FROM propiedades_vendidas WHERE noIdentidad_Agente = ?";
                    model = cargarPropiedadesAgente(sql, noIdentidadUsuario);
                    JT_PropiedadesVendidasAgente.setModel(model);
                } else if (estado.equals("En Mercado")) {
                    sql = "SELECT * FROM propiedades_en_mercado WHERE noIdentidad_Agente = ?";
                    model = cargarPropiedadesAgente(sql, noIdentidadUsuario);
                    JT_PropiedadesVendidasAgente.setModel(model);
                }
                break;
            case "vendedor":
                if (estado.equals("Vendidas")) {
                    sql = "SELECT * FROM propiedades_vendidas WHERE noIdentidad_Vendedor = ?";
                    model = cargarPropiedadesVendedor(sql, noIdentidadUsuario);
                    JT_PropiedadeEnMercadoVendedor.setModel(model);
                } else if (estado.equals("En Mercado")) {
                    sql = "SELECT * FROM propiedades_en_mercado WHERE noIdentidad_Vendedor = ?";
                    model = cargarPropiedadesVendedor(sql, noIdentidadUsuario);
                    JT_PropiedadeEnMercadoVendedor.setModel(model);

                }
                break;
            case "comprador":
                if (estado.equals("Vendidas")) {
                    sql = "SELECT * FROM propiedades_vendidas WHERE noIdentidad_Comprador = ?";
                    model = cargarPropiedadesComprador(sql, noIdentidadUsuario);
                    JT_PropiedadesEnMercadoComprador.setModel(model);
                }
                break;
            default:
                return; // Si el rol no es ninguno de los esperados, sale del método
        }
    }

    private DefaultTableModel cargarPropiedadesAgente(String sql, int noIdentidadUsuario) throws SQLException, ClassNotFoundException {
        Connection con = conectarMySQL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, noIdentidadUsuario);
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel();
        // Agregar columnas al modelo de la tabla...
        model.addColumn("ID Propiedad");
        model.addColumn("Nombre");
        model.addColumn("Ciudad");
        model.addColumn("Dirección");
        model.addColumn("Cantidad de Dormitorios");
        model.addColumn("Características");
        model.addColumn("Precio");
        model.addColumn("Fecha de Publicación");
        while (rs.next()) {
            // Obtener datos de las propiedades y agregarlas al modelo de la tabla...
            int idPropiedad = rs.getInt("idPropiedad");
            String nombre = rs.getString("nombre");
            String ciudad = rs.getString("ciudad");
            String direccion = rs.getString("direccion");
            int cantidadDormitorios = rs.getInt("cantidadDormitorios");
            String caracteristicas = rs.getString("caracteristicas");
            int precio = rs.getInt("precio");
            java.sql.Date fechaPublicacion = rs.getDate("fechaPublicacion");

            model.addRow(new Object[]{idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, fechaPublicacion});
        }
        rs.close();
        pstmt.close();
        con.close();
        return model;
    }

    private DefaultTableModel cargarPropiedadesVendedor(String sql, int noIdentidadUsuario) throws SQLException, ClassNotFoundException {
        Connection con = conectarMySQL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, noIdentidadUsuario);
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel();
        // Agregar columnas al modelo de la tabla...
        model.addColumn("ID Propiedad");
        model.addColumn("Nombre");
        model.addColumn("Ciudad");
        model.addColumn("Dirección");
        model.addColumn("Cantidad de Dormitorios");
        model.addColumn("Características");
        model.addColumn("Precio");
        model.addColumn("Fecha de Publicación");
        while (rs.next()) {
            // Obtener datos de las propiedades y agregarlas al modelo de la tabla...
            int idPropiedad = rs.getInt("idPropiedad");
            String nombre = rs.getString("nombre");
            String ciudad = rs.getString("ciudad");
            String direccion = rs.getString("direccion");
            int cantidadDormitorios = rs.getInt("cantidadDormitorios");
            String caracteristicas = rs.getString("caracteristicas");
            int precio = rs.getInt("precio");
            java.sql.Date fechaPublicacion = rs.getDate("fechaPublicacion");

            model.addRow(new Object[]{idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, fechaPublicacion});
        }
        rs.close();
        pstmt.close();
        con.close();
        return model;
    }

    private DefaultTableModel cargarPropiedadesComprador(String sql, int noIdentidadUsuario) throws SQLException, ClassNotFoundException {
        Connection con = conectarMySQL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, noIdentidadUsuario);
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel();
        // Agregar columnas al modelo de la tabla...
        model.addColumn("ID Propiedad");
        model.addColumn("Nombre");
        model.addColumn("Ciudad");
        model.addColumn("Dirección");
        model.addColumn("Cantidad de Dormitorios");
        model.addColumn("Características");
        model.addColumn("Precio");
        model.addColumn("Fecha de Publicación");
        while (rs.next()) {
            // Obtener datos de las propiedades y agregarlas al modelo de la tabla...
            int idPropiedad = rs.getInt("idPropiedad");
            String nombre = rs.getString("nombre");
            String ciudad = rs.getString("ciudad");
            String direccion = rs.getString("direccion");
            int cantidadDormitorios = rs.getInt("cantidadDormitorios");
            String caracteristicas = rs.getString("caracteristicas");
            int precio = rs.getInt("precio");
            java.sql.Date fechaPublicacion = rs.getDate("fechaPublicacion");

            model.addRow(new Object[]{idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, fechaPublicacion});
        }
        rs.close();
        pstmt.close();
        con.close();
        return model;
    }

// Método para obtener el noIdentidad del usuario (agente, vendedor o comprador)
    private int getNoIdentidadUsuario(int userId, String rol) throws SQLException, ClassNotFoundException {
        Connection con = conectarMySQL();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int noIdentidad = -1; // Cambiar el tipo de la variable a int y el valor predeterminado a -1
        String tabla = "";
        if ("agente".equals(rol)) {
            tabla = "agentes";
        } else if ("vendedor".equals(rol)) {
            tabla = "vendedores";
        } else if ("comprador".equals(rol)) {
            tabla = "compradores";
        }
        String query = "SELECT noIdentidad FROM " + tabla + " WHERE id_usuario = ?";
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                noIdentidad = rs.getInt("noIdentidad"); // Cambiar getString a getInt para obtener un entero
            }
        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return noIdentidad;
    }

    public void crearUsuario(int id, String nombreUsuario, String contraseña, String rol) throws NoSuchAlgorithmException { // Metodo para crear usuario sin Stored Procedure(CRUD)
        try (Connection conn = conectarMySQL()) {
            String sql = "INSERT INTO usuarios (id, nombre_usuario, contraseña, rol) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, nombreUsuario);
                pstmt.setString(3, getHash(contraseña));
                pstmt.setString(4, rol);
                pstmt.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet ejecutarConsulta(Connection connection, String consulta) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(consulta);
        return resultSet;
    }

    // Método para construir un DefaultTableModel a partir de un ResultSet
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        // Nombre de la columna
        String[] columnNames = {"Nombre de Usuario"};

        // Lista de datos
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Agrega los datos al modelo de la tabla
        while (resultSet.next()) {
            String nombreUsuario = resultSet.getString("nombre_usuario");
            model.addRow(new Object[]{nombreUsuario});
        }

        return model;
    }

    public void eliminarUsuario(String nombreUsuario) { // Metodo para eliminar usuario sin Stored Procedure(CRUD)
        try (Connection conn = conectarMySQL()) {
            String sql = "DELETE FROM usuarios WHERE nombre_usuario = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombreUsuario);
                pstmt.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JF_Login = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tf_password = new javax.swing.JPasswordField();
        tf_login = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        JB_LogIn = new javax.swing.JButton();
        JF_Admin = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        JP_CRUD = new javax.swing.JPanel();
        JB_CRUDUsuario = new javax.swing.JButton();
        JB_CRUDUsuario3 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        JP_Comprar = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        JT_ComprarCasaProp = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        JB_COnfirmarComprar = new javax.swing.JButton();
        jScrollPane27 = new javax.swing.JScrollPane();
        JT_ComprarCasaComprador = new javax.swing.JTable();
        JP_Reportes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JT_Vista5 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        JT_Vista3 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        JT_Vista4 = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        JT_Vista2 = new javax.swing.JTable();
        jScrollPane22 = new javax.swing.JScrollPane();
        JT_Vista1 = new javax.swing.JTable();
        jScrollPane23 = new javax.swing.JScrollPane();
        JT_Vista7 = new javax.swing.JTable();
        jScrollPane24 = new javax.swing.JScrollPane();
        JT_Vista8 = new javax.swing.JTable();
        jScrollPane25 = new javax.swing.JScrollPane();
        JT_Vista6 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        JB_BitacoraAdmin = new javax.swing.JButton();
        JB_CrudAdmin = new javax.swing.JButton();
        JB_ReportesAdmin = new javax.swing.JButton();
        JB_AsignarPropiedadAdmin = new javax.swing.JButton();
        JB_ComprarPropiedadAdmin = new javax.swing.JButton();
        JB_CerrarSesionAdmin = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        JP_Asignar = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        JT_SelVend = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        JB_AsignarCasa = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        JT_AsignarSelAG = new javax.swing.JTable();
        jScrollPane19 = new javax.swing.JScrollPane();
        JT_AsignarCasa2 = new javax.swing.JTable();
        JP_Bitacora = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        JT_Bitacora = new javax.swing.JTable();
        JD_PropiedadesCRUD = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        JB_CrearPropiedades = new javax.swing.JButton();
        JB_ModificarPropiedades = new javax.swing.JButton();
        JB_EliminarPropiedades = new javax.swing.JButton();
        JP_EliminarPropiedad = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        JT_EliminarProp = new javax.swing.JTable();
        JB_ConfirmarEliminarPropiedad = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        CB_ElimProp = new javax.swing.JComboBox<>();
        JP_CrearPropiedad = new javax.swing.JPanel();
        TF_CrearCaracteristica = new javax.swing.JTextField();
        JB_ConfirmarCrearUsuaro1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        JS_CrearHabitaciones = new javax.swing.JSpinner();
        TF_CrearDireccion = new javax.swing.JTextField();
        TF_CrearCiudad = new javax.swing.JTextField();
        JS_CrearPrecio = new javax.swing.JSpinner();
        jLabel55 = new javax.swing.JLabel();
        TF_CrearNombrePropiedad = new javax.swing.JTextField();
        JP_ModPropiedad = new javax.swing.JPanel();
        JB_ConfirmarModPropiedad = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        JT_ModPropiedad = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        TF_ModDirec = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        JS_ModHabitaciones = new javax.swing.JSpinner();
        TF_ModNombre = new javax.swing.JTextField();
        TF_ModCaracteristicas = new javax.swing.JTextField();
        SP_ModificarPrecio = new javax.swing.JSpinner();
        jLabel38 = new javax.swing.JLabel();
        CB_ModProp = new javax.swing.JComboBox<>();
        TF_ModCiudad1 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        TF_ModPROPID = new javax.swing.JTextField();
        JB_cambiarAgente = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        TF_ModAgenteProp = new javax.swing.JTextField();
        JF_Agente = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        JP_PropiedadesVendidas = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        JT_PropiedadesVendidasAgente = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        JB_CerrarSesionAgente = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        JB_PropiedadesAsignadas = new javax.swing.JButton();
        JB_PropiedadesVendidas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        JF_Vendedor = new javax.swing.JFrame();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        JB_CerrarSesionVendedor = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        JB_PropiedadesEnMercadoVendedor = new javax.swing.JButton();
        JB_PropiedadesVendidasVendedor = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        JP_PropiedadesEnMercadoVendedordor = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        JT_PropiedadeEnMercadoVendedor = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        JL_ImagenVendedor = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        JF_Comprador = new javax.swing.JFrame();
        jPanel15 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        JB_CerrarSesionComprador = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        JB_PropiedadesEnMercadoComprador = new javax.swing.JButton();
        JB_PropiedadesCompradasComprador = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        JP_PropiedadesEnMercadoComprador = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        JT_PropiedadesEnMercadoComprador = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        JL_ImagenComprador = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        JF_UsuarioCRUD = new javax.swing.JFrame();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        JB_CrearUsuario = new javax.swing.JButton();
        JB_ModificarUsuario = new javax.swing.JButton();
        JB_EliminarUsuario = new javax.swing.JButton();
        JP_ModUsuario1 = new javax.swing.JPanel();
        JB_ConfirmarModUsuaro1 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        JT_ModUsuario1 = new javax.swing.JTable();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        TF_NombreCrear3 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        TF_DireccionCrearPersona3 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        TF_CrearCelular3 = new javax.swing.JTextField();
        jl_TelefonoCompania3 = new javax.swing.JLabel();
        TF_CrearTelefono3 = new javax.swing.JTextField();
        CB_MODTipoUsuario1 = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        TF_modDNI = new javax.swing.JTextField();
        JP_EliminarUsuario1 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        JT_EliminarUser4 = new javax.swing.JTable();
        CB_ELIMTipoUsuario = new javax.swing.JComboBox<>();
        JB_ConfirmarEliminarUsuario1 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        JP_CrearUsuario1 = new javax.swing.JPanel();
        TF_CrearUser = new javax.swing.JTextField();
        TF_CrearPassword = new javax.swing.JTextField();
        CB_CrearTipoUsuario = new javax.swing.JComboBox<>();
        JB_ConfirmarCrearUsuaro = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        TF_IDUSER = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jl_TelefonoCompania = new javax.swing.JLabel();
        TF_DireccionCrearPersona = new javax.swing.JTextField();
        TF_NombreCrear = new javax.swing.JTextField();
        TF_CrearCelular = new javax.swing.JTextField();
        TF_CrearTelefono = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        TF_CrearDNI = new javax.swing.JTextField();
        ElegirnuevoAgente = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        JT_ModProp_NuevoAgente = new javax.swing.JTable();
        jl_TelefonoCompania1 = new javax.swing.JLabel();
        Confirmar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        JB_Login = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        compra = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        renta = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        vende = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();

        JF_Login.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JF_Login.setMinimumSize(new java.awt.Dimension(1920, 1080));
        JF_Login.setResizable(false);

        jPanel1.setBackground(new java.awt.Color(252, 248, 248));
        jPanel1.setPreferredSize(new java.awt.Dimension(1926, 924));

        jLabel7.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel7.setText("USERNAME:");

        jLabel8.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel8.setText("PASSWORD:");

        jPanel4.setBackground(new java.awt.Color(245, 140, 104));
        jPanel4.setPreferredSize(new java.awt.Dimension(314, 924));

        jLabel9.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("WELCOME BACK!");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(432, 432, 432)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );

        JB_LogIn.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_LogIn.setText("Log in");
        JB_LogIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_LogInMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_login, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(432, 432, 432)
                        .addComponent(JB_LogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(784, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(tf_login, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel8)
                .addGap(28, 28, 28)
                .addComponent(tf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(JB_LogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JF_LoginLayout = new javax.swing.GroupLayout(JF_Login.getContentPane());
        JF_Login.getContentPane().setLayout(JF_LoginLayout);
        JF_LoginLayout.setHorizontalGroup(
            JF_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JF_LoginLayout.setVerticalGroup(
            JF_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
        );

        JF_Admin.setMinimumSize(new java.awt.Dimension(1920, 1080));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JP_CRUD.setBackground(new java.awt.Color(255, 255, 255));
        JP_CRUD.setPreferredSize(new java.awt.Dimension(1200, 1000));

        JB_CRUDUsuario.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_CRUDUsuario.setText("Usuarios");
        JB_CRUDUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CRUDUsuarioMouseClicked(evt);
            }
        });

        JB_CRUDUsuario3.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_CRUDUsuario3.setText("Propiedades");
        JB_CRUDUsuario3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CRUDUsuario3MouseClicked(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 172, 144));

        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sellers_real_state_man_home_house_icon_231049.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 172, 144));

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/businessapplication_edit_male_user_thepencil_theclient_negocio_2321.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout JP_CRUDLayout = new javax.swing.GroupLayout(JP_CRUD);
        JP_CRUD.setLayout(JP_CRUDLayout);
        JP_CRUDLayout.setHorizontalGroup(
            JP_CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_CRUDLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addGroup(JP_CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_CRUDLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(JB_CRUDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(240, 240, 240)
                        .addComponent(JB_CRUDUsuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_CRUDLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(358, Short.MAX_VALUE))
        );
        JP_CRUDLayout.setVerticalGroup(
            JP_CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_CRUDLayout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addGroup(JP_CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(JP_CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JB_CRUDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JB_CRUDUsuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(452, 452, 452))
        );

        jPanel2.add(JP_CRUD, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1300, -1));

        JP_Comprar.setBackground(new java.awt.Color(255, 255, 255));
        JP_Comprar.setPreferredSize(new java.awt.Dimension(1200, 1000));
        JP_Comprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JP_ComprarMouseClicked(evt);
            }
        });

        JT_ComprarCasaProp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ID"
            }
        ));
        JT_ComprarCasaProp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_ComprarCasaPropMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(JT_ComprarCasaProp);

        jLabel14.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel14.setText("Seleccione el comprador ");

        jLabel33.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel33.setText("Seleccione la propiedad a comprar");

        JB_COnfirmarComprar.setBackground(new java.awt.Color(255, 172, 144));
        JB_COnfirmarComprar.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_COnfirmarComprar.setText("Comprar");
        JB_COnfirmarComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_COnfirmarComprarMouseClicked(evt);
            }
        });

        JT_ComprarCasaComprador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ID"
            }
        ));
        jScrollPane27.setViewportView(JT_ComprarCasaComprador);

        javax.swing.GroupLayout JP_ComprarLayout = new javax.swing.GroupLayout(JP_Comprar);
        JP_Comprar.setLayout(JP_ComprarLayout);
        JP_ComprarLayout.setHorizontalGroup(
            JP_ComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ComprarLayout.createSequentialGroup()
                .addContainerGap(148, Short.MAX_VALUE)
                .addGroup(JP_ComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(JP_ComprarLayout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150)
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174))
                    .addGroup(JP_ComprarLayout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))))
            .addGroup(JP_ComprarLayout.createSequentialGroup()
                .addGap(473, 473, 473)
                .addComponent(JB_COnfirmarComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JP_ComprarLayout.setVerticalGroup(
            JP_ComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ComprarLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(JP_ComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(JP_ComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(JB_COnfirmarComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        jPanel2.add(JP_Comprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1300, -1));

        JP_Reportes.setBackground(new java.awt.Color(255, 255, 255));
        JP_Reportes.setPreferredSize(new java.awt.Dimension(1920, 1080));
        JP_Reportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JP_ReportesMouseClicked(evt);
            }
        });
        JP_Reportes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JT_Vista5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(JT_Vista5);

        JP_Reportes.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 321));

        JT_Vista3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(JT_Vista3);

        JP_Reportes.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 270, 321));

        JT_Vista4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(JT_Vista4);

        JP_Reportes.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 270, 321));

        JT_Vista2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane21.setViewportView(JT_Vista2);

        JP_Reportes.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 270, 321));

        JT_Vista1.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        JT_Vista1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane22.setViewportView(JT_Vista1);

        JP_Reportes.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 270, 321));

        JT_Vista7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane23.setViewportView(JT_Vista7);

        JP_Reportes.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(648, 440, 270, 321));

        JT_Vista8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane24.setViewportView(JT_Vista8);

        JP_Reportes.add(jScrollPane24, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 440, 270, 321));

        JT_Vista6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JT_Vista6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane25.setViewportView(JT_Vista6);

        JP_Reportes.add(jScrollPane25, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 441, 270, 321));

        jLabel2.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel2.setText("Ventas Por Agente");
        JP_Reportes.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel3.setText("Ventas Por Vendedor");
        JP_Reportes.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, 30));

        jLabel4.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel4.setText("Compras por Comprador");
        JP_Reportes.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, -1));

        jLabel45.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel45.setText("Ventas por Ubicacion");
        JP_Reportes.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, -1, -1));

        jLabel58.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel58.setText("Ventas por precio de Propiedad");
        JP_Reportes.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        jLabel59.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel59.setText("de Propiedades");
        JP_Reportes.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, -1, -1));

        jLabel60.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel60.setText("Agente que vendio la mayor  cantidad de ");
        JP_Reportes.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 390, -1, 31));

        jLabel72.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel72.setText("propiedades en el año");
        JP_Reportes.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 410, -1, 20));

        jLabel73.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel73.setText("propiedades en el año por valor total");
        JP_Reportes.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, 31));

        jLabel74.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel74.setText("Precio de venta y tiempo promedio de");
        JP_Reportes.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 390, -1, 20));

        jLabel76.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel76.setText("Ventas por Caracteristicas ");
        JP_Reportes.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, -1, -1));

        jPanel2.add(JP_Reportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 2020, -1));

        jPanel5.setBackground(new java.awt.Color(255, 172, 144));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 1000));

        JB_BitacoraAdmin.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_BitacoraAdmin.setText("Bitacora");
        JB_BitacoraAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_BitacoraAdminMouseClicked(evt);
            }
        });

        JB_CrudAdmin.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_CrudAdmin.setText("CRUD");
        JB_CrudAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CrudAdminMouseClicked(evt);
            }
        });
        JB_CrudAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_CrudAdminActionPerformed(evt);
            }
        });

        JB_ReportesAdmin.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_ReportesAdmin.setText("Reportes");
        JB_ReportesAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ReportesAdminMouseClicked(evt);
            }
        });

        JB_AsignarPropiedadAdmin.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_AsignarPropiedadAdmin.setText("Asignar Propiedad");
        JB_AsignarPropiedadAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_AsignarPropiedadAdminMouseClicked(evt);
            }
        });
        JB_AsignarPropiedadAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_AsignarPropiedadAdminActionPerformed(evt);
            }
        });

        JB_ComprarPropiedadAdmin.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_ComprarPropiedadAdmin.setText("Comprar Propiedad");
        JB_ComprarPropiedadAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ComprarPropiedadAdminMouseClicked(evt);
            }
        });
        JB_ComprarPropiedadAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_ComprarPropiedadAdminActionPerformed(evt);
            }
        });

        JB_CerrarSesionAdmin.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        JB_CerrarSesionAdmin.setText("Cerrar Sesion");
        JB_CerrarSesionAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CerrarSesionAdminMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Rockwell", 0, 36)); // NOI18N
        jLabel15.setText("Bienvenido");

        jLabel16.setFont(new java.awt.Font("Rockwell", 0, 36)); // NOI18N
        jLabel16.setText("Administrador");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JB_AsignarPropiedadAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JB_BitacoraAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JB_CerrarSesionAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JB_ReportesAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JB_CrudAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JB_ComprarPropiedadAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel16))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(JB_AsignarPropiedadAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(JB_ComprarPropiedadAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(JB_CrudAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(JB_ReportesAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(JB_BitacoraAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(JB_CerrarSesionAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, -1));

        JP_Asignar.setBackground(new java.awt.Color(255, 255, 255));
        JP_Asignar.setPreferredSize(new java.awt.Dimension(1200, 1000));
        JP_Asignar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JP_AsignarMouseClicked(evt);
            }
        });
        JP_Asignar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JT_SelVend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ID"
            }
        ));
        JT_SelVend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_SelVendMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(JT_SelVend);

        JP_Asignar.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 345, 405));

        jLabel10.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel10.setText("Seleccione el vendedor\n");
        JP_Asignar.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 210, 38));

        jLabel13.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel13.setText("Seleccione la propiedad a asignar");
        JP_Asignar.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, 38));

        JB_AsignarCasa.setBackground(new java.awt.Color(255, 172, 144));
        JB_AsignarCasa.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_AsignarCasa.setText("Asignar");
        JB_AsignarCasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_AsignarCasaMouseClicked(evt);
            }
        });
        JP_Asignar.add(JB_AsignarCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 610, 178, 48));

        jLabel12.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel12.setText("Seleccione el agente");
        JP_Asignar.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 100, -1, 38));

        JT_AsignarSelAG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ID"
            }
        ));
        JT_AsignarSelAG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_AsignarSelAGMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(JT_AsignarSelAG);

        JP_Asignar.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 350, 400));

        JT_AsignarCasa2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ID"
            }
        ));
        JT_AsignarCasa2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_AsignarCasa2MouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(JT_AsignarCasa2);

        JP_Asignar.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 340, 405));

        jPanel2.add(JP_Asignar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1300, -1));

        JP_Bitacora.setBackground(new java.awt.Color(255, 255, 255));
        JP_Bitacora.setPreferredSize(new java.awt.Dimension(1200, 1000));

        jLabel75.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel75.setText("Bitacora");

        JT_Bitacora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane26.setViewportView(JT_Bitacora);

        javax.swing.GroupLayout JP_BitacoraLayout = new javax.swing.GroupLayout(JP_Bitacora);
        JP_Bitacora.setLayout(JP_BitacoraLayout);
        JP_BitacoraLayout.setHorizontalGroup(
            JP_BitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_BitacoraLayout.createSequentialGroup()
                .addGroup(JP_BitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_BitacoraLayout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_BitacoraLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(jLabel75)))
                .addContainerGap(362, Short.MAX_VALUE))
        );
        JP_BitacoraLayout.setVerticalGroup(
            JP_BitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_BitacoraLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel75)
                .addGap(51, 51, 51)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(405, Short.MAX_VALUE))
        );

        jPanel2.add(JP_Bitacora, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1300, -1));

        javax.swing.GroupLayout JF_AdminLayout = new javax.swing.GroupLayout(JF_Admin.getContentPane());
        JF_Admin.getContentPane().setLayout(JF_AdminLayout);
        JF_AdminLayout.setHorizontalGroup(
            JF_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1600, Short.MAX_VALUE)
        );
        JF_AdminLayout.setVerticalGroup(
            JF_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 643, Short.MAX_VALUE)
        );

        JD_PropiedadesCRUD.setMinimumSize(new java.awt.Dimension(1920, 1080));

        jPanel20.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(255, 193, 172));

        JB_CrearPropiedades.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JB_CrearPropiedades.setText("Crear");
        JB_CrearPropiedades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CrearPropiedadesMouseClicked(evt);
            }
        });

        JB_ModificarPropiedades.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JB_ModificarPropiedades.setText("Modificar");
        JB_ModificarPropiedades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ModificarPropiedadesMouseClicked(evt);
            }
        });

        JB_EliminarPropiedades.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JB_EliminarPropiedades.setText("Eliminar");
        JB_EliminarPropiedades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_EliminarPropiedadesMouseClicked(evt);
            }
        });
        JB_EliminarPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_EliminarPropiedadesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(JB_CrearPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152)
                .addComponent(JB_ModificarPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(JB_EliminarPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(657, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JB_CrearPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JB_ModificarPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JB_EliminarPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 150));

        JP_EliminarPropiedad.setBackground(new java.awt.Color(255, 255, 255));
        JP_EliminarPropiedad.setPreferredSize(new java.awt.Dimension(1920, 1080));

        JT_EliminarProp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ID"
            }
        ));
        jScrollPane11.setViewportView(JT_EliminarProp);

        JB_ConfirmarEliminarPropiedad.setBackground(new java.awt.Color(255, 193, 172));
        JB_ConfirmarEliminarPropiedad.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_ConfirmarEliminarPropiedad.setText("Eliminar");
        JB_ConfirmarEliminarPropiedad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ConfirmarEliminarPropiedadMouseClicked(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel37.setText("Selecciona la Propiedad a eliminar");

        jLabel30.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel30.setText("Tipo de propiedad a eliminar");

        CB_ElimProp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En Mercado", "Vendidas" }));
        CB_ElimProp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_ElimPropActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JP_EliminarPropiedadLayout = new javax.swing.GroupLayout(JP_EliminarPropiedad);
        JP_EliminarPropiedad.setLayout(JP_EliminarPropiedadLayout);
        JP_EliminarPropiedadLayout.setHorizontalGroup(
            JP_EliminarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_EliminarPropiedadLayout.createSequentialGroup()
                .addGroup(JP_EliminarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_EliminarPropiedadLayout.createSequentialGroup()
                        .addGap(488, 488, 488)
                        .addComponent(jLabel30)
                        .addGap(156, 156, 156)
                        .addComponent(CB_ElimProp, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_EliminarPropiedadLayout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_EliminarPropiedadLayout.createSequentialGroup()
                        .addGap(699, 699, 699)
                        .addComponent(JB_ConfirmarEliminarPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_EliminarPropiedadLayout.createSequentialGroup()
                        .addGap(654, 654, 654)
                        .addComponent(jLabel37)))
                .addContainerGap(780, Short.MAX_VALUE))
        );
        JP_EliminarPropiedadLayout.setVerticalGroup(
            JP_EliminarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_EliminarPropiedadLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel37)
                .addGap(51, 51, 51)
                .addGroup(JP_EliminarPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CB_ElimProp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(JB_ConfirmarEliminarPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );

        jPanel20.add(JP_EliminarPropiedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1920, 900));

        JP_CrearPropiedad.setBackground(new java.awt.Color(255, 255, 255));
        JP_CrearPropiedad.setPreferredSize(new java.awt.Dimension(1920, 1080));

        JB_ConfirmarCrearUsuaro1.setBackground(new java.awt.Color(255, 193, 172));
        JB_ConfirmarCrearUsuaro1.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_ConfirmarCrearUsuaro1.setText("Crear");
        JB_ConfirmarCrearUsuaro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ConfirmarCrearUsuaro1MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel28.setText("Precio");

        jLabel35.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel35.setText("Ciudad:");

        jLabel36.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel36.setText("Direccion:");

        jLabel39.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel39.setText("Nombre");

        jLabel40.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel40.setText("Habitaciones");

        jLabel55.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel55.setText("Caracteristicas");

        javax.swing.GroupLayout JP_CrearPropiedadLayout = new javax.swing.GroupLayout(JP_CrearPropiedad);
        JP_CrearPropiedad.setLayout(JP_CrearPropiedadLayout);
        JP_CrearPropiedadLayout.setHorizontalGroup(
            JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_CrearPropiedadLayout.createSequentialGroup()
                .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_CrearPropiedadLayout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_CrearNombrePropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_CrearCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JS_CrearPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_CrearDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_CrearCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JS_CrearHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(JP_CrearPropiedadLayout.createSequentialGroup()
                        .addGap(643, 643, 643)
                        .addComponent(JB_ConfirmarCrearUsuaro1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(703, Short.MAX_VALUE))
        );
        JP_CrearPropiedadLayout.setVerticalGroup(
            JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_CrearPropiedadLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_CrearDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_CrearNombrePropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_CrearCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_CrearCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(JP_CrearPropiedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(JS_CrearPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JS_CrearHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(142, 142, 142)
                .addComponent(JB_ConfirmarCrearUsuaro1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );

        jPanel20.add(JP_CrearPropiedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1920, 900));

        JP_ModPropiedad.setBackground(new java.awt.Color(255, 255, 255));
        JP_ModPropiedad.setPreferredSize(new java.awt.Dimension(1920, 1080));
        JP_ModPropiedad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JB_ConfirmarModPropiedad.setBackground(new java.awt.Color(255, 193, 172));
        JB_ConfirmarModPropiedad.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_ConfirmarModPropiedad.setText("Modificar");
        JB_ConfirmarModPropiedad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ConfirmarModPropiedadMouseClicked(evt);
            }
        });
        JP_ModPropiedad.add(JB_ConfirmarModPropiedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 560, 198, 58));

        JT_ModPropiedad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuario"
            }
        ));
        JT_ModPropiedad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_ModPropiedadMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(JT_ModPropiedad);

        JP_ModPropiedad.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, 410, 410));

        jLabel34.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel34.setText("Selecciona la Propiedad");
        JP_ModPropiedad.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));
        JP_ModPropiedad.add(TF_ModDirec, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 280, 319, 31));

        jLabel29.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel29.setText("Precio");
        JP_ModPropiedad.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 330, 60, 30));

        jLabel42.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel42.setText("Ciudad:");
        JP_ModPropiedad.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 230, -1, 30));

        jLabel43.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel43.setText("Direccion:");
        JP_ModPropiedad.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 290, 102, 30));

        jLabel44.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel44.setText("Nombre:");
        JP_ModPropiedad.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 180, -1, 30));

        jLabel46.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        JP_ModPropiedad.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, -1, 30));

        jLabel47.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel47.setText("Habitaciones:");
        JP_ModPropiedad.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 330, 135, 30));
        JP_ModPropiedad.add(JS_ModHabitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 332, 80, 30));

        TF_ModNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_ModNombreActionPerformed(evt);
            }
        });
        JP_ModPropiedad.add(TF_ModNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 180, 319, 31));
        JP_ModPropiedad.add(TF_ModCaracteristicas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 380, 210, 31));
        JP_ModPropiedad.add(SP_ModificarPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 330, 110, 30));

        jLabel38.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel38.setText("Tipo de propiedad ");
        JP_ModPropiedad.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, -1, 30));

        CB_ModProp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En Mercado", "Vendidas" }));
        CB_ModProp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                CB_ModPropMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                CB_ModPropMouseMoved(evt);
            }
        });
        CB_ModProp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_ModPropActionPerformed(evt);
            }
        });
        JP_ModPropiedad.add(CB_ModProp, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 146, 39));
        JP_ModPropiedad.add(TF_ModCiudad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 230, 319, 31));

        jLabel56.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel56.setText("ID");
        JP_ModPropiedad.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 120, -1, 30));

        TF_ModPROPID.setEditable(false);
        JP_ModPropiedad.add(TF_ModPROPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 120, 319, 31));

        JB_cambiarAgente.setBackground(new java.awt.Color(204, 255, 204));
        JB_cambiarAgente.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        JB_cambiarAgente.setText("Cambiar Agente");
        JB_cambiarAgente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_cambiarAgenteActionPerformed(evt);
            }
        });
        JP_ModPropiedad.add(JB_cambiarAgente, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 430, -1, -1));

        jLabel57.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel57.setText("Caracteristicas:");
        JP_ModPropiedad.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 380, -1, 30));

        TF_ModAgenteProp.setEditable(false);
        JP_ModPropiedad.add(TF_ModAgenteProp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 430, 210, 31));

        jPanel20.add(JP_ModPropiedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1920, 900));

        javax.swing.GroupLayout JD_PropiedadesCRUDLayout = new javax.swing.GroupLayout(JD_PropiedadesCRUD.getContentPane());
        JD_PropiedadesCRUD.getContentPane().setLayout(JD_PropiedadesCRUDLayout);
        JD_PropiedadesCRUDLayout.setHorizontalGroup(
            JD_PropiedadesCRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JD_PropiedadesCRUDLayout.setVerticalGroup(
            JD_PropiedadesCRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JF_Agente.setMinimumSize(new java.awt.Dimension(1920, 1080));

        jPanel3.setPreferredSize(new java.awt.Dimension(1926, 924));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JP_PropiedadesVendidas.setBackground(new java.awt.Color(253, 238, 238));

        JT_PropiedadesVendidasAgente.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JT_PropiedadesVendidasAgente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Precio"
            }
        ));
        jScrollPane5.setViewportView(JT_PropiedadesVendidasAgente);

        jLabel17.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel17.setText("Propiedades vedidas");

        javax.swing.GroupLayout JP_PropiedadesVendidasLayout = new javax.swing.GroupLayout(JP_PropiedadesVendidas);
        JP_PropiedadesVendidas.setLayout(JP_PropiedadesVendidasLayout);
        JP_PropiedadesVendidasLayout.setHorizontalGroup(
            JP_PropiedadesVendidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PropiedadesVendidasLayout.createSequentialGroup()
                .addGroup(JP_PropiedadesVendidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PropiedadesVendidasLayout.createSequentialGroup()
                        .addGap(447, 447, 447)
                        .addComponent(jLabel17))
                    .addGroup(JP_PropiedadesVendidasLayout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(503, Short.MAX_VALUE))
        );
        JP_PropiedadesVendidasLayout.setVerticalGroup(
            JP_PropiedadesVendidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PropiedadesVendidasLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel17)
                .addGap(57, 57, 57)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );

        jPanel3.add(JP_PropiedadesVendidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 1520, 760));

        jPanel9.setBackground(new java.awt.Color(255, 172, 144));
        jPanel9.setPreferredSize(new java.awt.Dimension(1926, 150));

        JB_CerrarSesionAgente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CerrarSesionAgenteMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel18.setText("Cerrar Sesion");

        javax.swing.GroupLayout JB_CerrarSesionAgenteLayout = new javax.swing.GroupLayout(JB_CerrarSesionAgente);
        JB_CerrarSesionAgente.setLayout(JB_CerrarSesionAgenteLayout);
        JB_CerrarSesionAgenteLayout.setHorizontalGroup(
            JB_CerrarSesionAgenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_CerrarSesionAgenteLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(21, 21, 21))
        );
        JB_CerrarSesionAgenteLayout.setVerticalGroup(
            JB_CerrarSesionAgenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_CerrarSesionAgenteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel19.setFont(new java.awt.Font("Rockwell", 0, 48)); // NOI18N
        jLabel19.setText("Bienvedido, has iniciado sesion como Agente");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel19)
                .addGap(174, 174, 174)
                .addComponent(JB_CerrarSesionAgente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(508, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JB_CerrarSesionAgente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 150));

        jPanel10.setBackground(new java.awt.Color(255, 172, 144));

        JB_PropiedadesAsignadas.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_PropiedadesAsignadas.setText("Asignadas");
        JB_PropiedadesAsignadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_PropiedadesAsignadasMouseClicked(evt);
            }
        });

        JB_PropiedadesVendidas.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_PropiedadesVendidas.setText("Vendidas");
        JB_PropiedadesVendidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_PropiedadesVendidasMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel1.setText("Propiedades");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JB_PropiedadesVendidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JB_PropiedadesAsignadas, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(98, 98, 98)
                .addComponent(JB_PropiedadesAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(JB_PropiedadesVendidas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 260, 490));

        javax.swing.GroupLayout JF_AgenteLayout = new javax.swing.GroupLayout(JF_Agente.getContentPane());
        JF_Agente.getContentPane().setLayout(JF_AgenteLayout);
        JF_AgenteLayout.setHorizontalGroup(
            JF_AgenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JF_AgenteLayout.setVerticalGroup(
            JF_AgenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JF_Vendedor.setMaximumSize(new java.awt.Dimension(1920, 1080));
        JF_Vendedor.setMinimumSize(new java.awt.Dimension(1920, 1080));
        JF_Vendedor.setPreferredSize(new java.awt.Dimension(1920, 1080));

        jPanel12.setPreferredSize(new java.awt.Dimension(1926, 924));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 172, 144));
        jPanel13.setPreferredSize(new java.awt.Dimension(1926, 150));

        JB_CerrarSesionVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CerrarSesionVendedorMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel20.setText("Cerrar Sesion");

        javax.swing.GroupLayout JB_CerrarSesionVendedorLayout = new javax.swing.GroupLayout(JB_CerrarSesionVendedor);
        JB_CerrarSesionVendedor.setLayout(JB_CerrarSesionVendedorLayout);
        JB_CerrarSesionVendedorLayout.setHorizontalGroup(
            JB_CerrarSesionVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JB_CerrarSesionVendedorLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel20)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        JB_CerrarSesionVendedorLayout.setVerticalGroup(
            JB_CerrarSesionVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_CerrarSesionVendedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel21.setFont(new java.awt.Font("Rockwell", 0, 48)); // NOI18N
        jLabel21.setText("Bienvedido, has iniciado sesion como Vendedor");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel21)
                .addGap(144, 144, 144)
                .addComponent(JB_CerrarSesionVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(475, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(JB_CerrarSesionVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 150));

        jPanel14.setBackground(new java.awt.Color(255, 172, 144));

        JB_PropiedadesEnMercadoVendedor.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_PropiedadesEnMercadoVendedor.setText("En Mercado");
        JB_PropiedadesEnMercadoVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_PropiedadesEnMercadoVendedorMouseClicked(evt);
            }
        });

        JB_PropiedadesVendidasVendedor.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_PropiedadesVendidasVendedor.setText("Vendidas");
        JB_PropiedadesVendidasVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_PropiedadesVendidasVendedorMouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel22.setText("Propiedades");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JB_PropiedadesVendidasVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel22)
                        .addComponent(JB_PropiedadesEnMercadoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel22)
                .addGap(93, 93, 93)
                .addComponent(JB_PropiedadesEnMercadoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(JB_PropiedadesVendidasVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jPanel12.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 210, 490));

        JP_PropiedadesEnMercadoVendedordor.setBackground(new java.awt.Color(253, 238, 238));

        JT_PropiedadeEnMercadoVendedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Precio", "Direccion", "Habitaciones", "Piscina"
            }
        ));
        JT_PropiedadeEnMercadoVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_PropiedadeEnMercadoVendedorMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(JT_PropiedadeEnMercadoVendedor);

        jPanel16.setBackground(new java.awt.Color(255, 172, 144));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(JL_ImagenVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(JL_ImagenVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel23.setText("Imagen");

        javax.swing.GroupLayout JP_PropiedadesEnMercadoVendedordorLayout = new javax.swing.GroupLayout(JP_PropiedadesEnMercadoVendedordor);
        JP_PropiedadesEnMercadoVendedordor.setLayout(JP_PropiedadesEnMercadoVendedordorLayout);
        JP_PropiedadesEnMercadoVendedordorLayout.setHorizontalGroup(
            JP_PropiedadesEnMercadoVendedordorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel23)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        JP_PropiedadesEnMercadoVendedordorLayout.setVerticalGroup(
            JP_PropiedadesEnMercadoVendedordorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createSequentialGroup()
                .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel23)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_PropiedadesEnMercadoVendedordorLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(163, Short.MAX_VALUE))
        );

        jPanel12.add(JP_PropiedadesEnMercadoVendedordor, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 1290, 730));

        javax.swing.GroupLayout JF_VendedorLayout = new javax.swing.GroupLayout(JF_Vendedor.getContentPane());
        JF_Vendedor.getContentPane().setLayout(JF_VendedorLayout);
        JF_VendedorLayout.setHorizontalGroup(
            JF_VendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JF_VendedorLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JF_VendedorLayout.setVerticalGroup(
            JF_VendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JF_VendedorLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        JF_Comprador.setMinimumSize(new java.awt.Dimension(1920, 1080));

        jPanel15.setOpaque(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(1926, 924));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 172, 144));
        jPanel17.setPreferredSize(new java.awt.Dimension(1926, 150));

        JB_CerrarSesionComprador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CerrarSesionCompradorMouseClicked(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel24.setText("Cerrar Sesion");

        javax.swing.GroupLayout JB_CerrarSesionCompradorLayout = new javax.swing.GroupLayout(JB_CerrarSesionComprador);
        JB_CerrarSesionComprador.setLayout(JB_CerrarSesionCompradorLayout);
        JB_CerrarSesionCompradorLayout.setHorizontalGroup(
            JB_CerrarSesionCompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_CerrarSesionCompradorLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(21, 21, 21))
        );
        JB_CerrarSesionCompradorLayout.setVerticalGroup(
            JB_CerrarSesionCompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_CerrarSesionCompradorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel25.setFont(new java.awt.Font("Rockwell", 0, 48)); // NOI18N
        jLabel25.setText("Bienvedido, has iniciado sesion como Comprador");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel25)
                .addGap(123, 123, 123)
                .addComponent(JB_CerrarSesionComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(460, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JB_CerrarSesionComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 150));

        jPanel18.setBackground(new java.awt.Color(255, 172, 144));

        JB_PropiedadesEnMercadoComprador.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_PropiedadesEnMercadoComprador.setText("En Mercado");
        JB_PropiedadesEnMercadoComprador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_PropiedadesEnMercadoCompradorMouseClicked(evt);
            }
        });

        JB_PropiedadesCompradasComprador.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_PropiedadesCompradasComprador.setText("Compradas");
        JB_PropiedadesCompradasComprador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_PropiedadesCompradasCompradorMouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel26.setText("Propiedades");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JB_PropiedadesCompradasComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel26)
                        .addComponent(JB_PropiedadesEnMercadoComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel26)
                .addGap(93, 93, 93)
                .addComponent(JB_PropiedadesEnMercadoComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(JB_PropiedadesCompradasComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jPanel15.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 210, 490));

        JP_PropiedadesEnMercadoComprador.setBackground(new java.awt.Color(253, 238, 238));

        JT_PropiedadesEnMercadoComprador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Precio", "Direccion", "Habitaciones", "Piscina"
            }
        ));
        JT_PropiedadesEnMercadoComprador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_PropiedadesEnMercadoCompradorMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(JT_PropiedadesEnMercadoComprador);

        jPanel19.setBackground(new java.awt.Color(255, 172, 144));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel19.add(JL_ImagenComprador, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 306, 300));

        jLabel27.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel27.setText("Imagen");

        javax.swing.GroupLayout JP_PropiedadesEnMercadoCompradorLayout = new javax.swing.GroupLayout(JP_PropiedadesEnMercadoComprador);
        JP_PropiedadesEnMercadoComprador.setLayout(JP_PropiedadesEnMercadoCompradorLayout);
        JP_PropiedadesEnMercadoCompradorLayout.setHorizontalGroup(
            JP_PropiedadesEnMercadoCompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel27))
                    .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        JP_PropiedadesEnMercadoCompradorLayout.setVerticalGroup(
            JP_PropiedadesEnMercadoCompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createSequentialGroup()
                .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel27)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_PropiedadesEnMercadoCompradorLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        jPanel15.add(JP_PropiedadesEnMercadoComprador, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 1290, 730));

        javax.swing.GroupLayout JF_CompradorLayout = new javax.swing.GroupLayout(JF_Comprador.getContentPane());
        JF_Comprador.getContentPane().setLayout(JF_CompradorLayout);
        JF_CompradorLayout.setHorizontalGroup(
            JF_CompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JF_CompradorLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JF_CompradorLayout.setVerticalGroup(
            JF_CompradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JF_CompradorLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        JF_UsuarioCRUD.setMinimumSize(new java.awt.Dimension(1920, 1080));

        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(255, 193, 172));

        JB_CrearUsuario.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JB_CrearUsuario.setText("Crear");
        JB_CrearUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_CrearUsuarioMouseClicked(evt);
            }
        });

        JB_ModificarUsuario.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JB_ModificarUsuario.setText("Modificar");
        JB_ModificarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ModificarUsuarioMouseClicked(evt);
            }
        });

        JB_EliminarUsuario.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        JB_EliminarUsuario.setText("Eliminar");
        JB_EliminarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_EliminarUsuarioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(305, Short.MAX_VALUE)
                .addComponent(JB_CrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(JB_ModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(JB_EliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(750, 750, 750))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JB_ModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JB_CrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JB_EliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 110));

        JP_ModUsuario1.setBackground(new java.awt.Color(255, 255, 255));
        JP_ModUsuario1.setPreferredSize(new java.awt.Dimension(1920, 1080));
        JP_ModUsuario1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JB_ConfirmarModUsuaro1.setBackground(new java.awt.Color(255, 193, 172));
        JB_ConfirmarModUsuaro1.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_ConfirmarModUsuaro1.setText("Modificar");
        JB_ConfirmarModUsuaro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ConfirmarModUsuaro1MouseClicked(evt);
            }
        });
        JP_ModUsuario1.add(JB_ConfirmarModUsuaro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, 198, 58));

        JT_ModUsuario1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuario"
            }
        ));
        JT_ModUsuario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_ModUsuario1MouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(JT_ModUsuario1);

        JP_ModUsuario1.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 357, 379));

        jLabel66.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel66.setText("Selecciona el tipo de usuario ");
        JP_ModUsuario1.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel67.setText("Nombre");
        JP_ModUsuario1.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, 79, -1));
        JP_ModUsuario1.add(TF_NombreCrear3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 220, 250, 30));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel68.setText("Direccion");
        JP_ModUsuario1.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 260, 98, -1));
        JP_ModUsuario1.add(TF_DireccionCrearPersona3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 290, 250, 30));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel69.setText("Celular");
        JP_ModUsuario1.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 330, 92, -1));
        JP_ModUsuario1.add(TF_CrearCelular3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 360, 250, 30));

        jl_TelefonoCompania3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jl_TelefonoCompania3.setText("Telefono Compañia");
        JP_ModUsuario1.add(jl_TelefonoCompania3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 410, 202, -1));
        JP_ModUsuario1.add(TF_CrearTelefono3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 440, 250, 30));

        CB_MODTipoUsuario1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "agente", "comprador", "vendedor", " " }));
        CB_MODTipoUsuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_MODTipoUsuario1ActionPerformed(evt);
            }
        });
        JP_ModUsuario1.add(CB_MODTipoUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 110, 40));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel70.setText("DNI");
        JP_ModUsuario1.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 79, -1));

        TF_modDNI.setEditable(false);
        JP_ModUsuario1.add(TF_modDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 250, 30));

        jPanel24.add(JP_ModUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1920, 970));

        JP_EliminarUsuario1.setBackground(new java.awt.Color(255, 255, 255));
        JP_EliminarUsuario1.setPreferredSize(new java.awt.Dimension(1920, 1080));

        JT_EliminarUser4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuario"
            }
        ));
        jScrollPane16.setViewportView(JT_EliminarUser4);

        CB_ELIMTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "agente", "comprador", "vendedor", " " }));
        CB_ELIMTipoUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CB_ELIMTipoUsuarioMouseClicked(evt);
            }
        });
        CB_ELIMTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_ELIMTipoUsuarioActionPerformed(evt);
            }
        });

        JB_ConfirmarEliminarUsuario1.setBackground(new java.awt.Color(255, 193, 172));
        JB_ConfirmarEliminarUsuario1.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_ConfirmarEliminarUsuario1.setText("Eliminar");
        JB_ConfirmarEliminarUsuario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ConfirmarEliminarUsuario1MouseClicked(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel71.setText("Selecciona el tipo de usuario a eliminar ");

        javax.swing.GroupLayout JP_EliminarUsuario1Layout = new javax.swing.GroupLayout(JP_EliminarUsuario1);
        JP_EliminarUsuario1.setLayout(JP_EliminarUsuario1Layout);
        JP_EliminarUsuario1Layout.setHorizontalGroup(
            JP_EliminarUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_EliminarUsuario1Layout.createSequentialGroup()
                .addGap(412, 412, 412)
                .addGroup(JP_EliminarUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_EliminarUsuario1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel71)
                        .addGap(121, 121, 121)
                        .addComponent(CB_ELIMTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_EliminarUsuario1Layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addComponent(JB_ConfirmarEliminarUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(830, Short.MAX_VALUE))
        );
        JP_EliminarUsuario1Layout.setVerticalGroup(
            JP_EliminarUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_EliminarUsuario1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(JP_EliminarUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(CB_ELIMTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(JB_ConfirmarEliminarUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );

        jPanel24.add(JP_EliminarUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1920, 900));

        JP_CrearUsuario1.setBackground(new java.awt.Color(255, 255, 255));
        JP_CrearUsuario1.setPreferredSize(new java.awt.Dimension(1920, 1080));
        JP_CrearUsuario1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        JP_CrearUsuario1.add(TF_CrearUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 300, 30));
        JP_CrearUsuario1.add(TF_CrearPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 310, 30));

        CB_CrearTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "agente", "comprador", "vendedor", " " }));
        CB_CrearTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_CrearTipoUsuarioActionPerformed(evt);
            }
        });
        JP_CrearUsuario1.add(CB_CrearTipoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 146, 39));

        JB_ConfirmarCrearUsuaro.setBackground(new java.awt.Color(255, 193, 172));
        JB_ConfirmarCrearUsuaro.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        JB_ConfirmarCrearUsuaro.setText("Crear");
        JB_ConfirmarCrearUsuaro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_ConfirmarCrearUsuaroMouseClicked(evt);
            }
        });
        JP_CrearUsuario1.add(JB_ConfirmarCrearUsuaro, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 560, 198, 58));

        jLabel5.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel5.setText("Contrasena");
        JP_CrearUsuario1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 129, 30));

        jLabel6.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel6.setText("Tipo");
        JP_CrearUsuario1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 102, 30));

        jLabel11.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel11.setText("Usuario");
        JP_CrearUsuario1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 102, 30));

        jLabel61.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel61.setText("Id de Usuario");
        JP_CrearUsuario1.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, 25));
        JP_CrearUsuario1.add(TF_IDUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 310, 30));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel62.setText("Nombre");
        JP_CrearUsuario1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 80, 79, -1));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel63.setText("Direccion");
        JP_CrearUsuario1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 260, 98, -1));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel64.setText("Celular");
        JP_CrearUsuario1.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 360, 92, -1));

        jl_TelefonoCompania.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jl_TelefonoCompania.setText("Telefono Compañia");
        JP_CrearUsuario1.add(jl_TelefonoCompania, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 460, 202, -1));
        JP_CrearUsuario1.add(TF_DireccionCrearPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 300, 310, 30));
        JP_CrearUsuario1.add(TF_NombreCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 120, 310, 30));
        JP_CrearUsuario1.add(TF_CrearCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 400, 310, 30));
        JP_CrearUsuario1.add(TF_CrearTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 500, 310, 30));

        jLabel65.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel65.setText("DNI");
        JP_CrearUsuario1.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 180, 43, -1));
        JP_CrearUsuario1.add(TF_CrearDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 210, 310, 30));

        jPanel24.add(JP_CrearUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1920, 900));

        javax.swing.GroupLayout JF_UsuarioCRUDLayout = new javax.swing.GroupLayout(JF_UsuarioCRUD.getContentPane());
        JF_UsuarioCRUD.getContentPane().setLayout(JF_UsuarioCRUDLayout);
        JF_UsuarioCRUDLayout.setHorizontalGroup(
            JF_UsuarioCRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JF_UsuarioCRUDLayout.setVerticalGroup(
            JF_UsuarioCRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ElegirnuevoAgente.setMinimumSize(new java.awt.Dimension(1920, 1080));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JT_ModProp_NuevoAgente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuario"
            }
        ));
        JT_ModProp_NuevoAgente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_ModProp_NuevoAgenteMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(JT_ModProp_NuevoAgente);

        jPanel6.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 315, 290));

        jl_TelefonoCompania1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jl_TelefonoCompania1.setText("Elegir nuevo agente");
        jPanel6.add(jl_TelefonoCompania1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 170, -1));

        Confirmar.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        Confirmar.setText("CONFIRMAR");
        Confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmarActionPerformed(evt);
            }
        });
        jPanel6.add(Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 160, 30));

        javax.swing.GroupLayout ElegirnuevoAgenteLayout = new javax.swing.GroupLayout(ElegirnuevoAgente.getContentPane());
        ElegirnuevoAgente.getContentPane().setLayout(ElegirnuevoAgenteLayout);
        ElegirnuevoAgenteLayout.setHorizontalGroup(
            ElegirnuevoAgenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ElegirnuevoAgenteLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ElegirnuevoAgenteLayout.setVerticalGroup(
            ElegirnuevoAgenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ElegirnuevoAgenteLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel50.setText("Los profesionales #1 para confiarles tu casa");
        jPanel7.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, -1, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Rockwell", 1, 60)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Real Staters");
        jPanel7.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, -1, -1));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/IMG-20240313-WA0005.jpg"))); // NOI18N
        jLabel31.setText("jLabel31");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 76, 1941, 557));

        JB_Login.setBackground(new java.awt.Color(255, 172, 144));
        JB_Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JB_LoginMouseClicked(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jLabel32.setText("Log In");

        javax.swing.GroupLayout JB_LoginLayout = new javax.swing.GroupLayout(JB_Login);
        JB_Login.setLayout(JB_LoginLayout);
        JB_LoginLayout.setHorizontalGroup(
            JB_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_LoginLayout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        JB_LoginLayout.setVerticalGroup(
            JB_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JB_LoginLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addContainerGap())
        );

        jPanel7.add(JB_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 20, 200, 40));

        jLabel48.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel48.setText("Real");
        jPanel7.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, -1));

        jLabel49.setBackground(new java.awt.Color(255, 113, 66));
        jLabel49.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 113, 66));
        jLabel49.setText("Staters");
        jPanel7.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, -1, -1));

        jPanel22.setBackground(new java.awt.Color(0, 51, 102));

        compra.setBackground(new java.awt.Color(255, 113, 66));

        jLabel51.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Compra");

        javax.swing.GroupLayout compraLayout = new javax.swing.GroupLayout(compra);
        compra.setLayout(compraLayout);
        compraLayout.setHorizontalGroup(
            compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, compraLayout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        compraLayout.setVerticalGroup(
            compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compraLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel51)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        renta.setBackground(new java.awt.Color(255, 113, 66));

        jLabel52.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Renta");

        javax.swing.GroupLayout rentaLayout = new javax.swing.GroupLayout(renta);
        renta.setLayout(rentaLayout);
        rentaLayout.setHorizontalGroup(
            rentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rentaLayout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        rentaLayout.setVerticalGroup(
            rentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rentaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel52)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        vende.setBackground(new java.awt.Color(255, 113, 66));

        jLabel53.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Vende");

        javax.swing.GroupLayout vendeLayout = new javax.swing.GroupLayout(vende);
        vende.setLayout(vendeLayout);
        vendeLayout.setHorizontalGroup(
            vendeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vendeLayout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        vendeLayout.setVerticalGroup(
            vendeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vendeLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addComponent(renta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(vende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(renta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 1620, 120));

        jPanel23.setBackground(new java.awt.Color(0, 51, 102));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/49aaf447-5817-48dc-a1c1-1f7ca2250444-removebg-preview_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addGap(123, 123, 123))
        );

        jPanel7.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JB_BitacoraAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_BitacoraAdminMouseClicked
        try {
            cargarBitacora();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        JP_CRUD.setVisible(false);
        JP_Asignar.setVisible(false);
        JP_Reportes.setVisible(false);
        JP_Bitacora.setVisible(true);
        JP_Comprar.setVisible(false);
    }//GEN-LAST:event_JB_BitacoraAdminMouseClicked

    private void JB_CrudAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CrudAdminMouseClicked
        JP_CRUD.setVisible(true);
        JP_Asignar.setVisible(false);
        JP_Reportes.setVisible(false);
        JP_Bitacora.setVisible(false);
        JP_Comprar.setVisible(false);
    }//GEN-LAST:event_JB_CrudAdminMouseClicked

    private void JB_ReportesAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ReportesAdminMouseClicked
        try {
            cargarVista1();
            cargarVista2();
            cargarVista3();
            cargarVista4();
            cargarVista5();
            cargarVista6();
            cargarVista7();
            cargarVista8();

            JP_CRUD.setVisible(false);
            JP_Asignar.setVisible(false);
            JP_Bitacora.setVisible(false);
            JP_Reportes.setVisible(true);
            JP_Comprar.setVisible(false);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JB_ReportesAdminMouseClicked

    private void JB_CrudAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_CrudAdminActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_JB_CrudAdminActionPerformed

    private void JB_LogInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_LogInMouseClicked

        String user = tf_login.getText();
        String pass = tf_password.getText();
        try {

            String[] login = getLogin(user, pass);
            if ("1".equals(login[0])) {
//                JOptionPane.showMessageDialog(null, login[1]);
                if ("admin".equals(login[1])) {
                    JF_Admin.setVisible(true);
                    JP_PropiedadesVendidas.setVisible(true);
                    tf_login.setText("");
                    tf_password.setText("");
                    JF_Admin.pack();
                    JF_Admin.setLocationRelativeTo(this);
                    JF_Login.setVisible(false);
                } else if ("vendedor".equals(login[1])) {
                    JF_Vendedor.setVisible(true);
                    JP_PropiedadesEnMercadoVendedordor.setVisible(true);
                    tf_login.setText("");
                    tf_password.setText("");
                    JF_Vendedor.pack();
                    JF_Vendedor.setLocationRelativeTo(this);
                    JF_Login.setVisible(false);
                } else if ("comprador".equals(login[1])) {
                    JF_Comprador.setVisible(true);
                    JP_PropiedadesEnMercadoComprador.setVisible(true);
                    tf_login.setText("");
                    tf_password.setText("");
                    JF_Comprador.pack();
                    JF_Comprador.setLocationRelativeTo(this);
                    JF_Login.setVisible(false);
                } else if ("agente".equals(login[1])) {
                    JF_Agente.setVisible(true);
                    tf_login.setText("");
                    tf_password.setText("");
                    JP_PropiedadesVendidas.setVisible(true);
                    JF_Agente.pack();
                    JF_Agente.setLocationRelativeTo(this);
                    JF_Login.setVisible(false);

                }
                //                JF_Vendedor.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                ;
            } else {
                JOptionPane.showMessageDialog(null, "A ocurrido un error, intente mas tarde!");
                JOptionPane.showMessageDialog(null, login[1]);

            }

        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_JB_LogInMouseClicked

    private void JB_CRUDUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CRUDUsuarioMouseClicked
        JF_UsuarioCRUD.setVisible(true);
        JF_UsuarioCRUD.pack();
        JF_UsuarioCRUD.setLocationRelativeTo(this);
    }//GEN-LAST:event_JB_CRUDUsuarioMouseClicked

    private void JB_PropiedadesVendidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_PropiedadesVendidasMouseClicked
        JP_PropiedadesVendidas.setVisible(true);
        try {
            cargarPropiedades("agente", "Vendidas");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_JB_PropiedadesVendidasMouseClicked

    private void JB_PropiedadesVendidasVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_PropiedadesVendidasVendedorMouseClicked

        try {
            cargarPropiedades("vendedor", "Vendidas");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JB_PropiedadesVendidasVendedorMouseClicked

    private void JB_PropiedadesEnMercadoVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_PropiedadesEnMercadoVendedorMouseClicked

        try {
            cargarPropiedades("vendedor", "En Mercado");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_JB_PropiedadesEnMercadoVendedorMouseClicked

    private void JB_PropiedadesEnMercadoCompradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_PropiedadesEnMercadoCompradorMouseClicked
        try {
            cargarPropiedadEnVenta(JT_PropiedadesEnMercadoComprador);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JB_PropiedadesEnMercadoCompradorMouseClicked

    private void JB_PropiedadesCompradasCompradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_PropiedadesCompradasCompradorMouseClicked

        try {
            cargarPropiedades("comprador", "Vendidas");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_JB_PropiedadesCompradasCompradorMouseClicked

    private void JB_CRUDUsuario3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CRUDUsuario3MouseClicked
        JD_PropiedadesCRUD.setVisible(true);
    }//GEN-LAST:event_JB_CRUDUsuario3MouseClicked

    private void TF_ModNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_ModNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_ModNombreActionPerformed

    private void JB_LoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_LoginMouseClicked
        this.dispose();
        //  JF_Login.setExtendedState(MAXIMIZED_BOTH);
        JF_Login.pack();
        JF_Login.setLocationRelativeTo(this);
        JF_Login.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JF_Login.setVisible(true);

    }//GEN-LAST:event_JB_LoginMouseClicked

    private void JB_AsignarPropiedadAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_AsignarPropiedadAdminMouseClicked
        try {
            cargarPropiedadEnVenta(JT_AsignarCasa2);
            cargarVendedores(JT_SelVend);
            cargarAgente(JT_AsignarSelAG);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        JP_CRUD.setVisible(false);
        JP_Asignar.setVisible(true);
        JP_Reportes.setVisible(false);
        JP_Comprar.setVisible(false);
    }//GEN-LAST:event_JB_AsignarPropiedadAdminMouseClicked

    private void JB_AsignarPropiedadAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_AsignarPropiedadAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JB_AsignarPropiedadAdminActionPerformed

    private void JP_ComprarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JP_ComprarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JP_ComprarMouseClicked

    private void JB_ComprarPropiedadAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ComprarPropiedadAdminMouseClicked
        try {

            cargarPropiedadEnVenta(JT_ComprarCasaProp);
            cargarComprador(JT_ComprarCasaComprador);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        JP_CRUD.setVisible(false);
        JP_Asignar.setVisible(false);
        JP_Reportes.setVisible(false);
        JP_Bitacora.setVisible(false);
        JP_Comprar.setVisible(true);
    }//GEN-LAST:event_JB_ComprarPropiedadAdminMouseClicked

    private void JB_ComprarPropiedadAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_ComprarPropiedadAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JB_ComprarPropiedadAdminActionPerformed

    private void JB_ConfirmarCrearUsuaro1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ConfirmarCrearUsuaro1MouseClicked
        try {
            CrearPropiedadEnMercado();
            JOptionPane.showMessageDialog(null, "Se creo la propiedad exitosamente.");
            TF_CrearNombrePropiedad.setText("");
            TF_CrearCaracteristica.setText("");
            TF_CrearDireccion.setText("");
            TF_CrearCiudad.setText("");
            JS_CrearPrecio.setValue(0);
            JS_CrearHabitaciones.setValue(0);

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JB_ConfirmarCrearUsuaro1MouseClicked

    private void JB_CrearPropiedadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CrearPropiedadesMouseClicked
        JP_CrearPropiedad.setVisible(true);
        JP_ModPropiedad.setVisible(false);
        JP_EliminarPropiedad.setVisible(false);
    }//GEN-LAST:event_JB_CrearPropiedadesMouseClicked

    private void CB_CrearTipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_CrearTipoUsuarioActionPerformed
        if (CB_CrearTipoUsuario.getSelectedItem().toString().equals("vendedor") || CB_CrearTipoUsuario.getSelectedItem().toString().equals("comprador")) {
            jl_TelefonoCompania.setVisible(false);
            TF_CrearTelefono.setVisible(false);
        } else {
            jl_TelefonoCompania.setVisible(true);
            TF_CrearTelefono.setVisible(true);
        }
    }//GEN-LAST:event_CB_CrearTipoUsuarioActionPerformed

    private void JB_ConfirmarCrearUsuaroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ConfirmarCrearUsuaroMouseClicked
        int id = Integer.parseInt(TF_IDUSER.getText());
        String usuario = TF_CrearUser.getText();
        String contraseña = TF_CrearPassword.getText();
        String rol = CB_CrearTipoUsuario.getSelectedItem().toString();
        try {
            crearUsuario(id, usuario, contraseña, rol);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (CB_CrearTipoUsuario.getSelectedItem().toString().equals("agente")) {
            try {
                CrearAgente();
                JOptionPane.showMessageDialog(null, "Se creo el agente con exito.");
                JB_ConfirmarCrearUsuaro.setText("");
                TF_CrearUser.setText("");
                TF_CrearPassword.setText("");
                TF_NombreCrear.setText("");
                TF_CrearDNI.setText("");
                TF_DireccionCrearPersona.setText("");
                TF_CrearCelular.setText("");
                TF_CrearTelefono.setText("");

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_CrearTipoUsuario.getSelectedItem().toString().equals("comprador")) {
            try {
                CrearComprador();
                JOptionPane.showMessageDialog(null, "Se creo el comprador con exito.");
                JB_ConfirmarCrearUsuaro.setText("");
                TF_CrearUser.setText("");
                TF_CrearPassword.setText("");
                TF_NombreCrear.setText("");
                TF_CrearDNI.setText("");
                TF_DireccionCrearPersona.setText("");
                TF_CrearCelular.setText("");
                TF_CrearTelefono.setText("");

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_CrearTipoUsuario.getSelectedItem().toString().equals("vendedor")) {
            try {
                CrearVendedor();
                JOptionPane.showMessageDialog(null, "Se creo el vendedor con exito.");
                JB_ConfirmarCrearUsuaro.setText("");
                TF_CrearUser.setText("");
                TF_CrearPassword.setText("");
                TF_NombreCrear.setText("");
                TF_CrearDNI.setText("");
                TF_DireccionCrearPersona.setText("");
                TF_CrearCelular.setText("");
                TF_CrearTelefono.setText("");

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_JB_ConfirmarCrearUsuaroMouseClicked

    private void JB_ModificarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ModificarUsuarioMouseClicked
        JP_CrearUsuario1.setVisible(false);
        JP_EliminarUsuario1.setVisible(false);
        JP_ModUsuario1.setVisible(true);
    }//GEN-LAST:event_JB_ModificarUsuarioMouseClicked

    private void JB_CrearUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CrearUsuarioMouseClicked
        JP_EliminarUsuario1.setVisible(false);
        JP_ModUsuario1.setVisible(false);
        JP_CrearUsuario1.setVisible(true);

    }//GEN-LAST:event_JB_CrearUsuarioMouseClicked

    private void JT_ModUsuario1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_ModUsuario1MouseClicked
        // AGARRAR EL INDICE DONDE SE LE DIO CLICK A LA TABLA 
        int filaSeleccionada = JT_ModUsuario1.getSelectedRow();
        if (filaSeleccionada != -1) {
            if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("agente")) {
                int dni = (int) JT_ModUsuario1.getValueAt(filaSeleccionada, 0);
                String nombreVendedor = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 1);
                String direccionVendedor = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 2);
                String celularVendedor = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 3);
                String telefono = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 4);
                TF_modDNI.setText("" + dni);
                TF_NombreCrear3.setText(nombreVendedor);
                TF_DireccionCrearPersona3.setText(direccionVendedor);
                TF_CrearCelular3.setText(celularVendedor);
                TF_CrearTelefono3.setText(telefono);
            } else {
                int dni = (int) JT_ModUsuario1.getValueAt(filaSeleccionada, 0);
                String nombreVendedor = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 1);
                String direccionVendedor = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 2);
                String celularVendedor = (String) JT_ModUsuario1.getValueAt(filaSeleccionada, 3);
                TF_modDNI.setText("" + dni);
                TF_NombreCrear3.setText(nombreVendedor);
                TF_DireccionCrearPersona3.setText(direccionVendedor);
                TF_CrearCelular3.setText(celularVendedor);
            }
        }


    }//GEN-LAST:event_JT_ModUsuario1MouseClicked

    private void CB_MODTipoUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_MODTipoUsuario1ActionPerformed
        if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("vendedor") || CB_MODTipoUsuario1.getSelectedItem().toString().equals("comprador")) {
            jl_TelefonoCompania3.setVisible(false);
            TF_CrearTelefono3.setVisible(false);
        } else {
            jl_TelefonoCompania3.setVisible(true);
            TF_CrearTelefono3.setVisible(true);
        }

        if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("agente")) {
            try {
                cargarAgente(JT_ModUsuario1);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("comprador")) {
            try {
                cargarComprador(JT_ModUsuario1);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("vendedor")) {
            try {
                cargarVendedores(JT_ModUsuario1);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_CB_MODTipoUsuario1ActionPerformed

    private void JB_ConfirmarModUsuaro1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ConfirmarModUsuaro1MouseClicked

        if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("agente")) {
            int idAgente = Integer.parseInt(TF_modDNI.getText());
            String nombreAgente = TF_NombreCrear3.getText();
            String direccionAgente = TF_DireccionCrearPersona3.getText();
            String celularAgente = TF_CrearCelular3.getText();
            String telefonoAgente = TF_CrearTelefono3.getText();
            try {
                modificarAgente(idAgente, nombreAgente, direccionAgente, celularAgente, telefonoAgente);
                cargarAgente(JT_ModUsuario1);
                JOptionPane.showMessageDialog(null, "Se modifico el usuario con exito.");
                TF_modDNI.setText("");
                TF_NombreCrear3.setText("");
                TF_DireccionCrearPersona3.setText("");
                TF_CrearCelular3.setText("");
                TF_CrearTelefono3.setText("");
                limpiarCampos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al modificar el agente.");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("comprador")) {
            int idComprador = Integer.parseInt(TF_modDNI.getText());
            String nombreComprador = TF_NombreCrear3.getText();
            String direccionComprador = TF_DireccionCrearPersona3.getText();
            String celularComprador = TF_CrearCelular3.getText();
            try {
                modificarComprador(idComprador, nombreComprador, direccionComprador, celularComprador);
                cargarComprador(JT_ModUsuario1);
                limpiarCampos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al modificar el comprador.");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_MODTipoUsuario1.getSelectedItem().toString().equals("vendedor")) {
            int idVendedor = Integer.parseInt(TF_modDNI.getText());
            String nombreVendedor = TF_NombreCrear3.getText();
            String direccionVendedor = TF_DireccionCrearPersona3.getText();
            String celularVendedor = TF_CrearCelular3.getText();
            try {
                modificarVendedor(idVendedor, nombreVendedor, direccionVendedor, celularVendedor);
                cargarVendedores(JT_ModUsuario1);
                limpiarCampos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al modificar el vendedor.");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_JB_ConfirmarModUsuaro1MouseClicked

    private void JB_ModificarPropiedadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ModificarPropiedadesMouseClicked
        if (CB_ModProp.getSelectedItem().toString().equals("En Mercado")) {
            try {
                cargarPropiedadEnVenta(JT_ModPropiedad);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(true);
            JP_EliminarPropiedad.setVisible(false);
        } else {
            try {
                cargarPropiedadVendida(JT_ModPropiedad);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        JP_ModPropiedad.setVisible(true);
        JP_CrearPropiedad.setVisible(false);
        JP_EliminarPropiedad.setVisible(false);
    }//GEN-LAST:event_JB_ModificarPropiedadesMouseClicked

    private void JB_EliminarPropiedadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_EliminarPropiedadesMouseClicked
        if (CB_ElimProp.getSelectedItem().toString().equals("En Mercado")) {
            try {
                cargarPropiedadEnVenta(JT_EliminarProp);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(false);
            JP_EliminarPropiedad.setVisible(true);
        } else {
            try {
                cargarPropiedadEnVenta(JT_EliminarProp);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(false);
            JP_EliminarPropiedad.setVisible(true);
        }

    }//GEN-LAST:event_JB_EliminarPropiedadesMouseClicked

    private void JB_ConfirmarEliminarUsuario1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ConfirmarEliminarUsuario1MouseClicked
        int filaSeleccionada = JT_EliminarUser4.getSelectedRow();
        if (CB_ELIMTipoUsuario.getSelectedItem().toString().equals("agente")) {
            if (filaSeleccionada != -1) {
                int idAgente = (int) JT_EliminarUser4.getValueAt(filaSeleccionada, 0);
                try {
                    // Llamar al procedimiento almacenado para eliminar el vendedor
                    eliminarAgente(idAgente);
                    JOptionPane.showMessageDialog(null, "Se elimino el usuario con exito.");
                    // Actualizar la tabla después de la eliminación
                    cargarAgente(JT_EliminarUser4);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el agente.");

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un agente para eliminar.");
            }
        } else if (CB_ELIMTipoUsuario.getSelectedItem().toString().equals("comprador")) {
            if (filaSeleccionada != -1) {
                int idComprador = (int) JT_EliminarUser4.getValueAt(filaSeleccionada, 0);
                try {
                    // Llamar al procedimiento almacenado para eliminar el vendedor
                    eliminarComprador(idComprador);
                    // Actualizar la tabla después de la eliminación
                    cargarComprador(JT_EliminarUser4);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el comprador.");

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un comprador para eliminar.");
            }
        } else if (CB_ELIMTipoUsuario.getSelectedItem().toString().equals("vendedor")) {
            if (filaSeleccionada != -1) {
                int idVendedor = (int) JT_EliminarUser4.getValueAt(filaSeleccionada, 0);
                try {
                    // Llamar al procedimiento almacenado para eliminar el vendedor
                    eliminarVendedor(idVendedor);
                    // Actualizar la tabla después de la eliminación
                    cargarVendedores(JT_EliminarUser4);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el vendedor.");

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un vendedor para eliminar.");
            }
        }
    }//GEN-LAST:event_JB_ConfirmarEliminarUsuario1MouseClicked

    private void JP_AsignarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JP_AsignarMouseClicked

    }//GEN-LAST:event_JP_AsignarMouseClicked

    private void JT_SelVendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_SelVendMouseClicked
        int filaSeleccionada = JT_SelVend.getSelectedRow();
        if (filaSeleccionada != -1) {
//                int idProp = (int) JT_SelVend.getValueAt(filaSeleccionada, 0);
//                String nombre = (String) JT_SelVend.getValueAt(filaSeleccionada, 1);
//                String ciudad = (String) JT_SelVend.getValueAt(filaSeleccionada, 2);
//                String direccion = (String) JT_SelVend.getValueAt(filaSeleccionada, 3);
//                int cant_dorm = (int) JT_SelVend.getValueAt(filaSeleccionada, 4);
//                String caracteristicas = (String) JT_SelVend.getValueAt(filaSeleccionada, 5);
//                int precio = (int) JT_SelVend.getValueAt(filaSeleccionada, 6);
        }
    }//GEN-LAST:event_JT_SelVendMouseClicked

    private void JT_AsignarSelAGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_AsignarSelAGMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_AsignarSelAGMouseClicked

    private void JT_AsignarCasa2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_AsignarCasa2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_AsignarCasa2MouseClicked

    private void JB_AsignarCasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_AsignarCasaMouseClicked
        int filaSeleccionada = JT_AsignarCasa2.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idPropiedad = (int) JT_AsignarCasa2.getValueAt(filaSeleccionada, 0); // Suponiendo que la primera columna es el ID de la propiedad

            // Obtener ID del vendedor seleccionado
            int filaVendedorSeleccionado = JT_SelVend.getSelectedRow();
            int idVendedor = (filaVendedorSeleccionado != -1) ? (int) JT_SelVend.getValueAt(filaVendedorSeleccionado, 0) : -1;

            // Obtener ID del agente seleccionado
            int filaAgenteSeleccionado = JT_AsignarSelAG.getSelectedRow();
            int idAgente = (filaAgenteSeleccionado != -1) ? (int) JT_AsignarSelAG.getValueAt(filaAgenteSeleccionado, 0) : -1;

            try {
                asignarIdsAPropiedad(idPropiedad, idVendedor, idAgente);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_JB_AsignarCasaMouseClicked

    private void CB_ElimPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_ElimPropActionPerformed
        if (CB_ElimProp.getSelectedItem().toString().equals("En Mercado")) {
            try {
                cargarPropiedadEnVenta(JT_EliminarProp);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(false);
            JP_EliminarPropiedad.setVisible(true);
        } else {
            try {
                cargarPropiedadVendida(JT_EliminarProp);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(false);
            JP_EliminarPropiedad.setVisible(true);
        }
    }//GEN-LAST:event_CB_ElimPropActionPerformed

    private void JB_ConfirmarEliminarPropiedadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ConfirmarEliminarPropiedadMouseClicked
        int filaSeleccionada = JT_EliminarProp.getSelectedRow();
        if (CB_ElimProp.getSelectedItem().toString().equals("En Mercado")) {
            if (filaSeleccionada != -1) {
                int idPropEnVenta = (int) JT_EliminarProp.getValueAt(filaSeleccionada, 0);
                try {
                    // Llamar al procedimiento almacenado para eliminar el vendedor
                    eliminarPropiedadEnVenta(idPropEnVenta);
                    JOptionPane.showMessageDialog(null, "Se elimino la propiedad exitosamente.");
                    // Actualizar la tabla después de la eliminación
                    cargarPropiedadEnVenta(JT_EliminarProp);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar la propiedad.");

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un agente para eliminar.");
            }
        } else if (CB_ElimProp.getSelectedItem().toString().equals("Vendidas")) {
            if (filaSeleccionada != -1) {
                int idPropVendida = (int) JT_EliminarProp.getValueAt(filaSeleccionada, 0);
                try {
                    // Llamar al procedimiento almacenado para eliminar el vendedor
                    eliminarPropiedadVendida(idPropVendida);
                    // Actualizar la tabla después de la eliminación
                    cargarPropiedadVendida(JT_EliminarProp);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el comprador.");

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un comprador para eliminar.");
            }
        }
    }//GEN-LAST:event_JB_ConfirmarEliminarPropiedadMouseClicked

    private void JB_EliminarPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_EliminarPropiedadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JB_EliminarPropiedadesActionPerformed

    private void CB_ModPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_ModPropActionPerformed
        if (CB_ModProp.getSelectedItem().toString().equals("En Mercado")) {
            try {
                cargarPropiedadEnVenta(JT_ModPropiedad);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            TF_ModAgenteProp.setText("");
            JB_cambiarAgente.setVisible(true);
            TF_ModAgenteProp.setVisible(true);
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(true);
            JP_EliminarPropiedad.setVisible(false);

        } else {
            try {
                cargarPropiedadVendida(JT_ModPropiedad);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            JB_cambiarAgente.setVisible(false);
            TF_ModAgenteProp.setVisible(false);
            JP_CrearPropiedad.setVisible(false);
            JP_ModPropiedad.setVisible(true);
            JP_EliminarPropiedad.setVisible(false);
        }
    }//GEN-LAST:event_CB_ModPropActionPerformed

    private void JT_ModPropiedadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_ModPropiedadMouseClicked
        int filaSeleccionada = JT_ModPropiedad.getSelectedRow();
        if (CB_ModProp.getSelectedItem().toString().equals("En Mercado")) {
            if (filaSeleccionada != -1) {
                int ID = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 0);
                String nombre = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 1);
                String ciudad = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 2);
                String direccion = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 3);
                int habi = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 4);
                String caracteristicas = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 5);
                int precio = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 6);
                int IDAGENTE = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 7);

                TF_ModPROPID.setText("" + ID);
                TF_ModDirec.setText(direccion);
                TF_ModCiudad1.setText(ciudad);
                TF_ModNombre.setText(nombre);
                TF_ModCaracteristicas.setText(caracteristicas);
                SP_ModificarPrecio.setValue((int) precio);
                JS_ModHabitaciones.setValue((int) habi);
                TF_ModAgenteProp.setText("" + IDAGENTE);
            }
        } else {
            if (filaSeleccionada != -1) {
                int ID = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 0);
                String nombre = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 1);
                String ciudad = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 2);
                String direccion = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 3);
                int habi = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 4);
                String caracteristicas = (String) JT_ModPropiedad.getValueAt(filaSeleccionada, 5);
                int precio = (int) JT_ModPropiedad.getValueAt(filaSeleccionada, 6);

                TF_ModPROPID.setText("" + ID);
                TF_ModDirec.setText(direccion);
                TF_ModCiudad1.setText(ciudad);
                TF_ModNombre.setText(nombre);
                TF_ModCaracteristicas.setText(caracteristicas);
                SP_ModificarPrecio.setValue((int) precio);
                JS_ModHabitaciones.setValue((int) habi);
            }
        }

    }//GEN-LAST:event_JT_ModPropiedadMouseClicked

    private void JB_ConfirmarModPropiedadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_ConfirmarModPropiedadMouseClicked
        if (CB_ModProp.getSelectedItem().toString().equals("En Mercado")) {
            int id = Integer.parseInt(TF_ModPROPID.getText());
            String nombre = TF_ModNombre.getText();
            String ciudad = TF_ModCiudad1.getText();
            String direccion = TF_ModDirec.getText();
            int habi = (int) JS_ModHabitaciones.getValue();
            String caracteristicas = TF_ModCaracteristicas.getText();
            int precio = (int) SP_ModificarPrecio.getValue();
            int idAgente = Integer.parseInt(TF_ModAgenteProp.getText());

            try {
                modificarPropiedad_enMercado(id, nombre, ciudad, direccion, habi, caracteristicas, precio, idAgente);
                cargarPropiedadEnVenta(JT_ModPropiedad);
                JOptionPane.showMessageDialog(null, "Se modifico la propiedad exitosamente.");
                TF_ModNombre.setText("");
                TF_ModCiudad1.setText("");
                TF_ModPROPID.setText("");
                TF_ModCiudad1.setText("");
                TF_ModDirec.setText("");
                TF_ModCaracteristicas.setText("");
                TF_ModAgenteProp.setText("");
                JS_ModHabitaciones.setValue(0);
                SP_ModificarPrecio.setValue(0);

                limpiarCampos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al modificar la propiedad.");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            int id = Integer.parseInt(TF_ModPROPID.getText());
            String nombre = TF_ModNombre.getText();
            String ciudad = TF_ModCiudad1.getText();
            String direccion = TF_ModDirec.getText();
            int habi = (int) JS_ModHabitaciones.getValue();
            String caracteristicas = TF_ModCaracteristicas.getText();
            int precio = (int) SP_ModificarPrecio.getValue();
            try {
                modificarPropiedad_Vendida(id, nombre, ciudad, direccion, habi, caracteristicas, precio);
                cargarPropiedadVendida(JT_ModPropiedad);
                limpiarCampos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al modificar la propiedad.");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_JB_ConfirmarModPropiedadMouseClicked

    private void JB_cambiarAgenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_cambiarAgenteActionPerformed
        try {
            cargarAgente(JT_ModProp_NuevoAgente);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        ElegirnuevoAgente.setVisible(true);
        ElegirnuevoAgente.pack();
        ElegirnuevoAgente.setLocationRelativeTo(null);
    }//GEN-LAST:event_JB_cambiarAgenteActionPerformed

    private void JT_ModProp_NuevoAgenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_ModProp_NuevoAgenteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_ModProp_NuevoAgenteMouseClicked

    private void ConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmarActionPerformed
        ElegirnuevoAgente.setVisible(false);
        int filaSeleccionada = JT_ModProp_NuevoAgente.getSelectedRow();
        if (filaSeleccionada != -1) {
            TF_ModAgenteProp.setText("" + JT_ModProp_NuevoAgente.getValueAt(filaSeleccionada, 0));
        }
    }//GEN-LAST:event_ConfirmarActionPerformed

    private void CB_ModPropMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CB_ModPropMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_ModPropMouseMoved

    private void CB_ModPropMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CB_ModPropMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_ModPropMouseDragged

    private void JP_ReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JP_ReportesMouseClicked

    }//GEN-LAST:event_JP_ReportesMouseClicked

    private void JB_COnfirmarComprarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_COnfirmarComprarMouseClicked

        int filaPropiedadSeleccionada = JT_ComprarCasaProp.getSelectedRow();
        int filaCompradorSeleccionado = JT_ComprarCasaComprador.getSelectedRow();
        if (filaPropiedadSeleccionada != -1 && filaCompradorSeleccionado != -1) {
            try {
                int idPropiedad = (int) JT_ComprarCasaProp.getValueAt(filaPropiedadSeleccionada, 0);
                int idComprador = (int) JT_ComprarCasaComprador.getValueAt(filaCompradorSeleccionado, 0);

                transferirPropiedadAVendidas(idPropiedad, idComprador);
                cargarPropiedadEnVenta(JT_ComprarCasaProp);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una propiedad y un comprador.");
        }


    }//GEN-LAST:event_JB_COnfirmarComprarMouseClicked

    private void JT_ComprarCasaPropMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_ComprarCasaPropMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_ComprarCasaPropMouseClicked

    private void JB_PropiedadesAsignadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_PropiedadesAsignadasMouseClicked

        try {
            // TODO add your handling code here:
            cargarPropiedades("agente", "En Mercado");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JB_PropiedadesAsignadasMouseClicked

    private void JB_EliminarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_EliminarUsuarioMouseClicked
        JP_CrearUsuario1.setVisible(false);
        JP_EliminarUsuario1.setVisible(true);
        JP_ModUsuario1.setVisible(false);
    }//GEN-LAST:event_JB_EliminarUsuarioMouseClicked

    private void JB_CerrarSesionAgenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CerrarSesionAgenteMouseClicked
        JF_Agente.setVisible(false);
        JP_PropiedadesVendidas.setVisible(false);
        JF_Login.pack();
        JF_Login.setLocationRelativeTo(this);
        JF_Login.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JF_Login.setVisible(true);
    }//GEN-LAST:event_JB_CerrarSesionAgenteMouseClicked

    private void JB_CerrarSesionVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CerrarSesionVendedorMouseClicked
        JF_Vendedor.setVisible(false);
        JP_PropiedadesEnMercadoVendedordor.setVisible(false);
        JF_Login.pack();
        JF_Login.setLocationRelativeTo(this);
        JF_Login.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JF_Login.setVisible(true);
    }//GEN-LAST:event_JB_CerrarSesionVendedorMouseClicked

    private void JB_CerrarSesionCompradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CerrarSesionCompradorMouseClicked
        JF_Comprador.setVisible(false);
        JP_PropiedadesEnMercadoComprador.setVisible(false);
        JF_Login.pack();
        JF_Login.setLocationRelativeTo(this);
        JF_Login.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JF_Login.setVisible(true);
    }//GEN-LAST:event_JB_CerrarSesionCompradorMouseClicked

    private void JB_CerrarSesionAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JB_CerrarSesionAdminMouseClicked
        JF_Admin.setVisible(false);
        JF_Login.pack();
        JF_Login.setLocationRelativeTo(this);
        JF_Login.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JF_Login.setVisible(true);
    }//GEN-LAST:event_JB_CerrarSesionAdminMouseClicked

    private void CB_ELIMTipoUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CB_ELIMTipoUsuarioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_ELIMTipoUsuarioMouseClicked

    private void CB_ELIMTipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_ELIMTipoUsuarioActionPerformed
        if (CB_ELIMTipoUsuario.getSelectedItem().toString().equals("agente")) {
            try {
                cargarAgente(JT_EliminarUser4);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_ELIMTipoUsuario.getSelectedItem().toString().equals("comprador")) {
            try {
                cargarComprador(JT_EliminarUser4);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CB_ELIMTipoUsuario.getSelectedItem().toString().equals("vendedor")) {
            try {
                cargarVendedores(JT_EliminarUser4);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_CB_ELIMTipoUsuarioActionPerformed

    private void JT_PropiedadeEnMercadoVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_PropiedadeEnMercadoVendedorMouseClicked

        // Obtener el índice de la fila seleccionada
        int selectedRow = JT_PropiedadeEnMercadoVendedor.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener el nombre del elemento seleccionado
            String elementoSeleccionado = (String) JT_PropiedadeEnMercadoVendedor.getValueAt(selectedRow, 1);
            // Establecer la imagen correspondiente al elemento seleccionado
            ImageIcon imagen = imagenes[selectedRow];
            // Establecer la imagen en el JLabel
            JL_ImagenVendedor.setIcon(imagen);
        }

// Cargar las imágenes

    }//GEN-LAST:event_JT_PropiedadeEnMercadoVendedorMouseClicked

    private void JT_PropiedadesEnMercadoCompradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_PropiedadesEnMercadoCompradorMouseClicked
        int selectedRow = JT_PropiedadesEnMercadoComprador.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener el nombre del elemento seleccionado
            String elementoSeleccionado = (String) JT_PropiedadesEnMercadoComprador.getValueAt(selectedRow, 1);
            // Establecer la imagen correspondiente al elemento seleccionado
            ImageIcon imagen = imagenes[selectedRow];
            // Establecer la imagen en el JLabel
            JL_ImagenComprador.setIcon(imagen);
        }
    }//GEN-LAST:event_JT_PropiedadesEnMercadoCompradorMouseClicked

    public void cargarPropiedadesAsignadas(int noIdentidadAgente) throws ClassNotFoundException, SQLException {
        Connection con = conectarMySQL();
        String sql = "SELECT * FROM propiedades_en_mercado WHERE noIdentidad_Agente = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, noIdentidadAgente);
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Propiedad");
        model.addColumn("Nombre");
        model.addColumn("Ciudad");
        model.addColumn("Dirección");
        model.addColumn("Cantidad de Dormitorios");
        model.addColumn("Características");
        model.addColumn("Precio");
        model.addColumn("Fecha de Publicación");
        while (rs.next()) {
            // Obtener los valores de cada columna
            int idPropiedad = rs.getInt("idPropiedad");
            String nombre = rs.getString("nombre");
            String ciudad = rs.getString("ciudad");
            String direccion = rs.getString("direccion");
            int cantidadDormitorios = rs.getInt("cantidadDormitorios");
            String caracteristicas = rs.getString("caracteristicas");
            int precio = rs.getInt("precio");
            java.sql.Date fechaPublicacion = rs.getDate("fechaPublicacion");

            model.addRow(new Object[]{idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, fechaPublicacion});
        }

        JT_PropiedadesVendidasAgente.setModel(model);

        rs.close();
        pstmt.close();
        con.close();
    }

    void CrearPropiedadEnMercado() throws SQLException, ClassNotFoundException {
        try {
            Connection conn = conectarMySQL();
            CallableStatement cs = conn.prepareCall("{call add_propiedad_en_mercado(?,?,?,?,?,?,?,?,?)}");

            cs.setString(1, TF_CrearNombrePropiedad.getText());
            cs.setString(2, TF_CrearCiudad.getText());
            cs.setString(3, TF_CrearDireccion.getText());
            cs.setInt(4, (int) JS_CrearHabitaciones.getValue());
            cs.setString(5, TF_CrearCaracteristica.getText());
            cs.setInt(6, (int) JS_CrearPrecio.getValue());
            cs.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            //cs.setString(8, "");
            //cs.setString(9, "");
            cs.execute();
            cs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void transferirPropiedadAVendidas(int idPropiedad, int idComprador) throws ClassNotFoundException {
        try {
            // Obtener la fecha de venta actual
            Connection connection = conectarMySQL();

            // SQL para insertar la propiedad vendida con los datos de la propiedad en mercado
            String sqlInsert = "INSERT INTO propiedades_vendidas (idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, fechaPublicacion, fechaVenta, noIdentidad_Agente, noIdentidad_Vendedor, noIdentidad_Comprador, comisionVenta) "
                    + "SELECT idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, fechaPublicacion, ?, noIdentidad_Agente, noIdentidad_Vendedor, ?, ? "
                    + "FROM propiedades_en_mercado WHERE idPropiedad = ?";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setInt(2, idComprador);
            statement.setInt(3, 1000);
            statement.setInt(4, idPropiedad);

            // Ejecutar la inserción
            int filasInsertadas = statement.executeUpdate();

            // Verificar si se insertó correctamente
            if (filasInsertadas > 0) {
                // Eliminar la propiedad de la tabla propiedades_en_mercado
                String sqlDelete = "DELETE FROM propiedades_en_mercado WHERE idPropiedad = ?";
                PreparedStatement statementDelete = connection.prepareStatement(sqlDelete);
                statementDelete.setInt(1, idPropiedad);
                statementDelete.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo transferir la propiedad a la tabla de propiedades vendidas.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al transferir la propiedad a la tabla de propiedades vendidas.");
        }
    }

    private void asignarIdsAPropiedad(int idPropiedad, int idVendedor, int idAgente) throws ClassNotFoundException {
        try {
            // Preparar la sentencia SQL de actualización
            String sql = "UPDATE propiedades_en_mercado SET noIdentidad_Agente = ?, noIdentidad_Vendedor = ? WHERE idPropiedad = ?";

            // Preparar la declaración
            Connection connection = conectarMySQL();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idAgente);
            statement.setInt(2, idVendedor);
            statement.setInt(3, idPropiedad);

            // Ejecutar la actualización
            int filasActualizadas = statement.executeUpdate();

            // Verificar si la actualización fue exitosa
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Los IDs se han asignado correctamente a la propiedad.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar los IDs a la propiedad.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al asignar IDs a la propiedad.");
        }
    }

    private void modificarVendedor(int dni, String nombreVendedor, String direccionVendedor, String celularVendedor) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL mod_vendedor(?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, dni);
        statement.setString(2, nombreVendedor);
        statement.setString(3, direccionVendedor);
        statement.setString(4, celularVendedor);
        statement.execute();
    }

    private void modificarPropiedad_enMercado(int id, String nombre, String ciudad, String direccion, int habitaciones, String caracteristicas, int precio, int id_Agente) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL mod_propiedad_en_mercado(?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, ciudad);
        statement.setString(4, direccion);
        statement.setInt(5, habitaciones);
        statement.setString(6, caracteristicas);
        statement.setInt(7, precio);
        statement.setInt(8, id_Agente);
        statement.execute();
    }

    private void modificarPropiedad_Vendida(int id, String nombre, String ciudad, String direccion, int habitaciones, String caracteristicas, int precio) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL mod_propiedad_vendida(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, ciudad);
        statement.setString(4, direccion);
        statement.setInt(5, habitaciones);
        statement.setString(6, caracteristicas);
        statement.setInt(7, precio);
        statement.execute();
    }

    private void modificarComprador(int dni, String nombreComprador, String direccionComprador, String celularComprador) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL mod_comprador(?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, dni);
        statement.setString(2, nombreComprador);
        statement.setString(3, direccionComprador);
        statement.setString(4, celularComprador);
        statement.execute();
    }

    private void modificarAgente(int dni, String nombreAgente, String direccionAgente, String celularAgente, String telefonoOficina) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL mod_agente(?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, dni);
        statement.setString(2, nombreAgente);
        statement.setString(3, direccionAgente);
        statement.setString(4, celularAgente);
        statement.setString(5, telefonoOficina);
        statement.execute();
    }

    private void eliminarVendedor(int idVendedor) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL eliminar_vendedor(?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, idVendedor);
        statement.execute();
    }

    private void eliminarComprador(int idComprador) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL eliminar_comprador(?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, idComprador);
        statement.execute();
    }

    private void eliminarAgente(int idAgente) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL eliminar_agente(?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, idAgente);
        statement.execute();
    }

    private void eliminarPropiedadEnVenta(int idPropEnVenta) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL eliminar_propiedad_en_mercado(?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, idPropEnVenta);
        statement.execute();
    }

    private void eliminarPropiedadVendida(int idPropVendida) throws SQLException, ClassNotFoundException {
        Connection connection = conectarMySQL();
        String procedimiento = "{CALL eliminar_propiedad_vendida(?)}";
        CallableStatement statement = connection.prepareCall(procedimiento);
        statement.setInt(1, idPropVendida);
        statement.execute();
    }

    private void limpiarCampos() {
        TF_modDNI.setText("");
        TF_NombreCrear3.setText("");
        TF_DireccionCrearPersona3.setText("");
        TF_CrearCelular3.setText("");
        TF_CrearTelefono3.setText("");
    }

    void CrearAgente() throws SQLException, ClassNotFoundException {
        try {
            Connection conn = conectarMySQL();
            CallableStatement cs = conn.prepareCall("{call add_agente(?,?,?,?,?,?)}");

            cs.setInt(1, Integer.parseInt(TF_CrearDNI.getText()));
            cs.setInt(2, Integer.parseInt(TF_IDUSER.getText()));
            cs.setString(3, TF_NombreCrear.getText());
            cs.setString(4, TF_DireccionCrearPersona.getText());
            cs.setInt(5, Integer.parseInt(TF_CrearCelular.getText()));
            cs.setString(6, TF_CrearTelefono.getText());
            cs.execute();
            cs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    void CrearComprador() throws SQLException, ClassNotFoundException {
        try {
            Connection conn = conectarMySQL();
            CallableStatement cs = conn.prepareCall("{call add_comprador(?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(TF_CrearDNI.getText()));
            cs.setInt(2, Integer.parseInt(TF_IDUSER.getText()));
            cs.setString(3, TF_NombreCrear.getText());
            cs.setString(4, TF_DireccionCrearPersona.getText());
            cs.setInt(5, Integer.parseInt(TF_CrearCelular.getText()));
            cs.execute();
            cs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    void CrearVendedor() throws SQLException, ClassNotFoundException {
        try {
            Connection conn = conectarMySQL();
            CallableStatement cs = conn.prepareCall("{call add_vendedor(?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(TF_CrearDNI.getText()));
            cs.setInt(2, Integer.parseInt(TF_IDUSER.getText()));
            cs.setString(3, TF_NombreCrear.getText());
            cs.setString(4, TF_DireccionCrearPersona.getText());
            cs.setInt(5, Integer.parseInt(TF_CrearCelular.getText()));
            cs.execute();
            cs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void cargarVendedores(JTable tabla) throws ClassNotFoundException {
        String consulta = "SELECT noIdentidad, nombre, direccion, celular FROM vendedores";
        String[] columnas = {"ID", "Nombre", "Direccion", "Celular"};
        DefaultTableModel dt = new DefaultTableModel(columnas, 0);
        try {
            Connection connection = conectarMySQL();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                int id = resultSet.getInt("noIdentidad");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String celular = resultSet.getString("celular");
                dt.addRow(new Object[]{id, nombre, direccion, celular});
                tabla.setModel(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los vendedores.");
        }
    }

    private void cargarComprador(JTable tabla) throws ClassNotFoundException {
        String consulta = "SELECT noIdentidad, nombre, direccion, celular FROM compradores";
        String[] columnas = {"ID", "Nombre", "Direccion", "Celular"};
        DefaultTableModel dt = new DefaultTableModel(columnas, 0);
        try {
            Connection connection = conectarMySQL();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                int id = resultSet.getInt("noIdentidad");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String celular = resultSet.getString("celular");
                dt.addRow(new Object[]{id, nombre, direccion, celular});
                tabla.setModel(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los vendedores.");
        }
    }

    private void cargarAgente(JTable tabla) throws ClassNotFoundException {
        String consulta = "SELECT noIdentidad, nombre, direccion, celular, telefonoOficina FROM agentes";
        String[] columnas = {"ID", "Nombre", "Direccion", "Celular", "Telefono Oficina"};
        DefaultTableModel dt = new DefaultTableModel(columnas, 0);
        try {
            Connection connection = conectarMySQL();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                int id = resultSet.getInt("noIdentidad");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String celular = resultSet.getString("celular");
                String telefono = resultSet.getString("telefonoOficina");
                dt.addRow(new Object[]{id, nombre, direccion, celular, telefono});
                tabla.setModel(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los vendedores.");
        }
    }

    private void cargarPropiedadEnVenta(JTable tabla) throws ClassNotFoundException {
        String consulta = "SELECT idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, noIdentidad_Agente FROM propiedades_en_mercado";
        String[] columnas = {"idPropiedad", "nombre", "ciudad", "direccion", "cantidadDormitorios", "caracteristicas", "precio", "noIdentidad_Agente"};
        DefaultTableModel dt = new DefaultTableModel(columnas, 0);
        try {
            Connection connection = conectarMySQL();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                int id = resultSet.getInt("idPropiedad");
                String nombre = resultSet.getString("nombre");
                String ciudad = resultSet.getString("ciudad");
                String direccion = resultSet.getString("direccion");
                int cantidadDormitorios = resultSet.getInt("cantidadDormitorios");
                String caracteristicas = resultSet.getString("caracteristicas");
                int precio = resultSet.getInt("precio");
                int id_Agente = resultSet.getInt("noIdentidad_Agente");
                dt.addRow(new Object[]{id, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio, id_Agente});
                tabla.setModel(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los propiedades.");
        }
    }

    private void cargarPropiedadVendida(JTable tabla) throws ClassNotFoundException {
        String consulta = "SELECT idPropiedad, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio FROM propiedades_vendidas";
        String[] columnas = {"idPropiedad", "nombre", "ciudad", "direccion", "cantidadDormitorios", "caracteristicas", "precio"};
        DefaultTableModel dt = new DefaultTableModel(columnas, 0);
        try {
            Connection connection = conectarMySQL();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                int id = resultSet.getInt("idPropiedad");
                String nombre = resultSet.getString("nombre");
                String ciudad = resultSet.getString("ciudad");
                String direccion = resultSet.getString("direccion");
                int cantidadDormitorios = resultSet.getInt("cantidadDormitorios");
                String caracteristicas = resultSet.getString("caracteristicas");
                int precio = resultSet.getInt("precio");
                dt.addRow(new Object[]{id, nombre, ciudad, direccion, cantidadDormitorios, caracteristicas, precio});
                tabla.setModel(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los propiedades.");
        }
    }

    private void cargarImagenes() { // Carga imagenes dentro del paquete img para setearselo a las propiedades seleccionadas
        imagenes = new ImageIcon[20]; // Número de imágenes
        imagenes[0] = new ImageIcon(getClass().getResource("/img/imagen1.png"));
        imagenes[1] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0047.png"));
        imagenes[2] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0052.png.png"));
        imagenes[3] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0048.png"));
        imagenes[4] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0049.png"));
        imagenes[5] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0050.png"));
        imagenes[6] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0051.png"));
        imagenes[7] = new ImageIcon(getClass().getResource("/img/imagen12.png"));
        imagenes[8] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0053.png"));
        imagenes[9] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0054.png"));
        imagenes[10] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0055.png"));
        imagenes[11] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0045.png"));
        imagenes[12] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0057.png"));
        imagenes[13] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0058.png"));
        imagenes[14] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0059.png"));
        imagenes[15] = new ImageIcon(getClass().getResource("/img/IMG-20240317-WA0060.png"));

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CB_CrearTipoUsuario;
    private javax.swing.JComboBox<String> CB_ELIMTipoUsuario;
    private javax.swing.JComboBox<String> CB_ElimProp;
    private javax.swing.JComboBox<String> CB_MODTipoUsuario1;
    private javax.swing.JComboBox<String> CB_ModProp;
    private javax.swing.JButton Confirmar;
    private javax.swing.JFrame ElegirnuevoAgente;
    private javax.swing.JButton JB_AsignarCasa;
    private javax.swing.JButton JB_AsignarPropiedadAdmin;
    private javax.swing.JButton JB_BitacoraAdmin;
    private javax.swing.JButton JB_COnfirmarComprar;
    private javax.swing.JButton JB_CRUDUsuario;
    private javax.swing.JButton JB_CRUDUsuario3;
    private javax.swing.JButton JB_CerrarSesionAdmin;
    private javax.swing.JPanel JB_CerrarSesionAgente;
    private javax.swing.JPanel JB_CerrarSesionComprador;
    private javax.swing.JPanel JB_CerrarSesionVendedor;
    private javax.swing.JButton JB_ComprarPropiedadAdmin;
    private javax.swing.JButton JB_ConfirmarCrearUsuaro;
    private javax.swing.JButton JB_ConfirmarCrearUsuaro1;
    private javax.swing.JButton JB_ConfirmarEliminarPropiedad;
    private javax.swing.JButton JB_ConfirmarEliminarUsuario1;
    private javax.swing.JButton JB_ConfirmarModPropiedad;
    private javax.swing.JButton JB_ConfirmarModUsuaro1;
    private javax.swing.JButton JB_CrearPropiedades;
    private javax.swing.JButton JB_CrearUsuario;
    private javax.swing.JButton JB_CrudAdmin;
    private javax.swing.JButton JB_EliminarPropiedades;
    private javax.swing.JButton JB_EliminarUsuario;
    private javax.swing.JButton JB_LogIn;
    private javax.swing.JPanel JB_Login;
    private javax.swing.JButton JB_ModificarPropiedades;
    private javax.swing.JButton JB_ModificarUsuario;
    private javax.swing.JButton JB_PropiedadesAsignadas;
    private javax.swing.JButton JB_PropiedadesCompradasComprador;
    private javax.swing.JButton JB_PropiedadesEnMercadoComprador;
    private javax.swing.JButton JB_PropiedadesEnMercadoVendedor;
    private javax.swing.JButton JB_PropiedadesVendidas;
    private javax.swing.JButton JB_PropiedadesVendidasVendedor;
    private javax.swing.JButton JB_ReportesAdmin;
    private javax.swing.JButton JB_cambiarAgente;
    private javax.swing.JDialog JD_PropiedadesCRUD;
    private javax.swing.JFrame JF_Admin;
    private javax.swing.JFrame JF_Agente;
    private javax.swing.JFrame JF_Comprador;
    private javax.swing.JFrame JF_Login;
    private javax.swing.JFrame JF_UsuarioCRUD;
    private javax.swing.JFrame JF_Vendedor;
    private javax.swing.JLabel JL_ImagenComprador;
    private javax.swing.JLabel JL_ImagenVendedor;
    private javax.swing.JPanel JP_Asignar;
    private javax.swing.JPanel JP_Bitacora;
    private javax.swing.JPanel JP_CRUD;
    private javax.swing.JPanel JP_Comprar;
    private javax.swing.JPanel JP_CrearPropiedad;
    private javax.swing.JPanel JP_CrearUsuario1;
    private javax.swing.JPanel JP_EliminarPropiedad;
    private javax.swing.JPanel JP_EliminarUsuario1;
    private javax.swing.JPanel JP_ModPropiedad;
    private javax.swing.JPanel JP_ModUsuario1;
    private javax.swing.JPanel JP_PropiedadesEnMercadoComprador;
    private javax.swing.JPanel JP_PropiedadesEnMercadoVendedordor;
    private javax.swing.JPanel JP_PropiedadesVendidas;
    private javax.swing.JPanel JP_Reportes;
    private javax.swing.JSpinner JS_CrearHabitaciones;
    private javax.swing.JSpinner JS_CrearPrecio;
    private javax.swing.JSpinner JS_ModHabitaciones;
    private javax.swing.JTable JT_AsignarCasa2;
    private javax.swing.JTable JT_AsignarSelAG;
    private javax.swing.JTable JT_Bitacora;
    private javax.swing.JTable JT_ComprarCasaComprador;
    private javax.swing.JTable JT_ComprarCasaProp;
    private javax.swing.JTable JT_EliminarProp;
    private javax.swing.JTable JT_EliminarUser4;
    private javax.swing.JTable JT_ModProp_NuevoAgente;
    private javax.swing.JTable JT_ModPropiedad;
    private javax.swing.JTable JT_ModUsuario1;
    private javax.swing.JTable JT_PropiedadeEnMercadoVendedor;
    private javax.swing.JTable JT_PropiedadesEnMercadoComprador;
    private javax.swing.JTable JT_PropiedadesVendidasAgente;
    private javax.swing.JTable JT_SelVend;
    private javax.swing.JTable JT_Vista1;
    private javax.swing.JTable JT_Vista2;
    private javax.swing.JTable JT_Vista3;
    private javax.swing.JTable JT_Vista4;
    private javax.swing.JTable JT_Vista5;
    private javax.swing.JTable JT_Vista6;
    private javax.swing.JTable JT_Vista7;
    private javax.swing.JTable JT_Vista8;
    private javax.swing.JSpinner SP_ModificarPrecio;
    private javax.swing.JTextField TF_CrearCaracteristica;
    private javax.swing.JTextField TF_CrearCelular;
    private javax.swing.JTextField TF_CrearCelular3;
    private javax.swing.JTextField TF_CrearCiudad;
    private javax.swing.JTextField TF_CrearDNI;
    private javax.swing.JTextField TF_CrearDireccion;
    private javax.swing.JTextField TF_CrearNombrePropiedad;
    private javax.swing.JTextField TF_CrearPassword;
    private javax.swing.JTextField TF_CrearTelefono;
    private javax.swing.JTextField TF_CrearTelefono3;
    private javax.swing.JTextField TF_CrearUser;
    private javax.swing.JTextField TF_DireccionCrearPersona;
    private javax.swing.JTextField TF_DireccionCrearPersona3;
    private javax.swing.JTextField TF_IDUSER;
    private javax.swing.JTextField TF_ModAgenteProp;
    private javax.swing.JTextField TF_ModCaracteristicas;
    private javax.swing.JTextField TF_ModCiudad1;
    private javax.swing.JTextField TF_ModDirec;
    private javax.swing.JTextField TF_ModNombre;
    private javax.swing.JTextField TF_ModPROPID;
    private javax.swing.JTextField TF_NombreCrear;
    private javax.swing.JTextField TF_NombreCrear3;
    private javax.swing.JTextField TF_modDNI;
    private javax.swing.JPanel compra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel jl_TelefonoCompania;
    private javax.swing.JLabel jl_TelefonoCompania1;
    private javax.swing.JLabel jl_TelefonoCompania3;
    private javax.swing.JPanel renta;
    private javax.swing.JTextField tf_login;
    private javax.swing.JPasswordField tf_password;
    private javax.swing.JPanel vende;
    // End of variables declaration//GEN-END:variables
private int noIdentidadUsuarioSesion; // Almacena el noIdentidad del usuario que ha iniciado sesión
private String rolUsuarioSesion; // Almacena el rol del usuario que ha iniciado sesión
private ImageIcon[] imagenes; // Variable global para almacenar las imagenes 
}
