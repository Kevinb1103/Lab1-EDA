import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class BigVigenere {
    private int[] key;
    private char[][] alphabet;
    private char[] Chars;

    public BigVigenere() {

        ///aca le pedimoss al usuario que ingrese una clave numerica para despues meterla en un arr de enteros
        ///para despues usarla y moverse por el alfabeto largo
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingresar clave numerica: ");
        String claveNumerica = sc.nextLine();
        this.key = new int[claveNumerica.length()];
        for (int i = 0; i < claveNumerica.length(); i++) {
            key[i] = Character.getNumericValue(claveNumerica.charAt(i));

        }
        this.Chars = new char[]{
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
                'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9'
        };
        this.alphabet=new char[this.Chars.length][this.Chars.length];
        ///aca se ajusta la matriz para que en cada fila horizontal, se vayan corriendo los Chars +1
        for (int i = 0; i < this.Chars.length; i++) {
            for (int j = 0; j < this.Chars.length; j++) {
                alphabet[i][j] = this.Chars[(i + j) % this.Chars.length];
            }
        }
    }

    public BigVigenere(String numericKey) {
        ///recibimos una key en forma de string y la transformamos a un arreglo de enteros
        /// (lo mismo de ante xd) solo que aca ya trae la key
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            key[i] = Character.getNumericValue(numericKey.charAt(i));
        }
        this.Chars = new char[]{
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
                'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9'
        };
        this.alphabet = new char[this.Chars.length][this.Chars.length];
        /// ajustamos la matriz
        for (int i = 0; i < this.Chars.length; i++) {
            for (int j = 0; j < this.Chars.length; j++) {
                this.alphabet[i][j] = this.Chars[(i + j) %this.Chars.length];
            }
        }

    }

    public String encrypt(String message) {
        /// trabajamos con la clase StringBuilder para modificar el string mas facil

        StringBuilder MensajeCifrado = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            /// vamos a recorrer el alfabeto con el caracter "c" entonces inicializamos las coordenadas en -1-1 para actualizar luego
            ///(con este tip podemos ver mas facilmente un error si esque llega a haber alguno en la busqueda osea coords-1-1=error)
            int coord=-1;
            for (int j = 0; j < this.Chars.length; j++) {
                if (this.Chars[j] == c) {
                    coord=j;
                    break;
                }
            }
            if (coord != -1) {
                ///encontrar la posiicion de la  clave
                int clave=this.key[i%this.key.length];
                char CharCifrado = this.alphabet[clave][coord];
                MensajeCifrado.append(CharCifrado);
            }
            else { //si el caracter no se encuentra, solo incluirlo tal cual
                MensajeCifrado.append(c);
            }
        }
        return MensajeCifrado.toString();
    }

    public String decrypt(String encryptedMessage) {
        ///el decrypt es casi lo mismo pero al revez, envez de avanzar en la matriz tenemnos que retroceder restando
        /// recibimos un mensaje encriptado y con la misma contraseña "retrocedemos" para encontrar el mnsj original
        StringBuilder MensajeDecifrado = new StringBuilder();
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char c = encryptedMessage.charAt(i);
            int coordAnterior=-1;
            int clave=this.key[i%this.key.length];

            /// recorremos la matriz con un doble for buscando la coordenada
            /// nos salimos del ciclo
            for (int j = 0; j < this.Chars.length; j++) {
                if (this.alphabet[clave][j] == c) {
                    coordAnterior=j;
                    break;
                }
            }
            if (coordAnterior != -1) {
                char cAnterior=this.Chars[coordAnterior];
                MensajeDecifrado.append(cAnterior);
            }
            else {
                MensajeDecifrado.append(c);
            }
        }
        return MensajeDecifrado.toString();
    }


    public void reEncrypt() {
        /// reEcntrypt por lo que nos piden es mas sencillo ya que es llamar a metodos que
        /// ya implementamos mas arriba en un orden en especifico
        Scanner sc = new Scanner(System.in);
//pedimos un mensaje encriptado
        System.out.print("ingresa mensaje cifrado: ");
        String MensajeCifrado = sc.nextLine();
//se desencripta
        String MensajeDecifrado = decrypt(MensajeCifrado);
//pedimos una clave nueva para encriptarlo
        System.out.println("ingresar nueva clave numErica: ");
        String Clave = sc.nextLine();
//generamos una instancia para encriptar
        BigVigenere cifrar= new BigVigenere(Clave);
//se encripta con la nueva clave
        String NuevoMnsjCifrado = cifrar.encrypt(MensajeDecifrado);
        //se imprime mensaje encriptado con la nueva clave
        System.out.println(NuevoMnsjCifrado);

    }

    public char search(int position) {
        int filas = this.alphabet.length;
        int columnas = alphabet[0].length;
        int Filacalculada= position/columnas;
        int Columnacalculada= position%columnas;
        return this.alphabet[Filacalculada][Columnacalculada];

    }

    public char optimalSearch(int position) {
        // para hacer una busqueda mas optima utilizamos el "mapeo de índice bidimensional"
        /// detallar en informe "mapeo de índice bidimensional"
        int filas = alphabet.length;
        int columnas = alphabet[0].length;
        int FilaCalculada = position / columnas;
        int ColumnaCalculada = position % columnas;
        return alphabet[FilaCalculada][ColumnaCalculada];


    }

     /*      public static void main(String[] args) {
        // Probar constructor por defecto
        System.out.println("--- Prueba Constructor por Defecto ---");
        BigVigenere vigenere1 = new BigVigenere(); // Pedirá la clave por consola
        String mensajeOriginal = "Hola Mundo 123 abc XYZ";
        System.out.println("Mensaje Original: " + mensajeOriginal);
        String mensajeCifrado1 = vigenere1.encrypt(mensajeOriginal);
        System.out.println("Mensaje Cifrado:  " + mensajeCifrado1);
        String mensajeDescifrado1 = vigenere1.decrypt(mensajeCifrado1);
        System.out.println("Mensaje Descifrado: " + mensajeDescifrado1);
        System.out.println("¿Descifrado coincide con original?: " + mensajeOriginal.equals(mensajeDescifrado1));
        System.out.println();

        // Probar constructor con clave dada
        System.out.println("--- Prueba Constructor con Clave '314' ---");
        BigVigenere vigenere2 = new BigVigenere("314");
        System.out.println("Mensaje Original: " + mensajeOriginal);
        String mensajeCifrado2 = vigenere2.encrypt(mensajeOriginal);
        System.out.println("Mensaje Cifrado:  " + mensajeCifrado2);
        String mensajeDescifrado2 = vigenere2.decrypt(mensajeCifrado2);
        System.out.println("Mensaje Descifrado: " + mensajeDescifrado2);
        System.out.println("¿Descifrado coincide con original?: " + mensajeOriginal.equals(mensajeDescifrado2));
        System.out.println();

        // Probar reEncrypt (requiere interacción)
        System.out.println("--- Prueba reEncrypt (usando clave '314' inicial) ---");
        // Necesitas ingresar el mensaje cifrado anterior (mensajeCifrado2) y luego una nueva clave
        // vigenere2.reEncrypt(); // Descomenta para probar interactivamente

        // Probar búsquedas
        System.out.println("--- Prueba Búsquedas ---");
        System.out.println("Carácter en posición 0 (search): " + vigenere2.search(0)); // Debería ser 'a'
        System.out.println("Carácter en posición 0 (optimalSearch): " + vigenere2.optimalSearch(0)); // Debería ser 'a'
        System.out.println("Carácter en posición 64 (search): " + vigenere2.search(64)); // Debería ser 'b' (inicio de la segunda fila si la primera es 'a'...'9')
        System.out.println("Carácter en posición 64 (optimalSearch): " + vigenere2.optimalSearch(64)); // Debería ser 'b'
        System.out.println("Carácter en posición 100 (search): " + vigenere2.search(100));
        System.out.println("Carácter en posición 100 (optimalSearch): " + vigenere2.optimalSearch(100));
        System.out.println("Carácter en posición -5 (search): " + vigenere2.search(-5)); // Prueba fuera de rango
        System.out.println("Carácter en posición 9999 (optimalSearch): " + vigenere2.optimalSearch(9999)); // Prueba fuera de rango

    }
    */

    public static void main(String[] args) throws IOException {
        int[] keySizes = {10, 50, 100, 500, 1000, 5000};
        int messageLength = 10000;
        String characters = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";

        // Generar mensaje base de 10.000 caracteres aleatorios
        Random rand = new Random();
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < messageLength; i++) {
            messageBuilder.append(characters.charAt(rand.nextInt(characters.length())));
        }
        String mensaje = messageBuilder.toString();

        // Crear archivo CSV
        FileWriter writer = new FileWriter("tiempos_cifrado.csv");
        writer.write("KeySize,Cifrado(ns),Descifrado(ns)\n");

        for (int size : keySizes) {
            // Generar clave numérica aleatoria del tamaño dado
            StringBuilder claveNumerica = new StringBuilder();
            for (int i = 0; i < size; i++) {
                claveNumerica.append(rand.nextInt(10)); // dígitos del 0 al 9
            }

            BigVigenere bigVigenere = new BigVigenere(claveNumerica.toString());

            // Medir cifrado
            long startEncrypt = System.nanoTime();
            String mensajeCifrado = bigVigenere.encrypt(mensaje);
            long endEncrypt = System.nanoTime();
            long tiempoCifrado = endEncrypt - startEncrypt;

            // Medir descifrado
            long startDecrypt = System.nanoTime();
            String mensajeDescifrado = bigVigenere.decrypt(mensajeCifrado);
            long endDecrypt = System.nanoTime();
            long tiempoDescifrado = endDecrypt - startDecrypt;

            // Escribir resultados
            System.out.printf("Clave de largo %d => Cifrado: %d ns | Descifrado: %d ns%n",
                    size, tiempoCifrado, tiempoDescifrado);

            writer.write(size + "," + tiempoCifrado + "," + tiempoDescifrado + "\n");
        }

        writer.close();
        System.out.println("Resultados guardados en tiempos_cifrado.csv ✅");
    }
}
