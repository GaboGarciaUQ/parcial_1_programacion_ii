public class ValidadorNumericos {

    public boolean esNumeroPerfecto(int numero) {
        if (numero <= 1) {
            return false;
        }

        int sumaDivisores = 1;

        for (int i = 2; i <= numero / 2; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
            }
        }

        return sumaDivisores == numero;
    }

    public boolean esPositivo(double numero) {
        return numero > 0;
    }

    public boolean esPorcentajeValido(double porcentaje) {
        return porcentaje >= 0.0 && porcentaje <= 100.0;
    }
}