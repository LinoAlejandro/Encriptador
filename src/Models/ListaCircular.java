package Models;

public class ListaCircular <T>{
	
	private Nodo<T> inicio;
	
	public ListaCircular(){
		setInicio(null);
	}
	
	public void insertNodeAntiClockwise(T dato){
		Nodo nodo = new Nodo(dato);
		if(isListEmpty()){
			this.setInicio(nodo);
		} else {
			Nodo aux = this.inicio;
			while(aux.getSiguiente() != inicio){
				aux = aux.getSiguiente();
			}
			nodo.setSiguiente(inicio);
			aux.setSiguiente(nodo);
		}
	}
	
	public String toString(){
		String texto = "";
		Nodo nodo = this.inicio;
		if(!isListEmpty()){
			do{
				texto = texto + nodo.getDato() + " ";
				nodo = nodo.getSiguiente();
			} while (nodo != inicio);
		}	
		return texto;
	}
	
	public boolean isListEmpty(){
		if(inicio == null)
			return true;
		else
			return false;
	}

	public Nodo<T> getInicio() {
		return inicio;
	}

	public void setInicio(Nodo<T> inicio) {
		this.inicio = inicio;
	}
}
