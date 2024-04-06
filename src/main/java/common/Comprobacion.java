package common;

public class Comprobacion {

    public static void categoriaOk(String categoria) throws CategoriaException {
        boolean categoriaValida = false;
        for (Categoria cat : Categoria.values()) {
            if (cat.name().equalsIgnoreCase(categoria)) {
                categoriaValida = true;
                break;
            }
        }
        if (!categoriaValida) {
            throw new CategoriaException();
        }
    }
}
