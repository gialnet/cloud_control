/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta;

/**
 *
 * @author antonio
 */
public class TuplasPaises {
    private final int id;
    private final String descripcion;
    private final String ISO_3166_1;
    private final String cuenta_cliente;
    private final String cuenta_ventas;

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getISO_3166_1() {
        return ISO_3166_1;
    }

    public String getCuenta_cliente() {
        return cuenta_cliente;
    }

    public String getCuenta_ventas() {
        return cuenta_ventas;
    }

    public static class Builder {

        private int id;
        private String descripcion;
        private String ISO_3166_1;
        private String cuenta_cliente;
        private String cuenta_ventas;
    
        public Builder() {
            super();
        }
        

        public Builder Id(final int id) {
            this.id = id;
            return this;
        }

        public Builder Descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder ISO_3166_1(final String ISO_3166_1) {
            this.ISO_3166_1 = ISO_3166_1;
            return this;
        }

        public Builder Cuenta_cliente(final String cuenta_cliente) {
            this.cuenta_cliente = cuenta_cliente;
            return this;
        }

        public Builder Cuenta_ventas(final String cuenta_ventas) {
            this.cuenta_ventas = cuenta_ventas;
            return this;
        }

        public TuplasPaises build() {
            return new TuplasPaises(this);
        }
        
        
    }
    
    private TuplasPaises(Builder builder)
        {
            this.id = builder.id;
            this.descripcion = builder.descripcion;
            this.ISO_3166_1 = builder.ISO_3166_1;
            this.cuenta_cliente = builder.cuenta_cliente;
            this.cuenta_ventas = builder.cuenta_ventas;
        }
    
    
}
