package org.jann.core;

public class InvalidInputVectorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputVectorException(){
		System.out.println("Le vecteur d'entr�e n'est pas du bon format");
	}
}
