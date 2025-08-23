package com.frankester.mscompras;

import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.estados.EstadoPublicacion;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.models.personas.documentos.Documento;
import com.frankester.mscompras.models.personas.documentos.TipoDeDocumento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class  Utils {
    private Utils(){}

    public static Comprador crearComprador(){
        Comprador comprador = new Comprador();
        comprador.setNombre("Franco");
        comprador.setApellido("Callero");
        comprador.setFechaNacimiento(LocalDate.of(2001,6,10));
        Documento documento = new Documento(TipoDeDocumento.DNI, "43244599");
        comprador.setDocumento(documento);
        return comprador;
    }

     public static Publicacion crearPublicacion(String nombrePublicacion, String precio, String nombreProductoBase, String nombrePersonalizacion, Tienda tienda){
        Publicacion publicacion = new Publicacion();
        publicacion.setNombrePublicacion(nombrePublicacion);
        publicacion.setFechaDePublicacion(LocalDate.of(2025, 8,10));
        publicacion.setPrecioPublicacion(new BigDecimal(precio));
        publicacion.setEstado(EstadoPublicacion.VIRGENTE);
        publicacion.setNombreProductoBase(nombreProductoBase);
        publicacion.setNombrePersonalizacion(nombrePersonalizacion);

        publicacion.setTienda(tienda);
        return publicacion;
    }
}
