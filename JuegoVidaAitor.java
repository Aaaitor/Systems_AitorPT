package Juego;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class JuegoVidaAitor {
	// Tama�o de la matriz
		static int tama�o = 50;
		// Muestra las casillas muertas con un - y las casillas vivas v
		static char muerta='-';
		static char v;
		// Las casillas vivas se representan con un 1 en la matriz y las casillas muertas un 0
		static int casillaViva = 1;
		static int casillaMuerta = 0;
		// Inicio es la matriz por defecto 
		static int[][] inicio = new int[tama�o][tama�o];

		// Numero de tableros que se producen
		static int tablero = 30;

		// Tiempo entre tableros
		static int tiempo = 2000;

		
		public static void Matriz(String ruta) {

			try {
				FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr);

				String linea;
				String[] fila;
				int numFilas = 0;

				while ((linea = br.readLine()) != null) {

					fila = linea.split(",");
					for (int numCol = 0; numCol < tama�o; numCol++) {
						if (numCol < fila.length) {
							inicio[numFilas][numCol] = Integer.parseInt(fila[numCol].trim());
						} else {
							inicio[numFilas][numCol] = 0;
						}

					}
					numFilas++;

				}
				if (numFilas < tama�o) {
					for (int i = numFilas; i < tama�o; i++) {
						for (int j = 0; j < tama�o; j++) {
							inicio[i][j] = 0;
						}

					}
				} 
				fr.close();
				//Tratamos Excepcion
			} catch (Exception e) {
				System.out.println("Excepcion fichero " + ruta + ": " + e);

			}

		}

		public static void mostrarTablero() {

			for (int i = 0; i < tama�o; i++) {
				for (int j = 0; j < tama�o; j++) {
					if (inicio[i][j] == 1) {
	                    System.out.print(" v");
	                } else {
	                    System.out.format(" -");
	                }
				}
				System.out.println();
			}

		}

		public static int numVivasRodean(int fila, int columna) {

			int numVivas = 0;

			int comienzoFila, finalFila, comienzoCol, finalCol;

			//  recorrido de la fila
			if (fila == 0) {
				comienzoFila = 0;
				finalFila = 1;
			} else if (fila == (tama�o - 1)) {
				comienzoFila = tama�o - 2;
				finalFila = tama�o - 1;
			} else {
				comienzoFila = fila - 1;
				finalFila = fila + 1;
			}

			//  recorrido de la columna
			if (columna == 0) {
				comienzoCol = 0;
				finalCol = 1;
			} else if (columna == (tama�o - 1)) {
				comienzoCol = tama�o - 2;
				finalCol = tama�o - 1;
			} else {
				comienzoCol = columna - 1;
				finalCol = columna + 1;
			}

			for (int i = comienzoFila; i <= finalFila; i++) {
				for (int j = comienzoCol; j <= finalCol; j++) {
					if (!(i == fila && j == columna) && inicio[i][j] == casillaViva) {
						numVivas++;
					}
				}

			}

			return numVivas;

		}


		public static int[][] proximaGen(int[][] generacion) throws InterruptedException {
			int[][] nuevoTablero = new int[tama�o][tama�o];
			for (int i = 0; i < tama�o; i++) {
				for (int j = 0; j < tama�o; j++) {
					// si la casilla esta viva
					if (generacion[i][j] == casillaViva) {
						if (numVivasRodean(i, j) < 2) {
							nuevoTablero[i][j] = casillaMuerta;
						} else if (numVivasRodean(i, j) < 4) {
							nuevoTablero[i][j] = casillaViva;
						} else {
							nuevoTablero[i][j] = casillaMuerta;
						}
						// si la casilla esta muerta
					} else {
						if (numVivasRodean(i, j) == 3) {
							nuevoTablero[i][j] = casillaViva;
						}
					}
				}
			}

			return nuevoTablero;
		}
		public static void main(String[] args) throws InterruptedException, IOException {
			Matriz("F:\\JuegoAitor.csv");

			for (int i = 0; i < tablero; i++) {

				System.out.println("\t\t  Numero de tablero " + (i + 1));
				mostrarTablero();
				Thread.sleep(tiempo);

				// generamos la siguiente generaci�n y la guardamos en el tablero
				inicio = proximaGen(inicio);

			}

		}
	
	

}

