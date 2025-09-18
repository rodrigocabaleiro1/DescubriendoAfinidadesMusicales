package testeoMain;
import negocio.Usuario;
public class Main {

	public static void main(String[] args) {
		Usuario usuario = new Usuario("Gay");
		System.out.println(usuario);
		try {
		usuario.disminuirInteresFolclore();
		}catch (RuntimeException e) {
		}
		usuario.incrementarInteresFolclore();
		usuario.incrementarInteresFolclore();
		System.out.println(usuario);
		usuario.incrementarInteresFolclore();
		usuario.incrementarInteresFolclore();
		System.out.println(usuario);
		usuario.incrementarInteresFolclore();
		System.out.println(usuario);
	}

}
