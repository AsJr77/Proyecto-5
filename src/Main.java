//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Telefono[] telefonos = new Telefono[10];
        iniciarMenu(telefonos);
    }

    public static void iniciarMenu(Telefono[] telefonos) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n## MENU PRINCIPAL ##");
            System.out.println("1. Crear Teléfono");
            System.out.println("2. Mostrar teléfonos creados");
            System.out.println("3. Usar Teléfono");
            System.out.println("4. Terminar programa");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearTelefono(telefonos);
                    break;
                case 2:
                    mostrarTelefonos(telefonos);
                    break;
                case 3:
                    usarTelefono(telefonos);
                    break;
                case 4:
                    System.out.println("Programa terminado.");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    public static void crearTelefono(Telefono[] telefonos) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < telefonos.length; i++) {
            if (telefonos[i] == null) {
                System.out.print("Ingrese el nombre del titular: ");
                String titular = scanner.nextLine();
                System.out.print("Ingrese el saldo inicial: ");
                double saldo = scanner.nextDouble();
                telefonos[i] = new Telefono(titular, saldo);
                System.out.println("Teléfono creado en la celda " + i + ".");
                return;
            }
        }
        System.out.println("Se ha llegado al máximo de teléfonos.");
    }

    public static void mostrarTelefonos(Telefono[] telefonos) {
        System.out.println("Teléfonos creados:");
        for (int i = 0; i < telefonos.length; i++) {
            if (telefonos[i] != null) {
                System.out.println("Celda " + i + ": " + telefonos[i].getTitular());
            }
        }
    }

    public static void usarTelefono(Telefono[] telefonos) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de celda del teléfono a usar: ");
        int celda = scanner.nextInt();
        if (celda < 0 || celda >= telefonos.length || telefonos[celda] == null) {
            System.out.println("No hay teléfono en la celda " + celda + ".");
            return;
        }

        Telefono telefono = telefonos[celda];
        while (true) {
            System.out.println("\n## Teléfono " + celda + " en uso ##");
            System.out.println("1. Mostrar estado");
            System.out.println("2. Encender/Apagar");
            System.out.println("3. Abonar saldo");
            System.out.println("4. Hacer llamada");
            System.out.println("5. Mostrar contactos");
            System.out.println("6. Mostrar historial de llamadas");
            System.out.println("10. Regresar a Menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    telefono.mostrarEstado();
                    break;
                case 2:
                    telefono.cambiarEstado();
                    break;
                case 3:
                    System.out.print("Ingrese la cantidad a abonar: ");
                    double cantidad = scanner.nextDouble();
                    telefono.abonarSaldo(cantidad);
                    break;
                case 4:
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese el nombre del contacto a llamar: ");
                    String nombre = scanner.nextLine();
                    telefono.hacerLlamada(nombre);
                    break;
                case 5:
                    telefono.mostrarContactos();
                    break;
                case 6:
                    telefono.mostrarHistorialLlamadas();
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
}

class Telefono {
    private String titular;
    private double saldo;
    private boolean encendido;
    private String[] contactos;
    private String[] numerosDesconocidos;
    private int[] contadorLlamadasContactos;
    private int[] contadorLlamadasDesconocidos;
    private int cantidadContactos;
    private int cantidadDesconocidos;

    public Telefono(String titular, double saldo) {
        this.titular = titular;
        this.saldo = saldo;
        this.encendido = true;
        this.contactos = new String[20];
        this.numerosDesconocidos = new String[20];
        this.contadorLlamadasContactos = new int[20];
        this.contadorLlamadasDesconocidos = new int[20];
        this.cantidadContactos = 0;
        this.cantidadDesconocidos = 0;
    }

    public String getTitular() {
        return titular;
    }

    public void mostrarEstado() {
        System.out.println("Teléfono de " + titular + " está " + (encendido ? "encendido" : "apagado") + ". Saldo: $" + saldo);
    }

    public void cambiarEstado() {
        encendido = !encendido;
        System.out.println("Teléfono " + (encendido ? "encendido" : "apagado") + ".");
    }

    public void abonarSaldo(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Se abonaron $" + cantidad + ". Saldo actual: $" + saldo);
        } else {
            System.out.println("Cantidad inválida.");
        }
    }

    public void hacerLlamada(String nombre) {
        if (!encendido) {
            System.out.println("El teléfono está apagado. No se puede hacer la llamada.");
            return;
        }
        if (saldo < 5) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número a llamar:");
        String numero = scanner.nextLine();

        if (!numero.matches("\\d{10}")) {
            System.out.println("Número inválido");
            return;
        }

        saldo -= 5;
        System.out.println("Llamada realizada al número " + numero);

        boolean registrado = false;
        for (int i = 0; i < cantidadDesconocidos; i++) {
            if (numerosDesconocidos[i].equals(numero)) {
                contadorLlamadasDesconocidos[i]++;
                registrado = true;
                break;
            }
        }

        if (!registrado) {
            if (cantidadDesconocidos < 20) {
                numerosDesconocidos[cantidadDesconocidos] = numero;
                contadorLlamadasDesconocidos[cantidadDesconocidos] = 1;
                cantidadDesconocidos++;
            } else {
                System.out.println("Historial lleno.");
            }
        }

        System.out.println("¿Desea registrar el número como contacto? (S/N)");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.println("Ingrese el nombre:");
            String nuevoNombre = scanner.nextLine();
            agregarContacto(nuevoNombre + "/" + numero);
        }
    }

    public void agregarContacto(String contacto) {
        if (cantidadContactos < 20) {
            contactos[cantidadContactos] = contacto;
            contadorLlamadasContactos[cantidadContactos] = 0;
            cantidadContactos++;
        }
    }

    public void mostrarContactos() {
        System.out.println("Contactos:");
        for (String contacto : contactos) {
            if (contacto != null) System.out.println(contacto);
        }
    }

    public void mostrarHistorialLlamadas() {
        System.out.println("Historial de llamadas:");
        for (int i = 0; i < cantidadDesconocidos; i++) {
            System.out.println(numerosDesconocidos[i] + " - Llamadas: " + contadorLlamadasDesconocidos[i]);
        }
    }
}
