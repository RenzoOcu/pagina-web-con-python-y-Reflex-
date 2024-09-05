
package modelo;

/**
 *
 * @author Eva Maria
 */
public class Proveedor {
     private int id ;
    private  int ruc;
    private String  nombre;
    private int telefono;
    private String razon;
    private String direccion;
    
    public Proveedor(){
        
    }

    public Proveedor(int id, int ruc, String nombre, int telefono, String razon, String direccion) {
        this.id = id;
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.razon = razon;
        this.direccion = direccion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuc() {
        return ruc;
    }

    public void setRuc(int ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
