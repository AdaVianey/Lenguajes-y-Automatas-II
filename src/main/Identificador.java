package main;

public class Identificador {
	
	private String nombre,tipo,valor;
	public Identificador(String n, String t,String v){
		nombre = n; tipo = t; valor = v;
	}
	public String getNombre() {
		return nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public String getValor() {
		return valor;
	}
	
	
}
