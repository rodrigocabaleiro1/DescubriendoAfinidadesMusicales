package testeoMain;
import negocio.IndiceDeSimilaridad;
import negocio.Usuario;
import pruebas.Prueba;
public class Main {

	public static void main(String[] args) {
		Prueba prueba = new Prueba();
		IndiceDeSimilaridad indice = new IndiceDeSimilaridad (prueba.cargarUsuarios());
		indice.MostrarSimilaridades();
	}

}
