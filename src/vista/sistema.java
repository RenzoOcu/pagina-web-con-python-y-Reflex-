/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;


import Reportes.Grafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.ClienteDao;
import modelo.Detalle;
import modelo.Eventos;
import modelo.Productos;
import modelo.ProductosDao;
import modelo.Proveedor;
import modelo.ProveedorDao;
import modelo.Venta;
import modelo.VentaDao;
import modelo.config;
import modelo.login;



import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @authores Renzo Rafael ocupa aliaga y Mendoza Bernabé Alexandra Tatiana
 */
public class sistema extends javax.swing.JFrame {

    //llamando a la clase cliente para que cuando se ejecute pase a restructurar y entrelazar cl:
    Date fechaVenta= new Date();
    String FechaActual= new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    Cliente cl = new Cliente();
    ClienteDao client = new ClienteDao();
    
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Productos pro = new Productos();
    
    ProductosDao proDao= new ProductosDao();
    
    Venta v=  new Venta();
    VentaDao Vdao= new VentaDao();
    Detalle Dv= new Detalle();  
    
    config Conf = new config ();
    Eventos event= new Eventos();
    DefaultTableModel modelo = new DefaultTableModel();
    
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    
    double TotalPagar=0.00;
    
    public sistema() {
        initComponents();

    }

    public sistema(login priv){
        
        initComponents();
        this.setLocationRelativeTo(null);
        idventas.setVisible(false);
        idconfig.setVisible(false);
        idcliente.setVisible(false);
        idventas.setVisible(false);
        idproducto.setVisible(false);
        idventa.setVisible(false);
        idproveedor.setVisible(false);
        AutoCompleteDecorator.decorate(proveedorproducto);
        
        proDao.ConsularProveedor(proveedorproducto);
        idconfig.setVisible(false);
        ListarConfig();
        if (priv.getRol().equals("Cliente")){
            editarproducto.setEnabled(false );
            ventas.setEnabled(false );
            editarcliente.setEnabled(false );
            eleminarcliente.setEnabled(false );
            codigoproductos.setEnabled(false );
            graficar.setEnabled(false );
            descripcionproductos.setEnabled(false );
            cantidadproductos.setEnabled(false );
            precioproductos.setEnabled(false );
            proveedorproducto.setEnabled(false );
            jButton19.setEnabled(false );
            agregarproductos.setEnabled(false );
            guardarproducto.setEnabled(false );
            proveedor.setEnabled(false);
            usuario.setEnabled(false );
            configuracion.setEnabled(false );
            nombreclienteventa.setText(priv.getNombre());
        }else{
            nombreclienteventa.setText(priv.getNombre());
        }
    }
    
    public void Listacliente() {
        List<Cliente> Listarcl = client.ListarCliente();
        modelo = (DefaultTableModel) tablaclientes.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < Listarcl.size(); i++) {
            ob[0] = Listarcl.get(i).getId();
            ob[1] = Listarcl.get(i).getDni();
            ob[2] = Listarcl.get(i).getNombre();
            ob[3] = Listarcl.get(i).getTelefono();
            ob[4] = Listarcl.get(i).getDireccion();
            ob[5] = Listarcl.get(i).getRazon();
            modelo.addRow(ob);

        }
        tablaclientes.setModel(modelo);
    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) tablaproveedor.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            ob[5] = ListarPr.get(i).getRazon();
            modelo.addRow(ob);

        }
        tablaproveedor.setModel(modelo);
    }
    
    public void ListarProductos() {
        List<Productos> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) tablaproducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedor();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);

        }
        tablaproducto.setModel(modelo);
    }
    
    
    
    public void ListarConfig(){
         Conf = proDao.BuscarDatos();
         idconfig.setText(""+Conf.getId());
         rucconfig.setText(""+Conf.getRuc());
         nombreconfig.setText(""+Conf.getNombre());
         telefonoconfig.setText(""+Conf.getTelefono());
         direccionconfig.setText(""+Conf.getDireccion());
         razonconfig.setText(""+Conf.getRazon());
    }
   
    public void ListarVentas() {
        List<Venta> ListarVenta =   Vdao.Listarventas();
        modelo = (DefaultTableModel) tablaventas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getCliente();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();
         
            modelo.addRow(ob);

        }
        tablaventas.setModel(modelo);
    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;

        }
    }

    /**
     * se relizo un sistema web en netbeans con el fin de gestionar la clientela
     * a base de base de datos "phpadmin" proyecto comenzado el 1/11/2023 a las
     * 18:24pm clases que aplica dicho sistema: 1-polimorfismo- 2-Aplicación de
     * estructuras estáticas con objetos- 3-Aplicación de estructuras de control
     * repetitivasusando clases y objetos- horas llevadas acabo hasta
     * ahorita:63h.21min RECOMENDACION:no hacer copia y pega se puede estreaviar
     * algunos elementos acabo-
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel34 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        nuevaventa = new javax.swing.JButton();
        cliente = new javax.swing.JButton();
        proveedor = new javax.swing.JButton();
        productos = new javax.swing.JButton();
        ventas = new javax.swing.JButton();
        configuracion = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        vendedorpro = new javax.swing.JLabel();
        usuario = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        eliminarventa = new javax.swing.JButton();
        codigoventa = new javax.swing.JTextField();
        descripcionventa = new javax.swing.JTextField();
        cantidadventa = new javax.swing.JTextField();
        precioventa = new javax.swing.JTextField();
        stockventa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaventa = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        rucventa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nombreclienteventa = new javax.swing.JTextField();
        generarventa = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        totalventa = new javax.swing.JLabel();
        telefonocv = new javax.swing.JTextField();
        direccionCV = new javax.swing.JTextField();
        razonCV = new javax.swing.JButton();
        idventa = new javax.swing.JTextField();
        graficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        direccioncliente = new javax.swing.JTextField();
        telefonocliente = new javax.swing.JTextField();
        razonsocialcliente = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        dnicliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaclientes = new javax.swing.JTable();
        guardancliente = new javax.swing.JButton();
        editarcliente = new javax.swing.JButton();
        eleminarcliente = new javax.swing.JButton();
        nuevocliente = new javax.swing.JButton();
        idcliente = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Rucproveedor = new javax.swing.JTextField();
        nombreproveedor = new javax.swing.JTextField();
        telefonoproveedor = new javax.swing.JTextField();
        direccionproveedor = new javax.swing.JTextField();
        razonsocialproveedor = new javax.swing.JTextField();
        editarproveedor = new javax.swing.JButton();
        guardarproveedor = new javax.swing.JButton();
        nuevoproveedor = new javax.swing.JButton();
        eliminarproveedor = new javax.swing.JButton();
        idproveedor = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        codigoproductos = new javax.swing.JTextField();
        descripcionproductos = new javax.swing.JTextField();
        cantidadproductos = new javax.swing.JTextField();
        precioproductos = new javax.swing.JTextField();
        proveedorproducto = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaproducto = new javax.swing.JTable();
        guardarproducto = new javax.swing.JButton();
        editarproducto = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        agregarproductos = new javax.swing.JButton();
        idproducto = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaventas = new javax.swing.JTable();
        pdfventas = new javax.swing.JButton();
        idventas = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        rucconfig = new javax.swing.JTextField();
        direccionconfig = new javax.swing.JTextField();
        nombreconfig = new javax.swing.JTextField();
        razonconfig = new javax.swing.JTextField();
        telefonoconfig = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        btnarctualizar = new javax.swing.JButton();
        idconfig = new javax.swing.JTextField();

        jLabel34.setText("jLabel34");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("sistema web");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Sitka Subheading", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("cafeteria \"Mi negrito\"");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 510, 70));

        jPanel2.setBackground(new java.awt.Color(179, 174, 181));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nuevaventa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nuevaventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Nventa.png"))); // NOI18N
        nuevaventa.setText("nueva venta ");
        nuevaventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaventaActionPerformed(evt);
            }
        });
        jPanel2.add(nuevaventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 290, 80));

        cliente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes.png"))); // NOI18N
        cliente.setText("clientes ");
        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });
        jPanel2.add(cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 290, 80));

        proveedor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedor.png"))); // NOI18N
        proveedor.setText("proveedor");
        proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedorActionPerformed(evt);
            }
        });
        jPanel2.add(proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 290, 80));

        productos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/producto.png"))); // NOI18N
        productos.setText("productos");
        productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosActionPerformed(evt);
            }
        });
        jPanel2.add(productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 290, 80));

        ventas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compras.png"))); // NOI18N
        ventas.setText("ventas");
        ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventasActionPerformed(evt);
            }
        });
        jPanel2.add(ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 290, 80));

        configuracion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/config.png"))); // NOI18N
        configuracion.setText("configuracion");
        configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configuracionActionPerformed(evt);
            }
        });
        jPanel2.add(configuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 730, 290, 80));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dd__1_2-removebg-preview.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 180, 180));

        vendedorpro.setText("vendedor informatico");
        jPanel2.add(vendedorpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 120, 20));

        usuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes.png"))); // NOI18N
        usuario.setText("Usuarios");
        usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioActionPerformed(evt);
            }
        });
        jPanel2.add(usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 290, 80));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 870));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img-como-montar-cafeteria-poco-presupuesto.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, -40, 980, 190));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel4.setText("CODIGO");

        jLabel5.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel5.setText("DESCRIPCION");

        jLabel6.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel6.setText("CANTIDAD");

        jLabel7.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel7.setText("PRECIO");

        jLabel8.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel8.setText("STOCK DISPONIBLE");

        eliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        eliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarventaActionPerformed(evt);
            }
        });

        codigoventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigoventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(153, 204, 255)));
        codigoventa.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        codigoventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoventaActionPerformed(evt);
            }
        });
        codigoventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoventaKeyTyped(evt);
            }
        });

        descripcionventa.setEditable(false);
        descripcionventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        descripcionventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        descripcionventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                descripcionventaKeyTyped(evt);
            }
        });

        cantidadventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cantidadventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(153, 204, 255)));
        cantidadventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadventaActionPerformed(evt);
            }
        });
        cantidadventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cantidadventaKeyTyped(evt);
            }
        });

        precioventa.setEditable(false);
        precioventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        precioventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        precioventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioventaActionPerformed(evt);
            }
        });
        precioventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precioventaKeyTyped(evt);
            }
        });

        stockventa.setEditable(false);
        stockventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        stockventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        stockventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockventaActionPerformed(evt);
            }
        });
        stockventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stockventaKeyTyped(evt);
            }
        });

        tablaventa.setBackground(new java.awt.Color(236, 234, 226));
        tablaventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tablaventa);

        jLabel9.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel9.setText("DNI/RUC");

        rucventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(153, 204, 255)));
        rucventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rucventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rucventaKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel10.setText("NOMBRE");

        nombreclienteventa.setEditable(false);
        nombreclienteventa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        nombreclienteventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreclienteventaKeyTyped(evt);
            }
        });

        generarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/print.png"))); // NOI18N
        generarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarventaActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/money.png"))); // NOI18N
        jLabel11.setText("TOTAL A PAGAR:");

        totalventa.setText("----------");

        telefonocv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonocvActionPerformed(evt);
            }
        });

        direccionCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionCVActionPerformed(evt);
            }
        });

        graficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/torta.png"))); // NOI18N
        graficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel12.setText("                 Seleccionar:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(rucventa, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(nombreclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(telefonocv, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(direccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(razonCV, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(generarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(totalventa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(579, 579, 579)
                        .addComponent(graficar, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 10, Short.MAX_VALUE)
                                .addComponent(codigoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(descripcionventa, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(122, 122, 122)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cantidadventa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(8, 8, 8)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel8)
                                .addGap(14, 14, 14))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(precioventa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(stockventa, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(idventa, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(Midate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graficar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Midate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stockventa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idventa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(precioventa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cantidadventa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(descripcionventa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(codigoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(telefonocv, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(direccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(razonCV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rucventa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombreclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(generarventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(totalventa))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(2000, 2000, 2000))
        );

        jTabbedPane1.addTab("tab1", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel13.setText("DNI/RUC:");

        jLabel14.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel14.setText("TELEFONO:");

        jLabel15.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel15.setText("RAZON SOCIAL:");

        jLabel16.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel16.setText("DIRECCION:");

        jLabel17.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel17.setText("NOMBRE:");

        direccioncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionclienteActionPerformed(evt);
            }
        });
        direccioncliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                direccionclienteKeyTyped(evt);
            }
        });

        telefonocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoclienteActionPerformed(evt);
            }
        });
        telefonocliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefonoclienteKeyTyped(evt);
            }
        });

        razonsocialcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                razonsocialclienteActionPerformed(evt);
            }
        });
        razonsocialcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                razonsocialclienteKeyTyped(evt);
            }
        });

        nombrecliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreclienteKeyTyped(evt);
            }
        });

        dnicliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dniclienteKeyTyped(evt);
            }
        });

        tablaclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaclientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaclientes);
        if (tablaclientes.getColumnModel().getColumnCount() > 0) {
            tablaclientes.getColumnModel().getColumn(3).setPreferredWidth(80);
        }

        guardancliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarTodo.png"))); // NOI18N
        guardancliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardanclienteActionPerformed(evt);
            }
        });

        editarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        editarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarclienteActionPerformed(evt);
            }
        });

        eleminarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        eleminarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eleminarclienteActionPerformed(evt);
            }
        });

        nuevocliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        nuevocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eleminarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevocliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guardancliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16))
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(razonsocialcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(direccioncliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                .addComponent(telefonocliente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nombrecliente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dnicliente, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(editarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(dnicliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(razonsocialcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(editarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guardancliente, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eleminarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab2", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tablaproveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        tablaproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaproveedor);
        if (tablaproveedor.getColumnModel().getColumnCount() > 0) {
            tablaproveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            tablaproveedor.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        jLabel18.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel18.setText("RUC:");

        jLabel19.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel19.setText("NOMBRE:");

        jLabel20.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel20.setText("TELEFONO:");

        jLabel21.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel21.setText("DIRECCION:");

        jLabel22.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel22.setText("RAZON SOCIAL:");

        Rucproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                RucproveedorKeyTyped(evt);
            }
        });

        nombreproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreproveedorKeyTyped(evt);
            }
        });

        telefonoproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoproveedorActionPerformed(evt);
            }
        });
        telefonoproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefonoproveedorKeyTyped(evt);
            }
        });

        direccionproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                direccionproveedorKeyTyped(evt);
            }
        });

        razonsocialproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                razonsocialproveedorKeyTyped(evt);
            }
        });

        editarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        editarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarproveedorActionPerformed(evt);
            }
        });

        guardarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarTodo.png"))); // NOI18N
        guardarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarproveedorActionPerformed(evt);
            }
        });

        nuevoproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        nuevoproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoproveedorActionPerformed(evt);
            }
        });

        eliminarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        eliminarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarproveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel18))
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(telefonoproveedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(direccionproveedor)
                            .addComponent(nombreproveedor)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(Rucproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(razonsocialproveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(editarproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guardarproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevoproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eliminarproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(Rucproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(razonsocialproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addComponent(editarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guardarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(eliminarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab3", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel23.setText("CODIGO:");

        jLabel24.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel24.setText("DESCRIPCION:");

        jLabel25.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel25.setText("CANTIDAD: ");

        jLabel26.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel26.setText("PRECIO:");

        jLabel27.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel27.setText("PROVEEDOR:");

        codigoproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoproductosActionPerformed(evt);
            }
        });
        codigoproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoproductosKeyTyped(evt);
            }
        });

        descripcionproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                descripcionproductosKeyTyped(evt);
            }
        });

        cantidadproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cantidadproductosKeyTyped(evt);
            }
        });

        precioproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precioproductosKeyTyped(evt);
            }
        });

        proveedorproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedorproductoActionPerformed(evt);
            }
        });

        tablaproducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCION", "PROVEEDOR ", "STOCK", "PRECIO"
            }
        ));
        tablaproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaproducto);
        if (tablaproducto.getColumnModel().getColumnCount() > 0) {
            tablaproducto.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        guardarproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarTodo.png"))); // NOI18N
        guardarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarproductoActionPerformed(evt);
            }
        });

        editarproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        editarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarproductoActionPerformed(evt);
            }
        });

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        agregarproductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        agregarproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarproductosActionPerformed(evt);
            }
        });

        idproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idproductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(guardarproducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editarproducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel24))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addComponent(codigoproductos)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(descripcionproductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cantidadproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(precioproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(proveedorproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(agregarproductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel27)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigoproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descripcionproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidadproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precioproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(17, 17, 17)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proveedorproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(editarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guardarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agregarproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab4", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tablaventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        tablaventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaventasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaventas);

        pdfventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        pdfventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfventasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(pdfventas, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(idventas, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(754, 754, 754))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idventas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdfventas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab5", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("SimSun", 1, 48)); // NOI18N
        jLabel28.setText("DATOS DE LA EMPRESA ");

        jLabel29.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel29.setText("RUC");

        rucconfig.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(0, 0, 0)));
        rucconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rucconfigActionPerformed(evt);
            }
        });
        rucconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rucconfigKeyTyped(evt);
            }
        });

        direccionconfig.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(0, 0, 0)));
        direccionconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                direccionconfigKeyTyped(evt);
            }
        });

        nombreconfig.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(0, 0, 0)));
        nombreconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreconfigKeyTyped(evt);
            }
        });

        razonconfig.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(0, 0, 0)));
        razonconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                razonconfigKeyTyped(evt);
            }
        });

        telefonoconfig.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(0, 0, 0)));
        telefonoconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefonoconfigKeyTyped(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel30.setText("NOMBRE");

        jLabel31.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel31.setText("TELEFONO");

        jLabel32.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel32.setText("DIRECCION ");

        jLabel33.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel33.setText("RAZON SOCIAL");

        btnarctualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btnarctualizar.setText("ACTUALIZAR");
        btnarctualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnarctualizarActionPerformed(evt);
            }
        });

        idconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idconfigActionPerformed(evt);
            }
        });
        idconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idconfigKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(rucconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(nombreconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(telefonoconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(direccionconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(169, 169, 169)
                        .addComponent(razonconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(196, 196, 196))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(btnarctualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(392, 392, 392))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(261, 261, 261))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel28)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31)
                        .addGap(100, 100, 100))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rucconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefonoconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(razonconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(btnarctualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", jPanel8);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 980, 750));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 870));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorActionPerformed
        
        LimpiarTable();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
        
    }//GEN-LAST:event_proveedorActionPerformed

    private void codigoventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoventaActionPerformed

    private void eliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarventaActionPerformed
     
        modelo= (DefaultTableModel)tablaventa.getModel();
        modelo.removeRow(tablaventa.getSelectedRow());
        TotalPagar();
        codigoventa.requestFocus();
        
        
    }//GEN-LAST:event_eliminarventaActionPerformed

    private void cantidadventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadventaActionPerformed

    private void direccionclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionclienteActionPerformed

    private void telefonoproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoproveedorActionPerformed

    private void codigoproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoproductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoproductosActionPerformed

    private void btnarctualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnarctualizarActionPerformed
        // TODO add your handling code here:
         if (!"".equals(rucconfig.getText()) || !"".equals(nombreconfig.getText()) || !"".equals(telefonoconfig.getText())|| !"".equals(direccionconfig.getText())) {

                Conf.setRuc(Integer.parseInt(rucconfig.getText()));
                Conf.setNombre(nombreconfig.getText());
                Conf.setTelefono(Integer.parseInt(telefonoconfig.getText()));
                Conf.setDireccion(direccionconfig.getText());
                Conf.setRazon(razonconfig.getText());
                Conf.setId(Integer.parseInt(idconfig.getText()));
                proDao.ModificarDatos(Conf);
                JOptionPane.showMessageDialog(null, "datos de la empresa modificado ");
                ListarConfig();
               

            } else {
                JOptionPane.showMessageDialog(null, "los campos estan vacios ");
            }
        
        
    }//GEN-LAST:event_btnarctualizarActionPerformed

    private void telefonocvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonocvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonocvActionPerformed

    private void direccionCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionCVActionPerformed

    private void idproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idproductoActionPerformed

    private void guardanclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardanclienteActionPerformed

// cuando el usuario precione guardar se ejercutara....
        if (!"".equals(dnicliente.getText()) || !"".equals(nombrecliente.getText()) || !"".equals(telefonocliente.getText()) || !"".equals(direccioncliente.getText())) {
            cl.setDni(Integer.parseInt(dnicliente.getText()));
            cl.setNombre(nombrecliente.getText());
            cl.setTelefono(Integer.parseInt(telefonocliente.getText()));
            cl.setDireccion(direccioncliente.getText());
            cl.setRazon(razonsocialcliente.getText());
            client.RegistrarCliente(cl);
            JOptionPane.showMessageDialog(null, "cliente registrado");
            
            LimpiarTable();
            LimpiarCliente();
            Listacliente();
            JOptionPane.showMessageDialog(null, "cliente registrado");

//si no hay nada para guardar se ejecutara
        } else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios :c");
        }


    }//GEN-LAST:event_guardanclienteActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        LimpiarTable();
        Listacliente();

        // entrelazado con la tabla
        jTabbedPane1.setSelectedIndex(1);

    }//GEN-LAST:event_clienteActionPerformed

    private void tablaclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclientesMouseClicked

        //nombramiento de ejecucion para cada columna dada con cada valor item asignado
        int fila = tablaclientes.rowAtPoint(evt.getPoint());
        idcliente.setText(tablaclientes.getValueAt(fila, 0).toString());
        dnicliente.setText(tablaclientes.getValueAt(fila, 1).toString());
        nombrecliente.setText(tablaclientes.getValueAt(fila, 2).toString());
        telefonocliente.setText(tablaclientes.getValueAt(fila, 3).toString());
        direccioncliente.setText(tablaclientes.getValueAt(fila, 4).toString());
        razonsocialcliente.setText(tablaclientes.getValueAt(fila, 5).toString());


    }//GEN-LAST:event_tablaclientesMouseClicked

    private void eleminarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eleminarclienteActionPerformed
        if (!"".equals(idcliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "estas seguro de eliminar ?");
            if (pregunta == 0) {
                int id = Integer.parseInt(idcliente.getText());
                client.EliminarCliente(id);

                //subnombramiendo  
                LimpiarTable();
                LimpiarCliente();
                Listacliente();

            }
        }
    }//GEN-LAST:event_eleminarclienteActionPerformed

    private void editarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarclienteActionPerformed
        if ("".equals(idcliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");

        } else {

            if (!"".equals(dnicliente.getText()) || !"".equals(nombrecliente.getText()) || !"".equals(telefonocliente.getText())) {

                cl.setDni(Integer.parseInt(dnicliente.getText()));
                cl.setNombre(nombrecliente.getText());
                cl.setTelefono(Integer.parseInt(telefonocliente.getText()));
                cl.setDireccion(direccioncliente.getText());
                cl.setRazon(razonsocialcliente.getText());
                cl.setId(Integer.parseInt(idcliente.getText()));
                client.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "cliente modificado ");
                LimpiarTable();
                LimpiarCliente();
                Listacliente();

            } else {
                JOptionPane.showMessageDialog(null, "los campos estan vacios ");
            }

        }
    }//GEN-LAST:event_editarclienteActionPerformed

    private void nuevoclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoclienteActionPerformed
        // TODO add your handling code here:

        //boton para hacer una nueva
        LimpiarCliente();
    }//GEN-LAST:event_nuevoclienteActionPerformed

    private void guardarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarproveedorActionPerformed

        if (!"".equals(Rucproveedor.getText()) || !"".equals(nombreproveedor.getText()) || !"".equals(telefonoproveedor.getText()) || !"".equals(direccionproveedor.getText()) || !"".equals(razonsocialproveedor.getText())) {
            pr.setRuc(Integer.parseInt(Rucproveedor.getText()));
            pr.setNombre(nombreproveedor.getText());
            pr.setTelefono(Integer.parseInt(telefonoproveedor.getText()));
            pr.setDireccion(direccionproveedor.getText());
            pr.setRazon(razonsocialproveedor.getText());
            PrDao.RegistrarProvedor(pr);
            
            JOptionPane.showMessageDialog(null, "proveedor registrado");
            LimpiarTable();
            ListarProveedor();
            LimpiarProveedor();
            
                
            
        
        } else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios ");
        }
    }//GEN-LAST:event_guardarproveedorActionPerformed

    private void tablaproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproveedorMouseClicked
      
        
        
        int fila= tablaproveedor.rowAtPoint(evt.getPoint());
        idproveedor.setText(tablaproveedor.getValueAt(fila,0).toString());
        Rucproveedor.setText(tablaproveedor.getValueAt(fila , 1).toString());
        nombreproveedor.setText(tablaproveedor.getValueAt(fila , 2).toString());
        telefonoproveedor.setText(tablaproveedor.getValueAt(fila , 3).toString());
        direccionproveedor.setText(tablaproveedor.getValueAt(fila , 4).toString());
        razonsocialproveedor.setText(tablaproveedor.getValueAt(fila , 5).toString());
    }//GEN-LAST:event_tablaproveedorMouseClicked

    private void eliminarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarproveedorActionPerformed
       if(!"".equals(idproveedor.getText())){
           int pregunta =JOptionPane.showConfirmDialog(null,"esta seguro de eliminar");
           if( pregunta ==0 ){
               int id = Integer.parseInt(idproveedor.getText());
               PrDao.EliminarProveedor(id);
               LimpiarTable();
               ListarProveedor();
               LimpiarProveedor();
           }
       }else{
           JOptionPane.showMessageDialog(null, "seleccione una fila");
           
       }
        
    }//GEN-LAST:event_eliminarproveedorActionPerformed

    private void editarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarproveedorActionPerformed
       
        if("".equals(idproveedor.getText())){
            JOptionPane.showMessageDialog(null, "seleccione una fila ");
         
         
        }else{
            if(!"".equals(Rucproveedor.getText())||!"".equals(nombreproveedor.getText())||!"".equals(telefonoproveedor.getText())||!"".equals(direccionproveedor.getText())||!"".equals(razonsocialproveedor.getText())){
                pr.setRuc(Integer.parseInt(Rucproveedor.getText()));
                pr.setNombre(nombreproveedor.getText());
                pr.setTelefono(Integer.parseInt(telefonoproveedor.getText()));
                pr.setDireccion(direccionproveedor.getText());
                pr.setRazon(razonsocialproveedor.getText());
                pr.setId(Integer.parseInt(idproveedor.getText())); 
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "proveedor modificado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
                
                
            }
        }
        
        
        
    }//GEN-LAST:event_editarproveedorActionPerformed

    private void nuevoproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoproveedorActionPerformed
        // TODO add your handling code here:
        
        LimpiarProveedor();
    }//GEN-LAST:event_nuevoproveedorActionPerformed

    private void guardarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarproductoActionPerformed
        // TODO add your handling code here:
        
        if(!"".equals(codigoproductos.getText())||!"".equals(descripcionproductos.getText())||!"".equals(proveedorproducto.getSelectedItem())||!"".equals(cantidadproductos.getText())||!"".equals(precioproductos.getText())){
            
            pro.setCodigo(codigoproductos.getText());
            pro.setNombre(descripcionproductos.getText());
            pro.setProveedor(proveedorproducto.getSelectedItem().toString());
            pro.setStock(Integer.parseInt(cantidadproductos.getText()));
            pro.setPrecio(Double.parseDouble (precioproductos.getText()));
            proDao.RegistrarProductos(pro);
            JOptionPane.showMessageDialog(null, "productos registrados");
            LimpiarTable();
            ListarProductos();
            LimpiarProductos();
        }else{
            JOptionPane.showMessageDialog(null, "los campos estan vacios");
            
            
        }
    }//GEN-LAST:event_guardarproductoActionPerformed

    private void proveedorproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorproductoActionPerformed
        
        
        
        
    }//GEN-LAST:event_proveedorproductoActionPerformed

    private void productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarProductos();
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_productosActionPerformed

    private void tablaproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproductoMouseClicked
       
        int fila= tablaproducto.rowAtPoint(evt.getPoint());
        idproducto.setText(tablaproducto.getValueAt(fila,0).toString());
        codigoproductos.setText(tablaproducto.getValueAt(fila , 1).toString());
        descripcionproductos.setText(tablaproducto.getValueAt(fila , 2).toString());
        proveedorproducto.setSelectedItem(tablaproducto.getValueAt(fila , 3).toString());
        cantidadproductos.setText(tablaproducto.getValueAt(fila , 4).toString());
        precioproductos.setText(tablaproducto.getValueAt(fila , 5).toString());
    }//GEN-LAST:event_tablaproductoMouseClicked

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        
        
        
         if (!"".equals(idproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "estas seguro de eliminar ?");
            if (pregunta == 0) {
                int id = Integer.parseInt(idproducto.getText());
                proDao.EliminarProductos(id);

                //subnombramiendo  
                LimpiarTable();
                LimpiarProductos();
                ListarProductos();

            }
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void editarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarproductoActionPerformed
        // TODO add your handling code here:
        
        
        
           if("".equals(idproducto.getText())){
            JOptionPane.showMessageDialog(null, "seleccione una fila ");
         
         
        }else{
            if(!"".equals(codigoproductos.getText())||!"".equals(descripcionproductos.getText())||!"".equals(cantidadproductos.getText())||!"".equals(precioproductos.getText())){
                pro.setCodigo(codigoproductos.getText());
                pro.setNombre(descripcionproductos.getText());
                pro.setProveedor(proveedorproducto.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(cantidadproductos.getText()));
                pro.setPrecio(Double.parseDouble(precioproductos.getText()));
                pro.setId(Integer.parseInt(idproducto.getText())); 
                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "proveedor modificado");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();
                
                
            }
        }
    }//GEN-LAST:event_editarproductoActionPerformed

    private void agregarproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarproductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_agregarproductosActionPerformed

    private void codigoventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoventaKeyPressed
       
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(!"".equals(codigoventa.getText())){
                
                String cod = codigoventa.getText();
                pro= proDao.BuscarPro(cod);
                
                if (pro.getNombre()!= null){
                    descripcionventa.setText(""+pro.getNombre());
                    precioventa.setText(""+pro.getPrecio());
                    stockventa.setText(""+pro.getStock());
                    cantidadventa.requestFocus();
                    
                }else{
                   LimpiarVenta();
                    codigoventa.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "ingrese el codigo del producto");
                codigoventa.requestFocus();
            }
        }
        
        
        
    }//GEN-LAST:event_codigoventaKeyPressed

    private void cantidadventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadventaKeyPressed
       
        
          if(evt.getKeyCode()==KeyEvent.VK_ENTER){
              if(!"".equals(cantidadventa.getText())){
                  String cod = codigoventa.getText();
                  String descripcion =descripcionventa.getText();
                  int cant = Integer.parseInt(cantidadventa.getText());
                  double precio = Double.parseDouble(precioventa.getText());
                  double total= cant*precio;
                  int stock= Integer.parseInt(stockventa.getText());
                  if(stock>=cant){
                      item=item+1;
                      
                     DefaultTableModel tmp=(DefaultTableModel)tablaventa.getModel();
                      
                      
                     ArrayList lista= new ArrayList();
                     lista.add(item);
                     lista.add(cod);
                     lista.add(descripcion);
                     lista.add(cant);
                     lista.add(precio);
                     lista.add(total);
                     Object[] o = new Object[5];
                     o[0]=lista.get(1);
                      o[1] = lista.get(2);
                      o[2]=lista.get(3);
                      o[3]=lista.get(4);
                      o[4]=lista.get(5);
                      tmp.addRow(o);
                      tablaventa.setModel(tmp);
                      TotalPagar();
                      LimpiarVenta();
                      
           
                      codigoventa.requestFocus();
                      
                      
                     
                      for(int i= 0; i<tablaventa.getRowCount();i++){
                          if(tablaventa.getValueAt(i, 1 ).equals(descripcionventa.getText())){
                              JOptionPane.showMessageDialog(null, "el producto ya esta registrado");
                              return;
                          }
                      }
                  
                  }else{
                      JOptionPane.showMessageDialog(null, "stock no disponible");
                  }
                  
              }else{
                  JOptionPane.showMessageDialog(null, "ingrese cantidad ");
              }
          }

    }//GEN-LAST:event_cantidadventaKeyPressed

    private void precioventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioventaActionPerformed

    private void stockventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockventaActionPerformed

    private void rucventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucventaKeyPressed
        
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            if(!"".equals(rucventa.getText())){
                int dni= Integer.parseInt(rucventa.getText());
                cl = client.Buscarcliente(dni);
                if(cl.getNombre()!=null){
                    nombreclienteventa.setText(""+cl.getNombre());
                    telefonocv.setText(""+cl.getTelefono());
                    direccionCV.setText(""+cl.getDireccion());
                    razonCV.setText(""+cl.getRazon());
                }else{
                    rucventa.setText("");
                    JOptionPane.showMessageDialog(null, "el cliente no existe");
                }
            }
        }
        
        
    }//GEN-LAST:event_rucventaKeyPressed

    private void generarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarventaActionPerformed
        if(tablaventa.getRowCount()>0){
            if(!"".equals(nombreclienteventa.getText())){
                 RegistrarVenta(); 
                 RegistrarDetalle();
                 ActualizarStock();
                 pdf();
                 LimpiarTablaVenta();
                 LimpiarClienteventa();
            }else{
                JOptionPane.showMessageDialog(null,"debes buscar un cliente");
            }
        } else{
            JOptionPane.showMessageDialog(null,"no hay productos en venta ");
        }
       
        
    }//GEN-LAST:event_generarventaActionPerformed

    private void ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ventasActionPerformed
       jTabbedPane1.setSelectedIndex(4);
       LimpiarTable();
       ListarVentas();
    }//GEN-LAST:event_ventasActionPerformed

    private void nuevaventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaventaActionPerformed
        // TODO add your handling code here:
        
        jTabbedPane1.setSelectedIndex(0);
        
    }//GEN-LAST:event_nuevaventaActionPerformed

    private void telefonoclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoclienteActionPerformed

    private void idconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idconfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idconfigActionPerformed

    private void codigoventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoventaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_codigoventaKeyTyped

    private void descripcionventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripcionventaKeyTyped
        // TODO add your handling code here:
         event.textKeyPress(evt);
    }//GEN-LAST:event_descripcionventaKeyTyped

    private void cantidadventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadventaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_cantidadventaKeyTyped

    private void precioventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioventaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
        
    }//GEN-LAST:event_precioventaKeyTyped

    private void stockventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stockventaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_stockventaKeyTyped

    private void nombreclienteventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreclienteventaKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_nombreclienteventaKeyTyped

    private void rucventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucventaKeyTyped
        // TODO add your handling code here:
         event.numberKeyPress(evt);
        
    }//GEN-LAST:event_rucventaKeyTyped

    private void dniclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dniclienteKeyTyped
        // TODO add your handling code here:
          event.numberKeyPress(evt);
        
    }//GEN-LAST:event_dniclienteKeyTyped

    private void nombreclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreclienteKeyTyped
         event.textKeyPress(evt);
         
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreclienteKeyTyped

    private void telefonoclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoclienteKeyTyped
        // TODO add your handling code here:
        
          event.numberKeyPress(evt);
    }//GEN-LAST:event_telefonoclienteKeyTyped

    private void direccionclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionclienteKeyTyped
        // TODO add your handling code here:
        
        event.textKeyPress(evt);
    }//GEN-LAST:event_direccionclienteKeyTyped

    private void razonsocialclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_razonsocialclienteKeyTyped
        // TODO add your handling code here:
        
        
        
        event.textKeyPress(evt);
    }//GEN-LAST:event_razonsocialclienteKeyTyped

    private void RucproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RucproveedorKeyTyped
        // TODO add your handling code here:
        
        event.numberKeyPress(evt);
        
    }//GEN-LAST:event_RucproveedorKeyTyped

    private void nombreproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreproveedorKeyTyped
        // TODO add your handling code here:
        
         event.textKeyPress(evt);
        
    }//GEN-LAST:event_nombreproveedorKeyTyped

    private void telefonoproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoproveedorKeyTyped
        // TODO add your handling code here:
        
        event.numberKeyPress(evt);
        
    }//GEN-LAST:event_telefonoproveedorKeyTyped

    private void direccionproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionproveedorKeyTyped
        // TODO add your handling code here:
        
        event.textKeyPress(evt);
    }//GEN-LAST:event_direccionproveedorKeyTyped

    private void razonsocialproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_razonsocialproveedorKeyTyped
        // TODO add your handling code here:
        
         event.textKeyPress(evt);
    }//GEN-LAST:event_razonsocialproveedorKeyTyped

    private void codigoproductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoproductosKeyTyped
        // TODO add your handling code here:
         
        
         event.numberKeyPress(evt);
    }//GEN-LAST:event_codigoproductosKeyTyped

    private void descripcionproductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripcionproductosKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_descripcionproductosKeyTyped

    private void cantidadproductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadproductosKeyTyped
        // TODO add your handling code here:
        
         event.numberKeyPress(evt);
    }//GEN-LAST:event_cantidadproductosKeyTyped

    private void precioproductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioproductosKeyTyped
        // TODO add your handling code h
         event.numberDecimalKeyPress(evt, precioproductos);
    }//GEN-LAST:event_precioproductosKeyTyped

    private void nombreconfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreconfigKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
        
    }//GEN-LAST:event_nombreconfigKeyTyped

    private void rucconfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucconfigKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
     
    }//GEN-LAST:event_rucconfigKeyTyped

    private void telefonoconfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoconfigKeyTyped
        // TODO add your handling code here:
     
         event.numberKeyPress(evt);
    }//GEN-LAST:event_telefonoconfigKeyTyped

    private void direccionconfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionconfigKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
         
    }//GEN-LAST:event_direccionconfigKeyTyped

    private void razonconfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_razonconfigKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
         
    }//GEN-LAST:event_razonconfigKeyTyped

    private void idconfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idconfigKeyTyped
        // TODO add your handling code here:
       
         event.numberKeyPress(evt);
    }//GEN-LAST:event_idconfigKeyTyped

    private void configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configuracionActionPerformed
        // TODO add your handling code here:
        
        jTabbedPane1.setSelectedIndex(5);
        
        
        
        
    }//GEN-LAST:event_configuracionActionPerformed

    private void tablaventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaventasMouseClicked
      
        int fila = tablaventas.rowAtPoint(evt.getPoint());
        idventas.setText(tablaventas.getValueAt(fila, 0).toString());
        
    }//GEN-LAST:event_tablaventasMouseClicked

    private void pdfventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfventasActionPerformed
        
          try {
            int id= Integer.parseInt(idventas.getText());
            
            File file= new File("src/pdf/venta.pdf");
      
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        
        
        
        
    }//GEN-LAST:event_pdfventasActionPerformed

    private void graficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarActionPerformed
        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(Midate.getDate());
        Grafico.Graficar(fechaReporte);
        
        
    }//GEN-LAST:event_graficarActionPerformed

    private void usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioActionPerformed
        // TODO add your handling code here:
        Registro reg = new Registro();
        reg.setVisible(true);
        
        
    }//GEN-LAST:event_usuarioActionPerformed

    private void rucconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rucconfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rucconfigActionPerformed

    private void razonsocialclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_razonsocialclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_razonsocialclienteActionPerformed

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
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTextField Rucproveedor;
    private javax.swing.JButton agregarproductos;
    private javax.swing.JButton btnarctualizar;
    private javax.swing.JTextField cantidadproductos;
    private javax.swing.JTextField cantidadventa;
    private javax.swing.JButton cliente;
    private javax.swing.JTextField codigoproductos;
    private javax.swing.JTextField codigoventa;
    private javax.swing.JButton configuracion;
    private javax.swing.JTextField descripcionproductos;
    private javax.swing.JTextField descripcionventa;
    private javax.swing.JTextField direccionCV;
    private javax.swing.JTextField direccioncliente;
    private javax.swing.JTextField direccionconfig;
    private javax.swing.JTextField direccionproveedor;
    private javax.swing.JTextField dnicliente;
    private javax.swing.JButton editarcliente;
    private javax.swing.JButton editarproducto;
    private javax.swing.JButton editarproveedor;
    private javax.swing.JButton eleminarcliente;
    private javax.swing.JButton eliminarproveedor;
    private javax.swing.JButton eliminarventa;
    private javax.swing.JButton generarventa;
    private javax.swing.JButton graficar;
    private javax.swing.JButton guardancliente;
    private javax.swing.JButton guardarproducto;
    private javax.swing.JButton guardarproveedor;
    private javax.swing.JTextField idcliente;
    private javax.swing.JTextField idconfig;
    private javax.swing.JTextField idproducto;
    private javax.swing.JTextField idproveedor;
    private javax.swing.JTextField idventa;
    private javax.swing.JTextField idventas;
    private javax.swing.JButton jButton19;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreclienteventa;
    private javax.swing.JTextField nombreconfig;
    private javax.swing.JTextField nombreproveedor;
    private javax.swing.JButton nuevaventa;
    private javax.swing.JButton nuevocliente;
    private javax.swing.JButton nuevoproveedor;
    private javax.swing.JButton pdfventas;
    private javax.swing.JTextField precioproductos;
    private javax.swing.JTextField precioventa;
    private javax.swing.JButton productos;
    private javax.swing.JButton proveedor;
    private javax.swing.JComboBox<String> proveedorproducto;
    private javax.swing.JButton razonCV;
    private javax.swing.JTextField razonconfig;
    private javax.swing.JTextField razonsocialcliente;
    private javax.swing.JTextField razonsocialproveedor;
    private javax.swing.JTextField rucconfig;
    private javax.swing.JTextField rucventa;
    private javax.swing.JTextField stockventa;
    private javax.swing.JTable tablaclientes;
    private javax.swing.JTable tablaproducto;
    private javax.swing.JTable tablaproveedor;
    private javax.swing.JTable tablaventa;
    private javax.swing.JTable tablaventas;
    private javax.swing.JTextField telefonocliente;
    private javax.swing.JTextField telefonoconfig;
    private javax.swing.JTextField telefonocv;
    private javax.swing.JTextField telefonoproveedor;
    private javax.swing.JLabel totalventa;
    private javax.swing.JButton usuario;
    private javax.swing.JLabel vendedorpro;
    private javax.swing.JButton ventas;
    // End of variables declaration//GEN-END:variables

    //limpiar los datos  
    private void LimpiarCliente() {
        idcliente.setText("");
        dnicliente.setText("");
        nombrecliente.setText("");
        telefonocliente.setText("");
        direccioncliente.setText("");
        razonsocialcliente.setText("");
    }
 private void LimpiarProveedor() {
        idproveedor.setText("");
        Rucproveedor.setText("");
        nombreproveedor.setText("");
        telefonoproveedor.setText("");
        direccionproveedor.setText("");
        razonsocialproveedor.setText("");
    }
 
  private void LimpiarProductos() {
        idproducto.setText("");
        codigoproductos.setText("");
        proveedorproducto.setSelectedItem(null);
        descripcionproductos.setText("");
        cantidadproductos.setText("");
        precioproductos.setText("");
    }
  
  private void TotalPagar(){
      TotalPagar = 0.00;
      int numFila = tablaventa.getRowCount();
      for( int i =  0; i<numFila;i++ ){
          double cal= Double.parseDouble(String.valueOf(tablaventa.getModel().getValueAt(i, 4)));
     
          TotalPagar= TotalPagar+cal;
      
      }
      totalventa.setText(String.format("%.2f", TotalPagar));
      
      
  }
  private void LimpiarVenta(){
      codigoventa.setText("");
      descripcionventa.setText("");
      cantidadventa.setText("");
      stockventa.setText("");
      precioventa.setText("");
      idventas.setText("");
      
  }
  private void RegistrarVenta(){
      String cliente= nombreclienteventa.getText();
      String vendedor = vendedorpro.getText();
      double monto = TotalPagar;
      v.setCliente(cliente);
      v.setVendedor(vendedor);
      v.setTotal(monto);
      v.setFecha(FechaActual);
      Vdao.RegistrarVenta(v);
  }
  private void RegistrarDetalle(){
      int id =Vdao.idventa();
      for ( int i=0; i<tablaventa.getRowCount();i++){
          String cod = tablaventa.getValueAt(i, 0).toString();
          int cant= Integer.parseInt(tablaventa.getValueAt(i, 2).toString());
          double precio= Double.parseDouble(tablaventa.getValueAt(i,3).toString());
        
          Dv.setCod_pro(cod);
          Dv.setCantidad(cant);
          Dv.setPrecio(precio);
          Dv.setId(id);
          Vdao.RegistrarDetalle(Dv);
                  
      }
  }
  // programacion de boton para actualizar stock 
  private void  ActualizarStock(){
      for(int i=0; i< tablaventa.getRowCount();i++){
          String cod = tablaventa.getValueAt(i, 0).toString();
          int cant= Integer.parseInt(tablaventa.getValueAt(i, 2).toString());
          pro=proDao.BuscarPro(cod);
          int StockActual = pro.getStock()-cant;
          Vdao.ActualizarStock(StockActual, cod);
      }
  }
  // void de nombramiento que ayudara a limpiar el tablero de ventas
  private void LimpiarTablaVenta(){
      tmp =( DefaultTableModel)tablaventa.getModel();
      int fila= tablaventa.getRowCount();
      for(int i=0; i<fila;i++){
          tmp.removeRow(0);
      }
  }
  private void LimpiarClienteventa(){
      rucventa.setText("");
      nombreclienteventa.setText("");
      telefonocv.setText("");
      direccionCV.setText("");
      razonCV.setText("");
  }        
  private void pdf(){ //clase pdf que generara archivos pdf ya nombrado
      try{
          int id= Vdao.idventa();
          FileOutputStream archivo;
          File file= new File("src/pdf/venta"+id+".pdf");
          archivo= new FileOutputStream(file);
          Document doc = new Document();
          PdfWriter.getInstance(doc, archivo);
          doc.open();
          
          Image img= Image.getInstance("src/imagenes/dd.png");
          
          Paragraph fecha= new Paragraph();
          Font negrita= new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLUE );
          
          fecha.add(Chunk.NEWLINE);
          Date date= new Date();
          fecha.add("Factura:"+id+"\n"+"Fecha"+new SimpleDateFormat("dd-mm-yyyy").format(date)+"\n\n");
          
          //posisicion de los parametros prueba 4´
          PdfPTable Encabezado = new PdfPTable(4); 
          Encabezado.setWidthPercentage(100);
          Encabezado.getDefaultCell().setBorder(0);
          float[]ColumnaEncabezado= new float[]{20f,30f,70f,40f};
          Encabezado.setWidths(ColumnaEncabezado);
          Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
          Encabezado.addCell(img);
          
          //datos qu se registran en el pdf que se generara 
          String ruc=rucconfig.getText();
          String nom=nombreconfig.getText()  ;
          String tel=telefonoconfig.getText();
          String dir=direccionconfig.getText();
          String ra=razonconfig.getText();
          
          Encabezado.addCell("");
          Encabezado.addCell("Ruc:"+ruc+"\nNombre:"+nom+"\nTelefono:"+tel+"\nDireccion:"+dir+"\nRazon:"+ra);
          Encabezado.addCell(fecha);
          doc.add(Encabezado);
          
          Paragraph cli= new  Paragraph();
          cli.add(Chunk.NEWLINE);
          cli.add("Datos de los clientes "+"\n\n");
          doc.add(cli);
          
          
          PdfPTable tablacli= new PdfPTable(4);
          
          tablacli.setWidthPercentage(100);
          
          tablacli.getDefaultCell().setBorder(0);
          
          float[]Columnacli= new float[]{20f,50f,30f,40f};
          
          tablacli.setWidths(Columnacli);
          tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);
          
          PdfPCell cl1= new PdfPCell(new Phrase("Dni/Ruc",negrita));
          PdfPCell cl2= new PdfPCell(new Phrase("Nombre",negrita));
          PdfPCell cl3= new PdfPCell(new Phrase("telefono",negrita));
          PdfPCell cl4= new PdfPCell(new Phrase("Direccion",negrita));
          
          cl1.setBorder(0);
          cl2.setBorder(0);
          cl3.setBorder(0);
          cl4.setBorder(0);
          tablacli.addCell(cl1);
          tablacli.addCell(cl2);
          tablacli.addCell(cl3);
          tablacli.addCell(cl4);
          tablacli.addCell(rucventa.getText());
          tablacli.addCell(nombreclienteventa.getText());
          tablacli.addCell(telefonocv.getText());
          tablacli.addCell(direccionCV.getText());
          
          doc.add(tablacli);
          

        //productos
        
        PdfPTable tablapro= new PdfPTable(4);
          
          tablapro.setWidthPercentage(100);
          tablapro.getDefaultCell().setBorder(0);
          float[]Columnapro= new float[]{10f,50f,15f,20f};
          tablapro.setWidths(Columnapro);
          tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
          
          PdfPCell pro1= new PdfPCell(new Phrase("cant.",negrita));
          PdfPCell pro2= new PdfPCell(new Phrase("Descripcion",negrita));
          PdfPCell pro3= new PdfPCell(new Phrase("precio U",negrita));
          PdfPCell pro4= new PdfPCell(new Phrase("precio T",negrita));
          
          //pone el color  de las letras en el pdf 
          pro1.setBorder(0);
          pro2.setBorder(0);
          pro3.setBorder(0);
          pro4.setBorder(0);
          pro1.setBackgroundColor(BaseColor.DARK_GRAY);
          pro2.setBackgroundColor(BaseColor.DARK_GRAY);
          pro3.setBackgroundColor(BaseColor.DARK_GRAY);
          pro4.setBackgroundColor(BaseColor.DARK_GRAY);
          
          
          
          
          tablapro.addCell(pro1);
          tablapro.addCell(pro2);
          tablapro.addCell(pro3);
          tablapro.addCell(pro4);
          
          for(int i = 0 ; i<tablaventa.getRowCount();i++){
              
              String producto= tablaventa.getValueAt(i, 1).toString();
              String cantidad= tablaventa.getValueAt(i, 2).toString();
              String precio= tablaventa.getValueAt(i, 3).toString();
              String total= tablaventa.getValueAt(i, 4).toString();
              tablapro.addCell(cantidad);
              tablapro.addCell(producto);
              tablapro.addCell(precio);
              tablapro.addCell(total);
          
          }
          doc.add(tablapro);
          
        Paragraph info= new Paragraph();
        info.add("total a pagar "+TotalPagar);
        info.add(Chunk.NEWLINE);
        info.setAlignment(Element.ALIGN_RIGHT);
        doc.add(info);
        
         Paragraph firma= new Paragraph();
        
        firma.add(Chunk.NEWLINE);
        firma.add(" cancelacion y firma\n \n");
        firma.add(" ----------------------");
        firma.setAlignment(Element.ALIGN_CENTER);
        doc.add(firma);
        
        
        Paragraph mensaje= new Paragraph();
        
        mensaje.add(Chunk.NEWLINE);
        mensaje.add("Gracias por su compra que tenga un dia maravilloso ");
        
        mensaje.setAlignment(Element.ALIGN_CENTER);
        doc.add(mensaje);
        doc.close();
          
          
        archivo.close();
        //cada vez que se genere una nueva venta el pdf se abrira automaticamente 
        Desktop.getDesktop().open(file);
      }catch(DocumentException | IOException e){
          System.out.println( e.toString());
      }
              
  }
}
