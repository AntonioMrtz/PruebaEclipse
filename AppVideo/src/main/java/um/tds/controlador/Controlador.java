package um.tds.controlador;

import java.time.LocalDate;
import java.util.List;

import um.tds.dominio.CatalogoEtiquetas;
import um.tds.dominio.CatalogoUsuarios;
import um.tds.dominio.CatalogoVideos;
import um.tds.dominio.Usuario;
import um.tds.dominio.Video;
import um.tds.persistencia.DAOException;
import um.tds.persistencia.FactoriaDAO;
import um.tds.persistencia.IAdaptadorUsuarioDAO;
import um.tds.persistencia.IAdaptadorVideoDAO;



public class Controlador {
	
	
	private static Controlador unicaInstancia;
	

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;
	
	//private Usuario usuarioActual;
	
	
	
	private Controlador()  {
		
		try {
			FactoriaDAO factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		
		
		
		inicialiarAdaptadores();
		inicializarCatálogos();
		
		
	}
	
	public static Controlador getUnicaInstancia(){
		
		
		if(unicaInstancia==null)
			unicaInstancia=new Controlador();
		
		return unicaInstancia;
	}
	
	
	/* REGISTER */

	public void registrarUsuario(String n,String apellidos,String email,boolean prem,String contra,String usu,LocalDate fecha) {
		
		
		Usuario u = new Usuario(n, apellidos, email, prem, contra, fecha);
		adaptadorUsuario.addUsuario(u);
		catalogoUsuarios.addUsuario(u);
		
		// no estamos controlando si se registra el mismo usuario varias veces
			
		
	}
	
	
	public void registrarVideo(String url,String titulo) {
		
		Video v = new Video(url, titulo);
		adaptadorVideo.addVideo(v);
		catalogoVideos.addVideo(v);
		
		
		
	}
	
	
	/* FIND */
	
	public Video findVideo(Video v) {
		
		return catalogoVideos.getVideo(v.getUrl());
	}
	
	public Usuario findUser(Usuario us) {
		
		return catalogoUsuarios.getUsuario(us.getUsuario());
		
	}
	
	
	
	/* INICIALIZAR */
	
	private void inicialiarAdaptadores() {
		
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario= factoria.getUsuarioDAO();
		adaptadorVideo = factoria.getVideoDAO();
		
		
	}
	
	
	
	private void inicializarCatálogos() {
		
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoVideos= CatalogoVideos.getUnicaInstancia();
		
		
	}
	
	
	
	/* REMOVE */
	
	public void removeUsuario(Usuario u) {
		
		adaptadorUsuario.removeUsuario(u);
		catalogoUsuarios.removeUsuario(u);
		
	}
	
	
	public void removeVideo(Video v) {
		
		adaptadorVideo.removeVideo(v);
		catalogoVideos.removeVideo(v);
		
	}
	
	

	/* GET */
	
	
	public List<Usuario> getUsuarios() {
		
		return catalogoUsuarios.getUsuarios();
	}
	public List<Video> getClientes() {

		return catalogoVideos.getVideos();
	}
	
}
