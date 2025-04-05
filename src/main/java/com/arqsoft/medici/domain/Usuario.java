package com.arqsoft.medici.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.arqsoft.medici.domain.utils.UsuarioEstado;

@Document(collection = "usuario")
public class Usuario {
	
	@Id
	private String mail;
	private String nombre;
	private String apellido;
	private UsuarioEstado estado;
	
	
	public Usuario() {}
	
	public Usuario(String nombre, String apellido, String mail) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.estado = UsuarioEstado.ACTIVO;
	}
	
	public Usuario(String nombre, String apellido, String mail, UsuarioEstado estado) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.estado = estado;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public UsuarioEstado getEstado() {
		return estado;
	}

	public void setEstado(UsuarioEstado estado) {
		this.estado = estado;
	}
	

}
