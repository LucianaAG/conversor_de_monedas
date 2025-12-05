public class Moneda {
    private String monedaBase;
    private String monedaObjetivo;
    private double cantidad;
    private double tasaDeConversion;
    private double resultado;

    public Moneda(String monedaBase, String monedaObjetivo, double cantidad,
                            double tasaDeConversion, double resultado) {
        this.monedaBase = monedaBase;
        this.monedaObjetivo = monedaObjetivo;
        this.cantidad = cantidad;
        this.tasaDeConversion = tasaDeConversion;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Conversión: " + cantidad + " " + monedaBase + " = " + resultado + " " + monedaObjetivo +
                "\nTasa utilizada: " + tasaDeConversion;
    }
}
